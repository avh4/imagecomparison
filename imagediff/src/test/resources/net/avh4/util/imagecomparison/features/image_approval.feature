Feature: approving images in a java project

Scenario: opening a folder with a single approval image

    Given a java project with a mismatched approval
    When I launch "imagediff"
    Then I should see the comparison of the mismatched images
