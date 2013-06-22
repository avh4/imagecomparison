package net.avh4.util.imagecomparison;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class LooksLikeHelper {
    private final String filename;
    private final BufferedImage referenceImage;
    private final Class<?> sourceClass;
    private boolean matches;
    private StringBuffer mismatchDescription;

    public LooksLikeHelper(String resourceName, Class<?> callingClass) throws IOException {
        sourceClass = callingClass;

        this.filename = resourceName;
        final URL resource = sourceClass.getResource(filename);
        if (resource == null) {
            referenceImage = null;
        } else {
            referenceImage = ImageIO.read(resource);
        }
    }

    public boolean matches() {
        return matches;
    }

    public String mismatchDescription() {
        return mismatchDescription.toString();
    }

    public void checkAndWriteFailingImage(Object item) {
        final ImageComparisonResult result = ImageComparison.compare(item, referenceImage);
        mismatchDescription = new StringBuffer();
        matches = false;
        if (result.isEqual()) {
            matches = true;
        } else if (referenceImage == null) {
            mismatchDescription.append("approval image ");
            mismatchDescription.append(filename);
            mismatchDescription.append(" doesn't exist -- expected to find it in ");
            mismatchDescription.append(sourceClass.getPackage().getName());
            result.writeActualImageToFile(filename);
        } else {
            mismatchDescription.append("images don't match: ");
            mismatchDescription.append(result.getFailureMessage());
            result.writeActualImageToFile(filename);
        }
    }

    public String selfDescription() {
        if (referenceImage == null) {
            return String.format("reference image \"%s\" to exist in %s",
                    filename, sourceClass.getPackage().getName());
        } else {
            return String.format("something that looks like %s (%dx%d)",
                    filename, referenceImage.getWidth(),
                    referenceImage.getHeight());
        }
    }
}
