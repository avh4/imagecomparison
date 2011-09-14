package net.avh4.util.imagecomparison;

import java.awt.Color;
import java.io.File;
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
		final ImageDiff ui = launch(args[0], args[1]);
		ui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ui.setLocationRelativeTo(null);
		ui.setVisible(true);
	}

	public static ImageDiff launch(final String file1, final String file2) {
		return launch(".", file1, file2);
	}

	public static ImageDiff launch(final String cwd, final String file1,
			final String file2) {
		final String filename1 = new File(cwd, file1).getPath();
		final String filename2 = new File(cwd, file2).getPath();
		try {
			return new ImageDiff(filename1, filename2);
		} catch (final IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
