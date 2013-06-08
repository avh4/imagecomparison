package net.avh4.util.imagecomparison;

import java.awt.image.BufferedImage;

public class SizeMismatchResult extends ImageComparisonResult {
    private final int actualWidth;
    private final int actualHeight;
    private final int expectedWidth;
    private final int expectedHeight;

    public SizeMismatchResult(BufferedImage actualImage, int actualWidth,
                              int actualHeight, int expectedWidth, int expectedHeight) {
        super(actualImage);
        this.actualWidth = actualWidth;
        this.actualHeight = actualHeight;
        this.expectedWidth = expectedWidth;
        this.expectedHeight = expectedHeight;
    }

    @Override
    public boolean isEqual() {
        return false;
    }

    @Override
    public String getFailureMessage() {
        return String.format("expected %d x %d, but got %d x %d",
                expectedWidth, expectedHeight, actualWidth, actualHeight);
    }

    @Override
    public String toString() {
        return "SizeMismatchResult{" +
                "actualImage=" + actualImage +
                ", actualWidth=" + actualWidth +
                ", actualHeight=" + actualHeight +
                ", expectedWidth=" + expectedWidth +
                ", expectedHeight=" + expectedHeight +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final SizeMismatchResult that = (SizeMismatchResult) o;

        if (actualHeight != that.actualHeight) return false;
        if (actualWidth != that.actualWidth) return false;
        if (expectedHeight != that.expectedHeight) return false;
        if (expectedWidth != that.expectedWidth) return false;
        if (actualImage != null ? !actualImage.equals(that.actualImage) :
                that.actualImage != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = actualImage != null ? actualImage.hashCode() : 0;
        result = 31 * result + actualWidth;
        result = 31 * result + actualHeight;
        result = 31 * result + expectedWidth;
        result = 31 * result + expectedHeight;
        return result;
    }
}
