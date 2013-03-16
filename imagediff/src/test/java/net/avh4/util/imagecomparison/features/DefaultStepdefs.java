package net.avh4.util.imagecomparison.features;

import cucumber.annotation.en.Given;
import cucumber.annotation.en.Then;
import cucumber.annotation.en.When;
import net.avh4.util.sandbox.Sandbox;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;

import java.io.File;

import static net.avh4.util.imagecomparison.ImageComparisonMatchers.looksLike;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SuppressWarnings("UnusedDeclaration")
public class DefaultStepdefs {
    private Agent agent;
    private Sandbox sandbox;
    private String actualFileName;
    private String expectedFileName;
    private byte[] actualFileContent;

    public DefaultStepdefs() {
        sandbox = new Sandbox();
        agent = new Agent(sandbox);
    }

    @Given("^two slightly different image files \"([^\"]*)\" and \"([^\"]*)\"$")
    public void givenTwoSlightlyDifferentImageFiles(String fileA, String fileB) {
        sandbox.useResource(fileA);
        sandbox.useResource(fileB);
    }

    @When("^I launch \"imagediff ([^ ]*) ([^ ]*)\"$")
    public void whenILaunchimagediffFiles(final String fileA,
                                          final String fileB) {
        agent.launchImagediff(fileA, fileB);
    }

    @Given("^a java project with a mismatched approval$")
    public void a_java_project_with_a_mismatched_approval() throws Throwable {
        actualFileName = "MyApp.initialState.png";
        expectedFileName = "src/test/resources/com/example/MyApp.initialState.png";
        sandbox.useResource("actual.png", actualFileName);
        sandbox.useResource("expected.png", expectedFileName);
        File newFile = new File(sandbox.getRoot(), actualFileName);
        actualFileContent = FileUtils.readFileToByteArray(newFile);
    }

    @When("^I launch \"imagediff\"$")
    public void I_launch_imagediff() throws Throwable {
        agent.launchImagediff();
    }

    @When("^I click the display$")
    public void whenIClickTheDisplay() {
        agent.clickViewingArea();
    }

    @Then("^I should see (.*)$")
    public void thenIShouldSeeADisplayOfActualpngWithTheDifferencesFromExpectedpngHighlighted(
            final String what) throws Exception {
        final String hash = DigestUtils.shaHex(what);
        assertThat(agent.getUi(), looksLike("/" + hash + ".png"));
    }

    @When("^choose to accept the new image$")
    public void choose_to_accept_the_new_image() throws Throwable {
        agent.approve();
    }

    @Then("^the new image will replace the old image in the filesystem$")
    public void the_new_image_will_replace_the_old_image_in_the_filesystem() throws Throwable {
        File oldFile = new File(sandbox.getRoot(), expectedFileName);
        byte[] expectedFileContent = FileUtils.readFileToByteArray(oldFile);
        assertThat(actualFileContent, is(expectedFileContent));
    }
}
