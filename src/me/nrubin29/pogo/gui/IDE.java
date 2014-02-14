package me.nrubin29.pogo.gui;

import me.nrubin29.pogo.lang.Class;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class IDE extends JFrame {

	private static final long serialVersionUID = 1L;

    private final JTextPane text;

    public IDE() {
        super("Pogo - IDE");

        text = new JTextPane();
        text.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));

        JButton run = new JButton("Run");
        run.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new Console(new Class(text.getText().split("\n")));
            }
        });

        JButton save = new JButton("Save");
        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.setFileFilter(new FileNameExtensionFilter("Pogo Code", "pogo"));
                chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                chooser.setMultiSelectionEnabled(false);

                if (chooser.showSaveDialog(IDE.this) == JFileChooser.APPROVE_OPTION) {
                    try {
                        BufferedWriter writer = new BufferedWriter(new FileWriter(new File(chooser.getSelectedFile().getAbsolutePath() + ".pogo")));

                        for (String line : text.getText().split("\n")) writer.write(line + "\n");

                        writer.close();
                    } catch (Exception ex) {
                        Thread.getDefaultUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), ex);
                    }
                }
            }
        });

        JButton load = new JButton("Load");
        load.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.setFileFilter(new FileNameExtensionFilter("Pogo Code", "pogo"));
                chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                chooser.setMultiSelectionEnabled(false);

                if (chooser.showOpenDialog(IDE.this) == JFileChooser.APPROVE_OPTION) {
                    text.setText("");

                    try {
                        BufferedReader reader = new BufferedReader(new FileReader(chooser.getSelectedFile()));

                        while (reader.ready())
                            text.getDocument().insertString(text.getDocument().getLength(), reader.readLine() + "\n", null);

                        reader.close();
                    } catch (Exception ex) {
                        Thread.getDefaultUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), ex);
                    }
                }
            }
        });

        JScrollPane scroll = new JScrollPane(text);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));

        JPanel toolBar = new JPanel();
        toolBar.setMaximumSize(new Dimension(640, 30));
        toolBar.add(run);
        toolBar.add(save);
        toolBar.add(load);

        add(toolBar);
        add(scroll);

        setSize(640, 480);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }
}