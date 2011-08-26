java_import 'net.avh4.util.imagecomparison.ImageDiff'

Given /^two slightly different image files "expected.png" and "actual.png"$/ do
end

When /^I launch "imagediff expected.png actual.png"$/ do
  @ui = ImageDiff.launch("src/test/resources/expected.png", "src/test/resources/actual.png")
end

Then /^I should see (.*)$/ do |what|
  @ui.should look_like what
end
