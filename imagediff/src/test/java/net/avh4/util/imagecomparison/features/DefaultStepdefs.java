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

	@Given("^two slightly different image files \"expected.png\" and \"actual.png\"$")
	public void givenTwoSlightlyDifferentImageFilesexpectedpngAndactualpng() {
	}

	@When("^I launch \"imagediff ([^ ]*) ([^ ]*)\"$")
	public void whenILaunchimagediffExpectedpngActualpng(final String fileA,
			final String fileB) {
		final Sandbox sandbox = new Sandbox();
		sandbox.useResource(fileA);
		sandbox.useResource(fileB);
		ui = ImageDiff.launch(sandbox.getRoot(), fileA, fileB);
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
