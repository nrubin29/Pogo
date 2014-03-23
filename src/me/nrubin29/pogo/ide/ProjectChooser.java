package me.nrubin29.pogo.ide;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class ProjectChooser extends Box {

    public ProjectChooser(final IDE ide) {
        super(BoxLayout.Y_AXIS);

        JLabel title = new JLabel("Pogo");
        JButton newProj = new JButton("New Project"), open = new JButton("Open Project");

        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 64));

        newProj.setAlignmentX(Component.CENTER_ALIGNMENT);
        newProj.setBorderPainted(false);
        newProj.setCursor(new Cursor(Cursor.HAND_CURSOR));
        newProj.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ide.doNewProject();
            }
        });

        open.setAlignmentX(Component.CENTER_ALIGNMENT);
        open.setBorderPainted(false);
        open.setCursor(new Cursor(Cursor.HAND_CURSOR));
        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ide.doOpen();
            }
        });

        add(Box.createVerticalGlue());
        add(title);
        add(Box.createVerticalStrut(20));
        add(newProj);
        add(open);
        add(Box.createVerticalGlue());

        setOpaque(true);
        setBackground(Color.WHITE);
        setAlignmentX(JComponent.CENTER_ALIGNMENT);
        setAlignmentY(JComponent.CENTER_ALIGNMENT);
        setSize(640, 200);
        setVisible(true);
    }
}