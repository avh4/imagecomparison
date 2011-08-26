package net.avh4.util.imagecomparison;

abstract class StackUtils {

	public static Class<?> getCallingClass(Class<?>... alsoSkippable) {
		final StackTraceElement callingStackTraceElement = getCallingStackTraceElement(alsoSkippable);
		Class<?> sourceClass;
		try {
			sourceClass = Class
					.forName(callingStackTraceElement.getClassName());
		} catch (final ClassNotFoundException e) {
			throw new RuntimeException(
					"Could not load resources for test class: "
							+ callingStackTraceElement.getClassName(), e);
		}
		return sourceClass;
	}

	public static StackTraceElement getCallingStackTraceElement(
			Class<?>... alsoSkippable) {
		final StackTraceElement[] stackTrace = Thread.currentThread()
				.getStackTrace();
		int i = 0;
		while (isSkippable(stackTrace[i], alsoSkippable)) {
			i++;
		}
		i++;
		while (isSkippable(stackTrace[i], alsoSkippable)) {
			i++;
		}
		final StackTraceElement callingStackTraceElement = stackTrace[i];
		return callingStackTraceElement;
	}

	private static boolean isSkippable(StackTraceElement stackTraceElement,
			Class<?>... alsoSkippable) {
		if (stackTraceElement.getClassName().equals(
				Thread.class.getCanonicalName())) {
			return true;
		} else if (stackTraceElement.getClassName().equals(
				StackUtils.class.getCanonicalName())) {
			return true;
		} else {
			for (final Class<?> skippable : alsoSkippable) {
				if (stackTraceElement.getClassName().equals(
						skippable.getCanonicalName())) {
					return true;
				}
			}
			return false;
		}
	}
}