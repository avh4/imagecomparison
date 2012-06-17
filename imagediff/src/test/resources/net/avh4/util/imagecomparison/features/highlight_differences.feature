Feature: Highlight image differences
  In order to debug problems in my rendering code
  As a software developer
  I want to see the tiniest differences between two images highlighted

Scenario: Product backlog example
  Given two slightly different image files "expected.png" and "actual.png"
  When I launch "imagediff expected.png actual.png"
  Then I should see a display of actual.png with the differences from expected.png highlighted

Scenario: Turn off highlight
  Given two slightly different image files "expected.png" and "actual.png"
  When I launch "imagediff expected.png actual.png"
  And I click the display
  Then I should see a display of actual.png and expected.png side-by-side