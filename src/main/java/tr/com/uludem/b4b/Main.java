package tr.com.uludem.b4b;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.io.InputStream;
import java.util.Iterator;

public class Main {

    private static List<Image> loadIcoFromResources(String path) {
        List<Image> list = new ArrayList<>();
        try (InputStream in = Main.class.getResourceAsStream(path)) {
            if (in == null) return list;
            try (ImageInputStream iis = ImageIO.createImageInputStream(in)) {
                Iterator<ImageReader> it = ImageIO.getImageReaders(iis);
                if (!it.hasNext()) return list;
                ImageReader reader = it.next();
                reader.setInput(iis);
                int n = reader.getNumImages(true);
                for (int i = 0; i < n; i++) list.add(reader.read(i));
                reader.dispose();
            }
        } catch (Exception ignored) { }
        return list;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            java.util.List<Image> icons = loadIcoFromResources("/Resources/uludemfavicon.ico");
            if (!icons.isEmpty()) frame.setIconImages(icons);

            frame.setContentPane(new MainForm());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setExtendedState(JFrame.NORMAL);
            frame.setResizable(false);
            frame.setVisible(true);
        });
    }
}
