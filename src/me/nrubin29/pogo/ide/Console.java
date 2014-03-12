package me.nrubin29.pogo.ide;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Console extends JTextPane {

    public Console() {
        ((AbstractDocument) getDocument()).setDocumentFilter(new Filter());

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    e.consume();
                } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    if (waiting) result = getText().split("\n")[getText().split("\n").length - 1];
                }
            }
        });

        setEditable(false);
        setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
    }

    public void run(final me.nrubin29.pogo.lang.Class clazz) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    clazz.run(Console.this);
                } catch (Exception e) {
                    Thread.getDefaultUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), e);
                }
            }
        }).start();
    }

    private boolean waiting = false;
    private String result = null;

    public String prompt() {
        setVisible(true);
        getParent().requestFocusInWindow();

        waiting = true;
        setEditable(true);

        while (result == null) {
            try {
                Thread.sleep(100);
            } catch (Exception ignored) {
            }
        }

        waiting = false;
        setEditable(false);

        String localResult = result;
        result = null;
        return localResult;
    }

    public void write(final String txt) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    getDocument().insertString(getDocument().getLength(), txt + "\n", null);
                } catch (Exception ignored) {
                }

                setCaret();
            }
        });
    }

    private class Filter extends DocumentFilter {
        @Override
        public void insertString(final FilterBypass fb, final int offset, final String string, final AttributeSet attr)
                throws BadLocationException {
            if (getLineStartOffset(getLineOfOffset(offset)) == getLineStartOffset(getLineOfOffset(getDocument().getLength()))) {
                super.insertString(fb, getDocument().getLength(), string, null);
            }
            setCaret();
        }

        @Override
        public void remove(final FilterBypass fb, final int offset, final int length) throws BadLocationException {
            if (getLineStartOffset(getLineOfOffset(offset)) == getLineStartOffset(getLineOfOffset(getDocument().getLength()))) {
                super.remove(fb, offset, length);
            }
            setCaret();
        }

        @Override
        public void replace(final FilterBypass fb, final int offset, final int length, final String string, final AttributeSet attrs)
                throws BadLocationException {
            if (getLineStartOffset(getLineOfOffset(offset)) == getLineStartOffset(getLineOfOffset(getDocument().getLength()))) {
                super.replace(fb, offset, length, string, null);
            }
            setCaret();
        }
    }

    private int getLineOfOffset(int offset) throws BadLocationException {
        Document doc = getDocument();
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
        Element map = getDocument().getDefaultRootElement();
        if (line < 0) {
            throw new BadLocationException("Negative line", -1);
        } else if (line > map.getElementCount()) {
            throw new BadLocationException("Given line too big", getDocument().getLength() + 1);
        } else {
            Element lineElem = map.getElement(line);
            return lineElem.getStartOffset();
        }
    }

    private void setCaret() {
        try {
            setCaretPosition(getDocument().getLength());
        } catch (Exception ignored) {
        }
    }
}