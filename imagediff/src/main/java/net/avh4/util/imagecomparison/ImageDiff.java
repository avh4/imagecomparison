package net.avh4.util.imagecomparison;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.io.filefilter.WildcardFileFilter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Iterator;

public class ImageDiff extends JPanel implements MouseListener {

    private static final long serialVersionUID = 1L;
    private final ImageDiffView view;

    public ImageDiff(File file1, File file2) throws IOException {
        setBackground(new Color(232, 232, 232));
        final FlowLayout layout = (FlowLayout) getLayout();
        layout.setHgap(0);
        layout.setVgap(0);
        addMouseListener(this);
        view = new ImageDiffView(file1, file2);
        add(view);
    }

    public static void main(final String[] args) {
        final JFrame window = new JFrame();
        final ImageDiff ui = launch(args);
        window.add(ui);
        window.pack();
        window.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }

    private static ImageDiff launch(String[] args) {
        if (args.length >= 2) {
            return launch(args[0], args[1]);
        } else {
            return launch();
        }
    }

    public static ImageDiff launch(final String file1, final String file2) {
        return launch(new File("."), file1, file2);
    }

    public static ImageDiff launch(final File root, final String file1,
                                   final String file2) {
        final String filename1 = new File(root, file1).getPath();
        final String filename2 = new File(root, file2).getPath();
        return launch(new File(filename1), new File(filename2));
    }

    private static ImageDiff launch(File file1, File file21) {
        try {
            return new ImageDiff(file1, file21);
        } catch (final IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ImageDiff launch() {
        return launch(new File("."));
    }

    public static ImageDiff launch(File root) {
        FilenameFilter pngFiles = new WildcardFileFilter("*.png");
        File actualFile = root.listFiles(pngFiles)[0];
        @SuppressWarnings("unchecked") Iterator<File> iterator = (Iterator<File>) FileUtils.iterateFiles(
                new File(root, "src/"),
                new WildcardFileFilter(actualFile.getName()),
                TrueFileFilter.TRUE);
        File expectedFile = iterator.next();
        return launch(actualFile, expectedFile);
    }

    @Override
    public void mouseClicked(final MouseEvent e) {
        view.setShowHighlight(false);
    }

    @Override
    public void mousePressed(final MouseEvent e) {
    }

    @Override
    public void mouseReleased(final MouseEvent e) {
    }

    @Override
    public void mouseEntered(final MouseEvent e) {
    }

    @Override
    public void mouseExited(final MouseEvent e) {
    }
}
