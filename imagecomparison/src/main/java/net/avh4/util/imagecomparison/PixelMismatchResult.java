package net.avh4.util.imagecomparison;

import java.awt.image.BufferedImage;

public class PixelMismatchResult extends ImageComparisonResult {
    private final int firstMismatchX;
    private final int firstMismatchY;
    private final int actualPixel;
    private final int expectedPixel;

    public PixelMismatchResult(BufferedImage itemImage, int firstMismatchX,
                               int firstMismatchY, int actualPixel, int expectedPixel) {
        super(itemImage);
        this.firstMismatchX = firstMismatchX;
        this.firstMismatchY = firstMismatchY;
        this.actualPixel = actualPixel;
        this.expectedPixel = expectedPixel;
    }

    @Override
    public boolean isEqual() {
        return false;
    }

    @Override
    public String getFailureMessage() {
        return String.format(
                "First incorrect pixel was (%d, %d): expected 0x%x, but got 0x%x",
                firstMismatchX, firstMismatchY, expectedPixel, actualPixel);
    }

    @Override
    public String toString() {
        return "PixelMismatchResult{" +
                "actualImage=" + actualImage +
                ", firstMismatchX=" + firstMismatchX +
                ", firstMismatchY=" + firstMismatchY +
                ", actualPixel=" + actualPixel +
                ", expectedPixel=" + expectedPixel +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final PixelMismatchResult that = (PixelMismatchResult) o;

        if (actualPixel != that.actualPixel) return false;
        if (expectedPixel != that.expectedPixel) return false;
        if (firstMismatchX != that.firstMismatchX) return false;
        if (firstMismatchY != that.firstMismatchY) return false;
        if (actualImage != null ? !actualImage.equals(that.actualImage) :
                that.actualImage != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = actualImage != null ? actualImage.hashCode() : 0;
        result = 31 * result + firstMismatchX;
        result = 31 * result + firstMismatchY;
        result = 31 * result + actualPixel;
        result = 31 * result + expectedPixel;
        return result;
    }
}
