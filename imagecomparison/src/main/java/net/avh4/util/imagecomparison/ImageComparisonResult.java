package net.avh4.util.imagecomparison;

public abstract class ImageComparisonResult {

	public static final ImageComparisonResult SUCCESS = new ImageComparisonResult() {
		@Override
		public boolean isEqual() {
			return true;
		}

		@Override
		public ImageMismatchException getException() {
			return null;
		}
	};

	public abstract boolean isEqual();

	public abstract ImageMismatchException getException();
}
