package net.avh4.util.imagecomparison;

import java.awt.image.BufferedImage;

public class ImageMismatchException extends Exception {
    private final BufferedImage itemImage;

    public ImageMismatchException(BufferedImage itemImage, int firstMismatchX, int firstMismatchY,
                                  int actualPixel, int expectedPixel) {
        this(itemImage, String.format("First incorrect pixel was (%d, %d): expected 0x%x, but got 0x%x",
                firstMismatchX, firstMismatchY, expectedPixel, actualPixel));
    }

    protected ImageMismatchException(BufferedImage itemImage, String message) {
        super(message);
        this.itemImage = itemImage;
    }

    public void writeActualImageToFile(String filename) {
        ImageComparison.write(itemImage, filename);
    }
}
