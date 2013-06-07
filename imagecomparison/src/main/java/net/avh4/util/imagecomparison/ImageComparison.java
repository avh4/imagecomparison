package net.avh4.util.imagecomparison;

import net.avh4.util.imagerender.ImageRenderer;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;

public class ImageComparison {

    public static void assertImagesMatch(final Object actual,
                                         final Object expected) throws ImageMismatchException {
        ImageComparisonResult result = compare(actual, expected);
        if (!result.isEqual()) {
            throw result.getException();
        }
    }

    public static ImageComparisonResult compare(Object actual, Object reference) {
        final BufferedImage itemImage = ImageRenderer.getImage(actual);
        if (reference == null) {
            return new NoReferenceImageResult(itemImage);
        }

        final BufferedImage referenceImage = ImageRenderer.getImage(reference);

        // Compare the image sizes
        if (itemImage.getWidth() != referenceImage.getWidth()
                || itemImage.getHeight() != referenceImage.getHeight()) {
            return new SizeMismatchResult(itemImage, itemImage.getWidth(),
                    itemImage.getHeight(), referenceImage.getWidth(),
                    referenceImage.getHeight());
        }

        // Validate the color models
        for (int size : itemImage.getColorModel().getComponentSize()) {
            if (size > 8) {
                throw new RuntimeException(
                        "Don't know how to handle images with more than 8 bits per channel");
            }
        }
        for (int size : referenceImage.getColorModel().getComponentSize()) {
            if (size > 8) {
                throw new RuntimeException(
                        "Don't know how to handle reference images with more than 8 bits per channel");
            }
        }
        final Raster itemRaster = itemImage.getData();
        final Raster referenceRaster = referenceImage.getData();
        final int itemBands = itemRaster.getNumBands();
        final int referenceBands = referenceRaster.getNumBands();
        if (itemBands < 3) {
            throw new RuntimeException(
                    "Don't know how to handle images with less than 3 data bands "
                            + "(expecting RGB or ARGB color model)");
        }
        if (itemBands > 4) {
            throw new RuntimeException(
                    "Don't know how to handle images with more than 4 data bands "
                            + "(expecting RGB or ARGB color model)");
        }
        if (referenceBands < 3) {
            throw new RuntimeException(
                    "Don't know how to handle reference images with less than 3 data bands "
                            + "(expecting RGB or ARGB color model)");
        }
        if (referenceBands > 4) {
            throw new RuntimeException(
                    "Don't know how to handle reference images with more than 4 data bands "
                            + "(expecting RGB or ARGB color model)");
        }

        // Compare the image data
        final int width = itemRaster.getWidth();
        final int height = itemRaster.getHeight();
        final int itemPixels[] = new int[itemBands * width];
        final int referencePixels[] = new int[referenceBands * width];
        for (int y = 0; y < height; y++) {
            itemRaster.getPixels(0, y, width, 1, itemPixels);
            referenceRaster.getPixels(0, y, width, 1, referencePixels);
            for (int x = 0; x < width; x++) {
                int itemAlpha = (itemBands == 4) ?
                        itemPixels[x * itemBands + 3] & 0xff : 0xff;
                int itemPixel = (itemPixels[x * itemBands] & 0xff)
                        | (itemPixels[x * itemBands + 1] & 0xff) << 8
                        | (itemPixels[x * itemBands + 2] & 0xff) << 16
                        | itemAlpha << 24;
                int referenceAlpha = (referenceBands == 4) ?
                        referencePixels[x * referenceBands + 3] & 0xff : 0xff;
                int referencePixel =
                        (referencePixels[x * referenceBands] & 0xff) |
                                (referencePixels[x * referenceBands + 1] & 0xff)
                                        << 8 |
                                (referencePixels[x * referenceBands + 2] & 0xff)
                                        << 16 | referenceAlpha << 24;
                if (itemPixel != referencePixel) {
                    return new PixelMismatchResult(itemImage, x, y, itemPixel,
                            referencePixel);
                }
            }
        }

        return ImageComparisonResult.SUCCESS;
    }
}
