package net.avh4.util.imagecomparison;

import org.apache.commons.io.IOUtils;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

/**
 * A class containing factory methods for hamcrest matchers contained in
 * {@code net.avh4.util.imagecomparison}.
 * 
 * @author avh4
 */
public class ImageComparisonMatchers {

	@Factory
	public static Matcher<Object> looksLike(final String string)
			throws IOException {
		return new LooksLikeMatcher(string);
	}

	@Factory
	public static Matcher<Object> isApproved() throws IOException {
		final StackTraceElement trace = StackUtils
				.getCallingTestMethodStackTraceElement();
		final String methodName = trace.getMethodName();
        Class<?> aClass = StackUtils.getClass(trace);
        final String className = getClassName(aClass);
		return looksLike(String.format("%s.%s.png", className, methodName));
	}

    private static String getClassName(Class<?> aClass) {
        return aClass.getCanonicalName().replaceAll("^[a-z0-9.]*", "");
    }

    @Factory
	public static Matcher<String> matchesFile(final String filename)
			throws IOException {
		final Class<?> clazz = StackUtils.getCallingClass();
		final InputStream is = clazz.getResourceAsStream(filename);
		StringWriter sw = null;
		if (is != null) {
			sw = new StringWriter();
			IOUtils.copy(is, sw);
		}
		final String expected = sw == null ? null : sw.toString();
		return new TypeSafeMatcher<String>() {

			@Override
			public boolean matchesSafely(final String actual) {
				if (actual.equals(expected)) {
					return true;
				} else {
					try {
						final FileOutputStream fo = new FileOutputStream(
								filename);
						IOUtils.write(actual, fo);
					} catch (final IOException e) {
						e.printStackTrace();
					}
					return false;
				}
			}

			@Override
			public void describeTo(final Description arg0) {
				arg0.appendText("a String matching " + filename);
			}
		};
	}
}
