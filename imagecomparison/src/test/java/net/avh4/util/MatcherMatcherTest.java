package net.avh4.util;

import static net.avh4.util.MatcherMatcher.*;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.core.Is.*;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.core.IsEqual;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@RunWith(Enclosed.class)
public class MatcherMatcherTest {

	public static class doesNotMatch {
		private MatcherMatcher matcherMatcher;

		@Before
		public void setUp() throws Exception {
			matcherMatcher = MatcherMatcher.doesNotMatch("Expected");
		}

		@Test
		public void withAMatcherThatDoesntMatch_shouldMatch() throws Exception {
			final Matcher<String> testMatcher = is("Actual");
			assertThat(matcherMatcher.matches(testMatcher), is(true));
		}

		@Test
		public void withAMatcherThatDoesMatch_shouldNotMatch()
				throws Exception {
			final Matcher<String> testMatcher = is("Expected");
			assertThat(matcherMatcher.matches(testMatcher), is(false));
			assertThat(getMismatchDescription(matcherMatcher, testMatcher),
					is("was a Matcher that matches \"Expected\""));
		}

		@Test
		public void shouldDescribeExpectation() throws Exception {
			assertThat(getDescription(matcherMatcher),
					is("a Matcher that does not match \"Expected\""));
		}
	}

	public static class doesNotMatch_withDescription {
		private MatcherMatcher matcherMatcher;

		@Before
		public void setUp() throws Exception {
			matcherMatcher = MatcherMatcher.doesNotMatch("Expected")
										   .withDescription(
												   "was an object described by this custom mismatch description");
		}

		@Test
		public void withAMatcherThatGivesTheWrongMismatchDescription_shouldNotMatch()
				throws Exception {
			final Matcher<String> testMatcher = is("Actual");
			assertThat(matcherMatcher.matches(testMatcher), is(false));
			assertThat(getMismatchDescription(matcherMatcher, testMatcher),
					is("was a Matcher with mismatch description \"was \\\"Expected\\\"\""));
		}

		@Test
		public void withAMatcherThatGivesTheCorrectMismatchDescription_shouldMatch()
				throws Exception {
			final Matcher<String> testMatcher = new IsEqual<String>("Actual") {
				@Override
				public void describeMismatch(Object item,
						Description description) {
					description.appendText(
							"was an object described by this custom mismatch description");
				}
			};
			assertThat(matcherMatcher.matches(testMatcher), is(true));
		}

		@Test
		public void shouldDescribeExpectation() throws Exception {
			assertThat(getDescription(matcherMatcher),
					is("a Matcher that does not match \"Expected\" "
							+ "with mismatch description \"was an object described by this custom mismatch description\""));
		}
	}
}
