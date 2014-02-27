package me.nrubin29.pogo.gui;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.*;

public class IDE extends JFrame {

    private final Console console;
    private final JTextPane text;

    public IDE() {
        super("Pogo - IDE");

        text = new JTextPane();
        text.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));
        
        JScrollPane scroll = new JScrollPane(text);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        
        console = new Console();
        
        JScrollPane consoleScroll = new JScrollPane(console);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scroll, consoleScroll);
        split.setOneTouchExpandable(true);
        split.setDividerLocation(320);
        
        add(split);
        
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("File");
        JMenuItem run = new JMenuItem("Run"), save = new JMenuItem("Save"), load = new JMenuItem("Load");
        
        menuBar.add(menu);
        
        menu.add(run);
        menu.add(save);
        menu.add(load);
        
        setJMenuBar(menuBar);
        
        run.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.META_DOWN_MASK));
        run.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                console.run(new me.nrubin29.pogo.lang.Class(text.getText().split("\n")));
            }
        });
        
        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.META_DOWN_MASK));
        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.setFileFilter(new FileNameExtensionFilter("Pogo Code", "pogo"));
                chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                chooser.setMultiSelectionEnabled(false);

                if (chooser.showSaveDialog(IDE.this) == JFileChooser.APPROVE_OPTION) {
                    try {
                    	String fileName = chooser.getSelectedFile().getAbsolutePath();
                    	if (!fileName.endsWith(".pogo")) fileName += ".pogo";
                    	
                        BufferedWriter writer = new BufferedWriter(new FileWriter(new File(fileName)));

                        String[] lines = text.getText().split("\n");
                        
                        for (int i = 0; i < lines.length; i++) {
                        	writer.write(lines[i]);
                        	
                        	if (i + 1 != lines.length) writer.newLine();
                        }

                        writer.close();
                    } catch (Exception ex) {
                        Thread.getDefaultUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), ex);
                    }
                }
            }
        });
        
        load.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, KeyEvent.META_DOWN_MASK));
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

                        while (reader.ready()) {
                            text.getDocument().insertString(text.getDocument().getLength(), reader.readLine() + "\n", null);
                        }

                        reader.close();
                    } catch (Exception ex) {
                        Thread.getDefaultUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), ex);
                    }
                }
            }
        });

        setSize(640, 480);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }
}