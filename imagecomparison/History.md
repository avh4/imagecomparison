## [0.0.7-SNAPSHOT](https://github.com/avh4/imagecomparison/compare/magecomparison-0.0.6...master)

* 3rd-party libraries can now provide their own `Renderer` implementations
  using the `ServiceLoader` pattern.
* Don't throw an exception if null is passed to a `Renderer`.
* isApproved() matcher will get the correct class name when called from an 
  inner static class.
* Added IntelliJ project files.

