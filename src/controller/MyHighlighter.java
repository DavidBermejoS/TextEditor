package controller;

import javax.swing.text.DefaultHighlighter;
import java.awt.*;

/**
 * Clase Highlighter
 * @author David Bermejo Simon
 */
public class MyHighlighter extends DefaultHighlighter.DefaultHighlightPainter {
    /**
     * Constructs a new highlight painter. If <code>c</code> is null,
     * the JTextComponent will be queried for its selection color.
     *
     * @param c the color for the highlight
     */
    public MyHighlighter(Color c) {
        super(c);
    }
}
