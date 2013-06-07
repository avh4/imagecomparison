package net.avh4.util.imagecomparison;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageMismatchException extends Exception {
	private final BufferedImage itemImage;

	public ImageMismatchException(BufferedImage itemImage, int firstMismatchX,
			int firstMismatchY, int actualPixel, int expectedPixel) {
		this(itemImage, String.format(
				"First incorrect pixel was (%d, %d): expected 0x%x, but got 0x%x",
				firstMismatchX, firstMismatchY, expectedPixel, actualPixel));
	}

	protected ImageMismatchException(BufferedImage itemImage, String message) {
		super(message);
		this.itemImage = itemImage;
	}

	private static void write(final BufferedImage image,
			final String filename) {
		final String safeFilename = filename.replaceFirst("^.*/+", "");
		try {
			ImageIO.write(image, "png", new File(safeFilename));
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	public void writeActualImageToFile(String filename) {
		write(itemImage, filename);
	}
}
