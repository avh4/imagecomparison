package net.avh4.util.imagecomparison.hamcrest;

import net.avh4.util.imagecomparison.LooksLikeHelper;
import net.avh4.util.reflection.StackUtils;
import org.hamcrest.Description;
import org.hamcrest.DiagnosingMatcher;

import java.io.IOException;

public class LooksLikeMatcher extends DiagnosingMatcher<Object> {

    private final LooksLikeHelper helper;

    public LooksLikeMatcher(String resourceName) throws IOException {
        this(resourceName, StackUtils.getCallingClass(ImageComparisonMatchers.class));
    }

    public LooksLikeMatcher(String resourceName, Class<?> callingClass)
            throws IOException {
        helper = new LooksLikeHelper(resourceName, callingClass);
    }

    @Override
    protected boolean matches(Object item, Description mismatchDescription) {
        helper.checkAndWriteFailingImage(item);
        if (helper.matches()) {
            return true;
        } else {
            mismatchDescription.appendText(helper.mismatchDescription());
            return false;
        }
    }

    @Override
    public void describeTo(Description description) {
        description.appendText(helper.selfDescription());
    }
}