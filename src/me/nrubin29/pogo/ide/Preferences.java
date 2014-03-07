package me.nrubin29.pogo.ide;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

class Preferences extends JPanel {

    public Preferences(final IDE ide) {
        JLabel text = new JLabel("Click here to change.");
        text.setCursor(new Cursor(Cursor.HAND_CURSOR));
        text.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                JFontChooser chooser = new JFontChooser();

                if (chooser.showDialog(Preferences.this) == JOptionPane.OK_OPTION) {
                    Font font = chooser.getSelectedFont();
                    ide.setIDEFont(font);
                }
            }
        });

        addLabeledPanel("Text", text);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(320, 140));
    }

    private void addLabeledPanel(String str, JComponent comp) {
        JPanel labeledPanel = new JPanel();
        labeledPanel.add(new JLabel(str));
        labeledPanel.add(Box.createHorizontalStrut(130));
        labeledPanel.add(comp);
        add(labeledPanel);
    }
}