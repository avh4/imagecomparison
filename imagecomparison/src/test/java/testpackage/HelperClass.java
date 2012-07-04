package testpackage;

import net.avh4.util.imagecomparison.ImageComparisonMatchers;
import org.hamcrest.Matcher;

import java.io.IOException;

public class HelperClass {
    public static Matcher<Object> helperMethod() throws IOException {
        return ImageComparisonMatchers.isApproved();
    }
}
