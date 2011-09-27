package net.avh4.util.imagecomparison;

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Window;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

public class ImageComparison {

	public static boolean matchesImage(final BufferedImage itemImage,
			final BufferedImage referenceImage, final String filename) {
		// Compare the image sizes
		if (itemImage.getWidth() != referenceImage.getWidth()
				|| itemImage.getHeight() != referenceImage.getHeight()) {
			write(itemImage, filename);
			return false;
		}

		// Compare the image data
		final Raster itemRaster = itemImage.getData();
		final Raster referenceRaster = referenceImage.getData();
		final int width = itemRaster.getWidth();
		final int height = itemRaster.getHeight();
		for (int y = 0; y < height; y++) {
			final int itemPixels[] = new int[4 * width];
			final int referencePixels[] = new int[4 * width];
			itemRaster.getPixels(0, y, width, 1, itemPixels);
			referenceRaster.getPixels(0, y, width, 1, referencePixels);
			for (int i = 0; i < 4 * width; i++) {
				if (itemPixels[i] != referencePixels[i]) {
					write(itemImage, filename);
					return false;
				}
			}
		}

		return true;
	}

	private static BufferedImage read(final String imageName) {
		try {
			return ImageIO.read(new File(imageName));
		} catch (final IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	private static void write(final BufferedImage image, final String filename) {
		try {
			ImageIO.write(image, "png", new File(filename));
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	public static boolean matches(final Object item,
			final String referenceFilename, final String outputFilename) {
		final BufferedImage expectedImage = read(referenceFilename);
		return matches(item, expectedImage, outputFilename);
	}

	public static boolean matches(final Object actual,
			final BufferedImage expectedImage, final String outputFilename) {
		final BufferedImage actualImage = ImageComparison.getImage(actual);
		if (actualImage == null) {
			return false;
		} else if (expectedImage == null) {
			write(actualImage, outputFilename);
			return false;
		} else {
			return matchesImage(actualImage, expectedImage, outputFilename);
		}
	}

	private static BufferedImage drawComponent(final Component c) {
		validateComponent(c);
		final BufferedImage image = new BufferedImage(c.getWidth(),
				c.getHeight(), BufferedImage.TYPE_INT_ARGB);
		final Graphics2D g = image.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_OFF);
		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
				RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
		c.paint(g);
		g.dispose();
		return image;
	}

	// This code remains here until we can refactor to move this into the
	// net.avh4.gui project.
	// private static BufferedImage drawView(final View view) {
	// final Dimension size = view.getPreferredSize();
	// final BufferedImage image = new BufferedImage(size.width, size.height,
	// BufferedImage.TYPE_INT_ARGB);
	// final Graphics2D g = image.createGraphics();
	// view.paint(g);
	// g.dispose();
	// return image;
	// }

	private static void validateComponent(final Component c) {
		if (!c.isValid()) {
			if (c instanceof Window) {
				final Window c1 = (Window) c;
				c1.pack();
			} else {
				c.setSize(c.getPreferredSize());
			}
		}
	}

	private static BufferedImage getImage(final Object item) {
		if (item instanceof JFrame) {
			return drawComponent(((JFrame) item).getContentPane());
		} else if (item instanceof Component) {
			return drawComponent((Component) item);
		} else if (item instanceof BufferedImage) {
			return (BufferedImage) item;
			// This code remains here until we can refactor to move this into
			// the net.avh4.gui project.
			// } else if (item instanceof View) {
			// return drawView((View) item);
		} else {
			throw new RuntimeException("Don't know how to make an image of "
					+ item);
		}
	}

}
