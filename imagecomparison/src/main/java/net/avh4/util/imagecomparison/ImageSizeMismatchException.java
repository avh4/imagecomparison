package net.avh4.util.imagecomparison;

import java.awt.image.BufferedImage;

public class ImageSizeMismatchException extends ImageMismatchException {
    public ImageSizeMismatchException(BufferedImage itemImage, int actualWidth, int actualHeight,
                                      int expectedWidth, int expectedHeight) {
        super(itemImage, String.format("expected %d x %d, but got %d x %d",
                expectedWidth, expectedHeight, actualWidth, actualHeight));
    }
}
