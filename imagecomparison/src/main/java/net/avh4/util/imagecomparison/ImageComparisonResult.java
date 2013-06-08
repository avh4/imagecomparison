package net.avh4.util.imagecomparison;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class ImageComparisonResult {
    protected final BufferedImage actualImage;

    public ImageComparisonResult(BufferedImage actualImage) {
        this.actualImage = actualImage;
    }

    public abstract boolean isEqual();

    private static void write(final BufferedImage image,
                              final String filename) {
        final String safeFilename = filename.replaceFirst("^.*/+", "");
        try {
            ImageIO.write(image, "png", new File(safeFilename));
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    public void writeActualImageToFile(String filename) {
        write(actualImage, filename);
    }

    public abstract String getFailureMessage();
}
