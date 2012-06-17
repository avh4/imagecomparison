# Architecture (imagecomparison)

This document is a place to record discussions and thoughts about architecture
decisions made on the project.

## Allowing other libraries to add `Renderer`s (2012-06-16)

This issue was revisited and found that the `ServiceLoader` approach could be
used without requiring any additional configuration by the consumers.


## Rendering uilayer `Scene`s as text (2012-06-16)

`Scene`s in uilayer should be saved and compared as some kind of text file
rather than as an image file so that difference in the JRE rendering on
different platforms will not cause test failures. This is not entirely
imagecomparison's concern, but it is addressed here because the
UILayerRenderer is currently packaged in imagecomparison.

In solving this, it would be ideal to push some of the responsibility for
rendering [uilayer][] `Scene`s down into uilayer, and possibly eliminate all
reference to uilayer classes from imagecomparison.

### Conclusion

It was found that [HDE][]'s tests could be made to pass on CI by making sure
all development environments used OpenJDK-1.7 so that font rendering would
produce the same results between dev environments and CI.

Finding a text-based approach to approving `Scene`s will be delayed.


## Allowing other libraries to add `Renderer`s (2011-09-26)

We want to be able to compare objects that are not known to imagecomparison.
There needs to be a way to render and compare instances of classes that
imagecomparison cannot depend on.

### `ServiceLoader`

Java provides the [ServiceLoader][1] API for allowing external libraries to
provide implementations of an interface. The drawback is that libraries
providing implementations of the interface must have a META-INF file
specifying this.

Note (2012-06-16): When this was originally investigated, it was thought that
using this approach would require any users of the library to have a
properties file that listed the provided implementations of the interface, but
revisiting this, it appears that this only requires the libraries to have a
file in META-INF and requires no additional configuration by the consumers.
This should perhaps be revisited as a better solution.

[1]: http://docs.oracle.com/javase/6/docs/api/java/util/ServiceLoader.html

### Searching the classpath for implementations

On initialization, a search of the classpath could be performed to find
implementations of the interface. This turns out to be difficult to do in Java
because there is no definitive list of what's available on the classpath due
to the way `ClassLoader`s work. There are libraries to assist with this, but
the solution overall is slow and complicated.  [[2]][2] [[3]][3]

[2]: http://stackoverflow.com/questions/435890/find-java-classes-implementing-an-interface
[3]: http://stackoverflow.com/questions/176527/how-can-i-enumerate-all-classes-in-a-package-and-add-them-to-a-list

### Conclusion

For now we are only concerned about integration with [uilayer][]. The approach
to be used for now is to have specialized `Renderer`s that live in
imagecomparison that know how to render the objects in question. However,
since imagecomparison cannot have a runtime dependency on uilayer, we use
reflection when implementing `UILayerRenderer`.

This is not the ideal solution since imagecomparison now depends on uilayer
(although there is no runtime or compile-time dependency). However this
solution was chosen because it does not require any additional configuration
when imagecomparison is used in another project--it will work simply by
including the uilayer-swing jar.

[uilayer]: http://github.com/avh4/uilayer "uilayer"
[HDE]: http://github.com/avh4/hde "HDE"