package net.avh4.util.imagecomparison;

import java.awt.image.BufferedImage;

public class ReferenceImageNotProvidedException extends ImageMismatchException {
    public ReferenceImageNotProvidedException(BufferedImage actualImage) {
        super(actualImage, "reference image was null");
    }
}
