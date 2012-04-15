package net.avh4.util;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.StringDescription;
import org.hamcrest.TypeSafeDiagnosingMatcher;

public class MatcherMatcher extends TypeSafeDiagnosingMatcher<Matcher<?>> {
    private final Object item;
    private String expectedMismatchDescription;

    public static MatcherMatcher doesNotMatch(Object item) {
        return new MatcherMatcher(item);
    }

    public MatcherMatcher(Object item) {
        this.item = item;
    }

    public static String getDescription(Matcher<?> matcher) {
        final StringDescription description = new StringDescription();
        matcher.describeTo(description);
        return description.toString();
    }

    public static String getMismatchDescription(Matcher<?> matcher, Object item) {
        final StringDescription description = new StringDescription();
        matcher.describeMismatch(item, description);
        return description.toString();
    }

    public MatcherMatcher withDescription(String mismatchDescription) {
        this.expectedMismatchDescription = mismatchDescription;
        return this;
    }

    @Override
    protected boolean matchesSafely(Matcher<?> matcher, Description mismatchDescription) {
        if (matcher.matches(item)) {
            mismatchDescription.appendText("was a Matcher that matches ");
            mismatchDescription.appendValue(item);
            return false;
        }
        if (expectedMismatchDescription != null
                && !expectedMismatchDescription.equals(getMismatchDescription(matcher, item))) {
            mismatchDescription.appendText("was a Matcher with mismatch description ");
            mismatchDescription.appendValue(getMismatchDescription(matcher, item));
            return false;
        }
        return true;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("a Matcher that does not match ");
        description.appendValue(item);
        if (expectedMismatchDescription != null) {
            description.appendText(" with mismatch description ");
            description.appendValue(expectedMismatchDescription);
        }
    }


}
