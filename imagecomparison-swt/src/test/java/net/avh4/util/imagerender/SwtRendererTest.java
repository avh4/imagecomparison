package net.avh4.util.imagerender;

import static net.avh4.util.imagecomparison.hamcrest.ImageComparisonMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.junit.Test;

/**
 * This test assumes that SwtRenderer is registered with the Java ServiceLoader
 * so that the isApproved() imagecomparison matcher will make use of it.
 */
public class SwtRendererTest {
	private static final Display display = Display.getDefault();

	@Test
	public void shouldAllowComparisonOfSwtImages() throws Exception {
		final Image image = new Image(display,
				getClass().getResourceAsStream("image1.png"));
		assertThat(image, isApproved());
	}

	@Test
	public void shouldAllowComparisonOfSwtImageData() throws Exception {
		final ImageData imageData = new ImageData(
				getClass().getResourceAsStream("image1.png"));
		assertThat(imageData, isApproved());
	}

	@Test
	public void shouldAllowComparisonOfSwtControls() throws Exception {
		Control control = new TestControl(new Shell());
		assertThat(control, isApproved());
	}

	/** Has a preferred size of 100x100, and draws a red square. */
	private static class TestControl extends Canvas {
		private final Color red = new Color(null, 255, 0, 0);

		public TestControl(Composite parent) {
			super(parent, 0);
			addPaintListener(new PaintListener() {
				@Override
				public void paintControl(PaintEvent e) {
					GC gc = e.gc;
					gc.setForeground(red);
					gc.drawRectangle(10, 10, 80, 80);
				}
			});
			addDisposeListener(new DisposeListener() {
				@Override
				public void widgetDisposed(DisposeEvent disposeEvent) {
					red.dispose();
				}
			});
		}

		@Override
		public Point computeSize(int i, int i2, boolean b) {
			return new Point(100, 100);
		}
	}
}
