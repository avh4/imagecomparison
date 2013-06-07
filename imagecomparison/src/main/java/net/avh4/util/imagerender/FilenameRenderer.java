package net.avh4.util.imagerender;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class FilenameRenderer implements Renderer {
    @Override
    public BufferedImage getImage(Object obj) {
        if (obj instanceof String) {
            String filename = (String) obj;
            try {
                final File file = new File(filename);
                return ImageIO.read(file);
            } catch (final IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }
}
