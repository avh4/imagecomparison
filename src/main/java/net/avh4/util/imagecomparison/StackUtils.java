package net.avh4.util.imagecomparison;

import java.lang.reflect.Method;

import org.junit.Test;

abstract class StackUtils {

	public static Class<?> getCallingClass(final Class<?>... alsoSkippable) {
		final StackTraceElement callingStackTraceElement = getCallingStackTraceElement(alsoSkippable);
		return getClass(callingStackTraceElement);
	}

	private static Class<?> getClass(final StackTraceElement stackTraceElement) {
		Class<?> sourceClass;
		try {
			sourceClass = Class.forName(stackTraceElement.getClassName());
		} catch (final ClassNotFoundException e) {
			throw new RuntimeException(
					"Could not load resources for test class: "
							+ stackTraceElement.getClassName(), e);
		}
		return sourceClass;
	}

	public static StackTraceElement getCallingStackTraceElement(
			final Class<?>... alsoSkippable) {
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

	private static boolean isSkippable(
			final StackTraceElement stackTraceElement,
			final Class<?>... alsoSkippable) {
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

	public static StackTraceElement getCallingTestMethodStackTraceElement() {
		final StackTraceElement[] stackTrace = Thread.currentThread()
				.getStackTrace();
		int i = 0;
		while (!isTestMethod(stackTrace[i])) {
			i++;
		}
		final StackTraceElement callingStackTraceElement = stackTrace[i];
		return callingStackTraceElement;
	}

	private static boolean isTestMethod(
			final StackTraceElement stackTraceElement) {
		final Class<?> clazz = getClass(stackTraceElement);
		try {
			final Method method = clazz.getMethod(stackTraceElement
					.getMethodName());
			final Test annotation = method.getAnnotation(Test.class);
			if (annotation == null) {
				return false;
			} else {
				return true;
			}
		} catch (final SecurityException e) {
			throw new RuntimeException("Couldn't read stack trace information",
					e);
		} catch (final NoSuchMethodException e) {
			// This will happen often since we are trying to get a method with
			// the method name take from the stack trace with no parameters. So
			// any method calls in the stack trace that do take parameters will
			// trigger this exception. (But this is safe to silently ignore,
			// since any method that has parameters will not be a test method.)
			return false;
		}
	}
}