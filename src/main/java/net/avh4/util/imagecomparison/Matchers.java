package net.avh4.util.imagecomparison;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

import org.apache.commons.io.IOUtils;
import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;

/**
 * A class containing factory methods for hamcrest matchers contained in
 * {@code net.avh4.test}.
 * 
 * @author avh4
 */
public class Matchers {

	@Factory
	public static Matcher<Object> looksLike(String string) throws IOException {
		return new LooksLikeMatcher(string);
	}

	@Factory
	public static Matcher<Object> isApproved() throws IOException {
		final StackTraceElement trace = StackUtils
				.getCallingStackTraceElement();
		final String methodName = trace.getMethodName();
		final String className = StackUtils.getCallingClass().getSimpleName();
		return looksLike(String.format("%s.%s.png", className, methodName));
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
			public boolean matchesSafely(String actual) {
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

			public void describeTo(Description arg0) {
				arg0.appendText("a String matching " + filename);
			}
		};
	}
}
