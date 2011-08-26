package net.avh4.util.imagecomparison;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

public class LooksLikeMatcher extends BaseMatcher<Object> {

	private final String filename;
	private final BufferedImage referenceImage;
	private final Dimension referenceImageSize;
	private final Class<?> sourceClass;

	public LooksLikeMatcher(String filename) throws IOException {
		sourceClass = StackUtils.getCallingClass((Class<?>) Matchers.class);

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

	public boolean matches(Object item) {
		return ImageComparison.matches(item, referenceImage, filename);
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