package cw.kop.rawcapture.api;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by TheKeeperOfPie on 8/31/2014.
 */
public class SsdpClient {

    private static final String TAG = SsdpClient.class.getSimpleName();

    private final static int SSDP_RECEIVE_TIMEOUT = 10000; // msec
    private final static int PACKET_BUFFER_SIZE = 1024;
    private final static int SSDP_PORT = 1900;
    private final static int SSDP_MX = 1;
    private final static String SSDP_ADDR = "239.255.255.250";
    private final static String SSDP_ST = "urn:schemas-sony-com:service:ScalarWebAPI:1";

    private boolean isSearching = false;

    /** Handler interface for SSDP search result. */
    public interface SearchResultHandler {

        /**
         * Called when API server device is found. Note that it's performed by
         * non-UI thread.
         *
         * @param device API server device that is found by searching
         */
        public void onDeviceFound(CameraDevice device);

        /**
         * Called when searching completes successfully. Note that it's
         * performed by non-UI thread.
         */
        public void onFinished();

        /**
         * Called when searching completes with some errors. Note that it's
         * performed by non-UI thread.
         */
        public void onErrorFinished();
    }

    public boolean search(final String ssid, final SearchResultHandler handler) {
        if (isSearching) {
            Log.w(TAG, "search() already searching.");
            return false;
        }

        Log.i(TAG, "search() Start.");

        final String ssdpRequest = "M-SEARCH * HTTP/1.1\r\n"
                + String.format("HOST: %s:%d\r\n", SSDP_ADDR, SSDP_PORT)
                + String.format("MAN: \"ssdp:discover\"\r\n")
                + String.format("MX: %d\r\n", SSDP_MX)
                + String.format("ST: %s\r\n", SSDP_ST) + "\r\n";
        final byte[] sendData = ssdpRequest.getBytes();

        new Thread() {

            @Override
            public void run() {
                // Send Datagram packets
                DatagramSocket socket = null;
                DatagramPacket receivePacket = null;
                DatagramPacket packet = null;
                try {
                    socket = new DatagramSocket();
                    InetSocketAddress iAddress = new InetSocketAddress(SSDP_ADDR, SSDP_PORT);
                    packet = new DatagramPacket(sendData, sendData.length, iAddress);
                    Log.i(TAG, "search() Send Datagram packet 3 times.");
                    socket.send(packet);
                    Thread.sleep(100);
                    socket.send(packet);
                    Thread.sleep(100);
                    socket.send(packet);
                } catch (InterruptedException e) {
                    // do nothing.
                } catch (SocketException e) {
                    Log.e(TAG, "search() DatagramSocket error:", e);
                } catch (IOException e) {
                    Log.e(TAG, "search() IOException:", e);
                }

                if (socket == null) {
                    return;
                }

                // Receive reply packets
                isSearching = true;
                long startTime = System.currentTimeMillis();
                List<String> foundDevices = new ArrayList<String>();
                byte[] array = new byte[PACKET_BUFFER_SIZE];
                while (isSearching) {
                    receivePacket = new DatagramPacket(array, array.length);
                    try {
                        socket.setSoTimeout(SSDP_RECEIVE_TIMEOUT);
                        socket.receive(receivePacket);
                        String ssdpReplyMessage = new String(
                                receivePacket.getData(), 0,
                                receivePacket.getLength());
                        String ddUsn = findParameterValue(ssdpReplyMessage, "USN");

                        /*
                         * There is possibility to receive multiple packets from
                         * a individual server.
                         */
                        if (!foundDevices.contains(ddUsn)) {
                            String ddLocation = findParameterValue(ssdpReplyMessage, "LOCATION");
                            foundDevices.add(ddUsn);

                            // Fetch Device Description XML and parse it.
                            CameraDevice device = fetch(ddLocation);
                            device.setSsid(ssid);

                            handler.onDeviceFound(device);

                            break;
                        }
                    } catch (InterruptedIOException e) {
                        Log.d(TAG, "search() Timeout.");
                        break;
                    } catch (IOException e) {
                        Log.d(TAG, "search() IOException.");
                        return;
                    }
                    if (SSDP_RECEIVE_TIMEOUT < System.currentTimeMillis() - startTime) {
                        break;
                    }
                }
                isSearching = false;
                if (socket != null && !socket.isClosed()) {
                    socket.close();
                }
                handler.onFinished();
            }
        }.start();

        return true;
    }

    private static String findParameterValue(String ssdpMessage, String paramName) {
        String name = paramName;
        if (!name.endsWith(":")) {
            name = name + ":";
        }
        int start = ssdpMessage.indexOf(name);
        int end = ssdpMessage.indexOf("\r\n", start);
        if (start != -1 && end != -1) {
            start += name.length();
            String val = ssdpMessage.substring(start, end);
            if (!val.equals("")) {
                return val.trim();
            }
        }
        return null;
    }


    private static CameraDevice fetch(String ddUrl) {
        if (ddUrl == null) {
            throw new NullPointerException("ddUrl is null.");
        }

        String ddXml = "";
        try {
            ddXml = HttpConnector.httpGet(ddUrl);
            Log.d(TAG, "fetch () httpGet done.");
        } catch (IOException e) {
            Log.e(TAG, "fetch: IOException.", e);
            return null;
        }

        Log.i(TAG, ddXml);

        CameraDevice device = new CameraDevice();

        Document doc = Jsoup.parse(ddXml);

        device.setDdUIrl(ddUrl);
        device.setFriendlyName(doc.select("friendlyName").first().text());
        device.setModelName(doc.select("modelName").first().text());
        device.setUdn(doc.select("UDN").first().text());

        for (Element element : doc.select("av|X_ScalarWebAPI_Service")) {

            if (element.select("av|X_ScalarWebAPI_ServiceType").text().equals("camera")) {
                device.setActionUrl(element.select("av|X_ScalarWebAPI_ActionList_URL").text());
                break;
            }

        }

        Log.d(TAG, "fetch () parsing XML done.");
        return device;
    }
}
