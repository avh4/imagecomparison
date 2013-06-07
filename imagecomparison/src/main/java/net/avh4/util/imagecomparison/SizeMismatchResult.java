package net.avh4.util.imagecomparison;

import java.awt.image.BufferedImage;

public class SizeMismatchResult extends ImageComparisonResult {
	private final BufferedImage itemImage;
	private final int actualWidth;
	private final int actualHeight;
	private final int expectedWidth;
	private final int expectedHeight;

	public SizeMismatchResult(BufferedImage itemImage, int actualWidth,
			int actualHeight, int expectedWidth, int expectedHeight) {
		this.itemImage = itemImage;
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
	public ImageMismatchException getException() {
		return new ImageSizeMismatchException(itemImage, actualWidth,
				actualHeight, expectedWidth, expectedHeight);
	}

	@Override
	public String toString() {
		return "SizeMismatchResult{" +
				"itemImage=" + itemImage +
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
		if (itemImage != null ? !itemImage.equals(that.itemImage) :
				that.itemImage != null) {
			return false;
		}

		return true;
	}

	@Override
	public int hashCode() {
		int result = itemImage != null ? itemImage.hashCode() : 0;
		result = 31 * result + actualWidth;
		result = 31 * result + actualHeight;
		result = 31 * result + expectedWidth;
		result = 31 * result + expectedHeight;
		return result;
	}
}
