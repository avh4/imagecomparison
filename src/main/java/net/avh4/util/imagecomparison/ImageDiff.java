package net.avh4.util.imagecomparison;

import java.awt.Color;
import java.io.IOException;

import javax.swing.JFrame;

public class ImageDiff extends JFrame {

	private static final long serialVersionUID = 1L;

	public ImageDiff(final String file1, final String file2) throws IOException {
		setBackground(Color.WHITE);
		getContentPane().add(new ImageDiffView(file1, file2));
		pack();
	}

	public static void main(final String[] args) {
		final ImageDiff ui = launch("actual.png", "expected.png");
		ui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ui.setLocationRelativeTo(null);
		ui.setVisible(true);
	}

	public static ImageDiff launch(final String file1, final String file2) {
		try {
			return new ImageDiff(file1, file2);
		} catch (final IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
