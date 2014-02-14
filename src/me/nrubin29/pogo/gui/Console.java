package me.nrubin29.pogo.gui;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Console extends JFrame {
	
	private static final long serialVersionUID = 1L;

    private JTextPane text;
    private Filter f = new Filter();

    private String lastInput;

    public Console(final me.nrubin29.pogo.lang.Class clazz) {
        super("Pogo - Console");

        text = new JTextPane();
        ((AbstractDocument) text.getDocument()).setDocumentFilter(f);
        text.setEditable(false);
        text.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        text.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP) { e.consume(); }

                else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    try {
                        lastInput = text.getText().split("\n")[text.getText().split("\n").length - 1];

                        if (waiting) result = lastInput;
                    }
                    catch (Exception ex) { ex.printStackTrace(); }
                }
            }
        });

        JScrollPane scroll = new JScrollPane(text);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        new SmartScroller(scroll);

        add(scroll);

        setSize(640, 480);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setVisible(true);

        new Thread(new Runnable() {
            public void run() {
                try { clazz.run(Console.this); }
                catch (Exception e) {
                    dispose();
                    Thread.getDefaultUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), e);
                }
            }
        }).start();
    }

    private boolean waiting = false;
    private String result = null;

    public String prompt() {
        waiting = true;
        text.setEditable(true);

        while (result == null) {
            try { Thread.sleep(100); }
            catch (Exception e) { }
        }

        waiting = false;
        text.setEditable(false);

        String localResult = result;
        result = null;
        return localResult;
    }

    public void write(final String txt) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try { text.getDocument().insertString(text.getDocument().getLength(), txt + "\n", null); }
                catch (Exception e) { }

                setCaret();
            }
        });
    }

    private class Filter extends DocumentFilter {
        public void insertString(final FilterBypass fb, final int offset, final String string, final AttributeSet attr)
                throws BadLocationException {
            if (getLineStartOffset(getLineOfOffset(offset)) == getLineStartOffset(getLineOfOffset(text.getDocument().getLength()))) {
                super.insertString(fb, text.getDocument().getLength(), string, null);
            }
            setCaret();
        }

        public void remove(final FilterBypass fb, final int offset, final int length) throws BadLocationException {
            if (getLineStartOffset(getLineOfOffset(offset)) == getLineStartOffset(getLineOfOffset(text.getDocument().getLength()))) {
                super.remove(fb, offset, length);
            }
            setCaret();
        }

        public void replace(final FilterBypass fb, final int offset, final int length, final String string, final AttributeSet attrs)
                throws BadLocationException {
            if (getLineStartOffset(getLineOfOffset(offset)) == getLineStartOffset(getLineOfOffset(text.getDocument().getLength()))) {
                super.replace(fb, offset, length, string, null);
            }
            setCaret();
        }
    }

    private int getLineOfOffset(int offset) throws BadLocationException {
        Document doc = text.getDocument();
        if (offset < 0) {
            throw new BadLocationException("Can't translate offset to line", -1);
        } else if (offset > doc.getLength()) {
            throw new BadLocationException("Can't translate offset to line", doc.getLength() + 1);
        } else {
            Element map = doc.getDefaultRootElement();
            return map.getElementIndex(offset);
        }
    }

    private int getLineStartOffset(int line) throws BadLocationException {
        Element map = text.getDocument().getDefaultRootElement();
        if (line < 0) {
            throw new BadLocationException("Negative line", -1);
        } else if (line > map.getElementCount()) {
            throw new BadLocationException("Given line too big", text.getDocument().getLength() + 1);
        } else {
            Element lineElem = map.getElement(line);
            return lineElem.getStartOffset();
        }
    }

    private void setCaret() {
        try { text.setCaretPosition(text.getDocument().getLength()); }
        catch (Exception e) { e.printStackTrace(); }
    }
}