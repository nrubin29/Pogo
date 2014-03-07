package me.nrubin29.pogo.ide;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.*;
import java.net.URI;

public class IDE extends JFrame {

    private final JTextPane text;
    private final Console console;

    private final Preferences prefs;

    public IDE() {
        super("Pogo IDE");

        text = new JTextPane();
        text.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));

        JScrollPane scroll = new JScrollPane(text);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));

        console = new Console();

        prefs = new Preferences(this);

        JScrollPane consoleScroll = new JScrollPane(console);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scroll, consoleScroll);
        split.setOneTouchExpandable(true);
        split.setDividerLocation(320);

        add(split);

        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("File"), help = new JMenu("Help");
        JMenuItem run = new JMenuItem("Run"), save = new JMenuItem("Save"), load = new JMenuItem("Load"), preferences = new JMenuItem("Preferences"), gitHub = new JMenuItem("GitHub Wiki");

        menuBar.add(file);
        menuBar.add(help);

        file.add(save);
        file.add(load);
        file.addSeparator();
        file.add(run);
        file.addSeparator();
        file.add(preferences);

        help.add(gitHub);

        setJMenuBar(menuBar);

        int meta = System.getProperty("os.name").startsWith("Mac") ? KeyEvent.META_DOWN_MASK : KeyEvent.CTRL_DOWN_MASK;

        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, meta));
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

        load.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, meta));
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

        preferences.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_COMMA, meta));
        preferences.setEnabled(false);
        preferences.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(IDE.this, prefs, "Preferences", JOptionPane.PLAIN_MESSAGE);
            }
        });

        run.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, meta));
        run.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                console.run(new me.nrubin29.pogo.lang.Class(text.getText().split("\n")));
            }
        });

        gitHub.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, meta + KeyEvent.SHIFT_DOWN_MASK));
        gitHub.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI("http://www.github.com/nrubin29/Pogo/wiki"));
                } catch (Exception ex) {
                    Thread.currentThread().getUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), new Exception("Could not open page."));
                }
            }
        });

        setSize(640, 480);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void setIDEFont(Font font) {
        text.setFont(font);
    }
}