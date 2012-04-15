package net.avh4.util.imagecomparison;

import org.hamcrest.Description;
import org.hamcrest.DiagnosingMatcher;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class LooksLikeMatcher extends DiagnosingMatcher<Object> {

    private final String filename;
    private final BufferedImage referenceImage;
    private final Dimension referenceImageSize;
    private final Class<?> sourceClass;

    public LooksLikeMatcher(String filename) throws IOException {
        super();
        sourceClass = StackUtils.getCallingClass((Class<?>) ImageComparisonMatchers.class);

        this.filename = filename;
        final URL resource = sourceClass.getResource(filename);
        if (resource == null) {
            referenceImage = null;
            referenceImageSize = null;
        } else {
            referenceImage = ImageIO.read(resource);

            referenceImageSize = new Dimension(referenceImage.getWidth(),
                    referenceImage.getHeight());
        }
    }

    @Override
    protected boolean matches(Object item, Description mismatchDescription) {
        try {
            return ImageComparison.matches(item, referenceImage, filename);
        } catch (UnrenderableException e) {
            mismatchDescription.appendText("don't know how to make an image of ");
            mismatchDescription.appendValue(item);
            return false;
        } catch (ApprovalImageNotFoundException e) {
            mismatchDescription.appendText("approval image ");
            mismatchDescription.appendText(filename);
            mismatchDescription.appendText(" doesn't exist");
            return false;
        }
    }

    public void describeTo(Description description) {
        if (referenceImage == null) {
            description.appendText(String.format(
                    "reference image \"%s\" to exist in %s", filename,
                    sourceClass.getPackage().getName()));
        } else {
            description.appendText(String.format(
                    "something that looks like %s (%dx%d)", filename,
                    referenceImageSize.width, referenceImageSize.height));
        }
    }

}