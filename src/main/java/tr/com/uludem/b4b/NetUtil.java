package tr.com.uludem.b4b;

import java.net.NetworkInterface;
import java.util.Enumeration;

public final class NetUtil {
    public static String getMacKod() {
        try {
            Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces();
            while (en.hasMoreElements()) {
                NetworkInterface ni = en.nextElement();
                if (ni == null || ni.isLoopback() || ni.isVirtual() || !ni.isUp()) continue;
                byte[] mac = ni.getHardwareAddress();
                if (mac == null || mac.length == 0) continue;

                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < mac.length; i++) {
                    if (i > 0) sb.append("-");
                    sb.append(String.format("%02X", mac[i]));
                }
                return sb.toString();
            }
        } catch (Exception ignored) { }
        return "";
    }
}
