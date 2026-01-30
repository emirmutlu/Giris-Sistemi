package tr.com.uludem.b4b;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.URI;
public final class BrowserLauncher {
    private static final String[][] BROWSER_PATHS = new String[][]{
            {"Google Chrome",
                    "C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe",
                    "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe" },
            {"Mozilla Firefox",
                    "C:\\Program Files\\Mozilla Firefox\\firefox.exe",
                    "C:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe"},
            {"Opera",
                    "C:\\Program Files\\Opera\\launcher.exe",
                    "C:\\Program Files (x86)\\Opera\\launcher.exe"},
            {"Brave",
                    "C:\\Program Files\\BraveSoftware\\Brave-Browser\\Application\\brave.exe",
                    "C:\\Program Files (x86)\\BraveSoftware\\Brave-Browser\\Application\\brave.exe"},
            {"Vivaldi",
                    "C:\\Program Files\\Vivaldi\\Application\\vivaldi.exe",
                    "C:\\Program Files (x86)\\Vivaldi\\Application\\vivaldi.exe"},
    };

    public static void open (String browserChoice, String url){
        try{
            if(browserChoice != null && !browserChoice.isEmpty()){
                for (String[] row : BROWSER_PATHS){
                    if(browserChoice.equals(row[0])){
                        for ( int i = 1; i< row.length; i++){
                            String p = row[i];
                            if(new File(p).exists()){
                                new ProcessBuilder(p,url).start();
                                return;
                            }
                        }
                    }
                }
            }

            Desktop.getDesktop().browse(new URI(url));
        } catch (Exception e) {
            MessageWriteToFile.Write("BrowserLauncher.open", e);
            JOptionPane.showMessageDialog(null, "URL açılamadı:\n" + e.getMessage());
        }
    }
}
