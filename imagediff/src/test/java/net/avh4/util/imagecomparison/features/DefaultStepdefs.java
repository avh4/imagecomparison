package net.avh4.util.imagecomparison.features;

import cucumber.annotation.en.Given;
import cucumber.annotation.en.Then;
import cucumber.annotation.en.When;
import net.avh4.util.imagecomparison.ImageDiff;
import net.avh4.util.sandbox.Sandbox;
import org.apache.commons.codec.digest.DigestUtils;

import java.awt.event.MouseListener;

import static net.avh4.util.imagecomparison.ImageComparisonMatchers.looksLike;
import static org.hamcrest.MatcherAssert.assertThat;

@SuppressWarnings("UnusedDeclaration")
public class DefaultStepdefs {
    private ImageDiff ui;
    private Sandbox sandbox;

    @Given("^two slightly different image files \"([^\"]*)\" and \"([^\"]*)\"$")
    public void givenTwoSlightlyDifferentImageFiles(String fileA, String fileB) {
        sandbox = new Sandbox();
        sandbox.useResource(fileA);
        sandbox.useResource(fileB);
    }

    @When("^I launch \"imagediff ([^ ]*) ([^ ]*)\"$")
    public void whenILaunchimagediffFiles(final String fileA,
                                          final String fileB) {
        ui = ImageDiff.launch(sandbox.getRoot(), fileA, fileB);
    }

    @Given("^a java project with a mismatched approval$")
    public void a_java_project_with_a_mismatched_approval() throws Throwable {
        sandbox = new Sandbox();
        sandbox.useResource("actual.png", "MyApp.initialState.png");
        sandbox.useResource("expected.png", "src/test/resources/com/example/MyApp.initialState.png");
    }

    @When("^I launch \"imagediff\"$")
    public void I_launch_imagediff() throws Throwable {
        ui = ImageDiff.launch(sandbox.getRoot());
    }

    @When("^I click the display$")
    public void whenIClickTheDisplay() {
        final MouseListener[] mouseListeners = ui.getMouseListeners();
        for (final MouseListener mouseListener : mouseListeners) {
            mouseListener.mouseClicked(null);
        }
    }

    @Then("^I should see (.*)$")
    public void thenIShouldSeeADisplayOfActualpngWithTheDifferencesFromExpectedpngHighlighted(
            final String what) throws Exception {
        final String hash = DigestUtils.shaHex(what);
        assertThat(ui, looksLike("/" + hash + ".png"));
    }
}
