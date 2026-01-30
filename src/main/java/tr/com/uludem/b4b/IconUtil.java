package tr.com.uludem.b4b;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.Image;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

final class IconUtil {

    static List<Image> loadIcoFromResource(String resourcePath) {
        List<Image> images = new ArrayList<>();
        try (InputStream in = IconUtil.class.getResourceAsStream(resourcePath)) {
            if (in == null) return images;
            try (ImageInputStream iis = ImageIO.createImageInputStream(in)) {
                Iterator<ImageReader> it = ImageIO.getImageReaders(iis);
                if (!it.hasNext()) return images;
                ImageReader reader = it.next();
                reader.setInput(iis);
                int count = reader.getNumImages(true);
                for (int i = 0; i < count; i++) {
                    images.add(reader.read(i));
                }
                reader.dispose();
            }
        } catch (Exception ignored) { }
        return images;
    }
}
