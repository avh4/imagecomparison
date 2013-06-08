package net.avh4.util.imagecomparison;

import java.awt.image.BufferedImage;

class ImageComparisonSuccess extends ImageComparisonResult {

    public ImageComparisonSuccess(BufferedImage actualImage) {
        super(actualImage);
    }

    @Override
    public boolean isEqual() {
        return true;
    }

    @Override
    public String getFailureMessage() {
        return null;
    }

    @Override
    public String toString() {
        return "ImageComparisonSuccess{" +
                "actualImage=" + actualImage +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final ImageComparisonSuccess that = (ImageComparisonSuccess) o;

        if (actualImage != null ? !actualImage.equals(that.actualImage) :
                that.actualImage != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = actualImage != null ? actualImage.hashCode() : 0;
        return result;
    }
}
