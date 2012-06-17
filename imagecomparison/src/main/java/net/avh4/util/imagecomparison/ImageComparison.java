package net.avh4.util.imagecomparison;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

public class ImageComparison {

    private static final ServiceLoader<Renderer> rendererLoader =
            ServiceLoader.load(Renderer.class);

    public static boolean matchesImage(final BufferedImage itemImage,
                                       final BufferedImage referenceImage, final String filename) {
        // Compare the image sizes
        if (itemImage.getWidth() != referenceImage.getWidth()
                || itemImage.getHeight() != referenceImage.getHeight()) {
            write(itemImage, filename);
            return false;
        }

        // Compare the image data
        final Raster itemRaster = itemImage.getData();
        final Raster referenceRaster = referenceImage.getData();
        final int width = itemRaster.getWidth();
        final int height = itemRaster.getHeight();
        for (int y = 0; y < height; y++) {
            final int itemPixels[] = new int[4 * width];
            final int referencePixels[] = new int[4 * width];
            itemRaster.getPixels(0, y, width, 1, itemPixels);
            referenceRaster.getPixels(0, y, width, 1, referencePixels);
            for (int i = 0; i < 4 * width; i++) {
                if (itemPixels[i] != referencePixels[i]) {
                    write(itemImage, filename);
                    return false;
                }
            }
        }

        return true;
    }

    private static BufferedImage read(final String imageName) {
        try {
            return ImageIO.read(new File(imageName));
        } catch (final IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void write(final BufferedImage image, final String filename) {
        final String safeFilename = filename.replaceFirst("^.*/+", "");
        try {
            ImageIO.write(image, "png", new File(safeFilename));
        } catch (final IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean matches(final Object item,
                                  final String referenceFilename, final String outputFilename) {
        final BufferedImage expectedImage = read(referenceFilename);
        return matches(item, expectedImage, outputFilename);
    }

    public static boolean matches(final Object actual,
                                  final BufferedImage expectedImage, final String outputFilename) {
        final BufferedImage actualImage = ImageComparison.getImage(actual);
        if (actualImage == null) {
            List<Renderer> renderers = new ArrayList<Renderer>();
            for (Renderer renderer : rendererLoader) {
                renderers.add(renderer);
            }
            throw new UnrenderableException(actual, renderers);
        } else if (expectedImage == null) {
            write(actualImage, outputFilename);
            throw new ApprovalImageNotFoundException(outputFilename);
        } else {
            return matchesImage(actualImage, expectedImage, outputFilename);
        }
    }

    private static BufferedImage getImage(final Object item) {

        if (item == null) {
            return null;
        }

        if (item instanceof BufferedImage) {
            return (BufferedImage) item;
        }

        for (final Renderer r : rendererLoader) {
            final BufferedImage rendering = r.getImage(item);
            if (rendering != null) {
                return rendering;
            }
        }

        return null;
    }
}
