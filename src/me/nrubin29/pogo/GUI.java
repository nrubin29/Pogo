package me.nrubin29.pogo;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.WindowConstants;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.DocumentFilter;
import javax.swing.text.Element;

public class GUI extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private static GUI instance = new GUI();
	
	public static GUI getInstance() {
		return instance;
	}

    private JTextPane text;
    private Filter f = new Filter();

    private String lastInput;

    GUI() {
        super("Pogo - Jump into Coding");

        text = new JTextPane();
        ((AbstractDocument) text.getDocument()).setDocumentFilter(f);
        text.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        text.setForeground(Color.GREEN);
        text.setBackground(Color.BLACK);
        text.setCaretColor(Color.GREEN);
        text.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_UP) { e.consume(); }

//                else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
//                    try {
//                        lastInput = text.getText().split("\n")[text.getText().split("\n").length - 1];
//
//                        new Thread(new Runnable() {
//                            public void run() {
//                                if (hijack) result = lastInput;
//                                else CommandParser.getInstance().parse(lastInput);
//                            }
//                        }).start();
//                    }
//                    catch (Exception ex) { ex.printStackTrace(); }
//                }
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
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }
    
    private String result = null;

    public String prompt() {
        while (result == null) {
            //Utils.pause(Utils.SECOND / 10);
        }

        String localResult = result;
        result = null;
        return localResult;
    }

    public void write(String txt) {
        try { text.getDocument().insertString(text.getDocument().getLength(), txt + "\n", null); }
        catch (Exception e) { }
        
        setCaret();
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