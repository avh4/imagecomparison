[![Build Status](https://secure.travis-ci.org/avh4/imagecomparison.png?branch=master)](http://travis-ci.org/avh4/imagecomparison)

## Usage

Add the following dependencies to your `pom.xml`:

```xml
  <dependency>
    <groupId>net.avh4.util</groupId>
    <artifactId>imagecomparison</artifactId>
    <version>0.3.1</version>
  </dependency>
  <!-- if you will be comparing AWT components, Swing JComponents or JFrames: -->
  <dependency>
    <groupId>net.avh4.util</groupId>
    <artifactId>imagecomparison-swing</artifactId>
    <version>0.3.1</version>
    <scope>runtime</scope>
  </dependency>
  <!-- if you will be comparing SWT components -->
  <dependency>
    <groupId>net.avh4.util</groupId>
    <artifactId>imagecomparison-swt</artifactId>
    <version>0.3.1</version>
    <scope>runtime</scope>
  </dependency>
```

### `isApproved()` hamcrest matcher

```java
import static net.avh4.util.imagecomparison.hamcrest.ImageComparisonMatchers.*;

public class MyJComponentTest {
  @Test public void testInitialState() throws Exception {
    MyJComponent subject = new MyJComponent();
    assertThat(subject, isApproved());
  }
}
```

This will assert that the rendering of `subject` matches `MyJComponentTest.testInitialState.png` (taken 
from the test class and method name), which will be loaded from the classpath in the same package as the test class.

You'll probably see something like this:

```
Failed tests: 
  testInitialState(MyJComponentTest): 
Expected: something that looks like MyJComponentTest.testInitialState.png (800x600)
     but: images don't match: First incorrect pixel was (0, 0): expected 0xffffff, but got 0x0
```

The actual rendering of the object will be written to `./MyJComponentTest.testInitialState.png` for you to inspect.

### `looksLike()` hamcrest matcher

You can also compare objects to specific image files.

```java
assertThat(subject, looksLike("snazzy-dashboard.png"));
```


## Comparison API

```java
ImageComparisonResult result = ImageComparison.compare(new MyJComponent(), "./snazzy-dashboard.png");
boolean renderingMatches = result.isEqual();
if (!renderingMatches) {
  System.out.println(result.getFailureMessage());
  result.writeActualImageToFile("failed-rendering.png");
}
```

## What you can test

The `looks-like` checker currently can check the following types of objects:

* `java.awt.image.BufferedImage`
* with imagecomparison-swing: `java.awt.Component`
* with imagecomparison-swing: `javax.swing.JComponent`
* with imagecomparison-swing: `javax.swing.JFrame`
* with imagecomparison-swt: `org.eclipse.swt.graphics.Image`
* with imagecomparison-swt: `org.eclipse.swt.graphics.ImageData`
* with imagecomparison-swt: `org.eclipse.swt.widgets.Control`

Additional renderers can be added by implementing `net.avh4.util.imagerender.Renderer` and registering in `META-INF/services`
(see [ServiceLoader](http://docs.oracle.com/javase/6/docs/api/java/util/ServiceLoader.html)).

