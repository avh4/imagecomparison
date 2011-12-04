package net.avh4.util.imagecomparison.features;

import static org.hamcrest.MatcherAssert.assertThat;

import java.awt.event.MouseListener;

import net.avh4.util.imagecomparison.ImageDiff;
import net.avh4.util.imagecomparison.Matchers;
import net.avh4.util.sandbox.Sandbox;

import org.apache.commons.codec.digest.DigestUtils;

import cucumber.annotation.en.Given;
import cucumber.annotation.en.Then;
import cucumber.annotation.en.When;

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
		assertThat(ui, Matchers.looksLike("/" + hash + ".png"));
	}
}
