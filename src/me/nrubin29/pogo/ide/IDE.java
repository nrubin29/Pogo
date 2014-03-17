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

    private final JToolBar consolePane;
    private final Console console;

    private File currentFile;

    public IDE() {
        super("Pogo IDE - No File");

        text = new JTextPane();
        text.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));

        JScrollPane scroll = new JScrollPane(text);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));

        add(scroll);

        console = new Console();

        JScrollPane consoleScroll = new JScrollPane(console);
        consoleScroll.setBorder(null);
        consoleScroll.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));

        consolePane = new JToolBar("Pogo Console");
        consolePane.setFloatable(false);
        consolePane.setVisible(false);
        consolePane.add(consoleScroll);

        add(consolePane);

        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("File"), settings = new JMenu("Settings"), orientation = new JMenu("Orientation"), help = new JMenu("Help");
        JMenuItem
                save = new JMenuItem("Save"),
                load = new JMenuItem("Load"),
                run = new JMenuItem("Run"),
                closeConsole = new JMenuItem("Close Console"),
                horizontal = new JRadioButtonMenuItem("Horizontal"),
                vertical = new JRadioButtonMenuItem("Vertical"),
                gitHub = new JMenuItem("GitHub Wiki");

        menuBar.add(file);
        menuBar.add(settings);
        menuBar.add(help);

        file.add(save);
        file.add(load);
        file.add(run);

        settings.add(closeConsole);
        settings.add(orientation);

        ButtonGroup orientationGroup = new ButtonGroup();
        orientationGroup.add(horizontal);
        orientationGroup.add(vertical);

        vertical.setSelected(true);

        orientation.add(vertical);
        orientation.add(horizontal);

        help.add(gitHub);

        setJMenuBar(menuBar);

        int meta = System.getProperty("os.name").startsWith("Mac") ? KeyEvent.META_DOWN_MASK : KeyEvent.CTRL_DOWN_MASK;

        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, meta));
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File toUse = null;

                if (currentFile != null) toUse = currentFile;

                else {
                    JFileChooser chooser = new JFileChooser();
                    chooser.setFileFilter(new FileNameExtensionFilter("Pogo Code", "pogo"));
                    chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                    chooser.setMultiSelectionEnabled(false);

                    if (chooser.showSaveDialog(IDE.this) == JFileChooser.APPROVE_OPTION) {
                        toUse = chooser.getSelectedFile();
                    }
                }

                if (toUse == null) return;

                try {
                    String fileName = toUse.getAbsolutePath();
                    if (!fileName.endsWith(".pogo")) fileName += ".pogo";

                    BufferedWriter writer = new BufferedWriter(new FileWriter(new File(fileName)));

                    String[] lines = text.getText().split("\n");

                    for (int i = 0; i < lines.length; i++) {
                        writer.write(lines[i]);

                        if (i + 1 != lines.length) writer.newLine();
                    }

                    writer.close();

                    currentFile = toUse;

                    String goodName;

                    if (toUse.getName().contains("."))
                        goodName = toUse.getName().substring(0, toUse.getName().lastIndexOf("."));
                    else goodName = toUse.getName();

                    setTitle("Pogo IDE - " + goodName);
                } catch (Exception ex) {
                    Thread.getDefaultUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), ex);
                }
            }
        });

        load.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, meta));
        load.addActionListener(new ActionListener() {
            @Override
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

                        currentFile = chooser.getSelectedFile();
                        setTitle("Pogo IDE - " + currentFile.getName().substring(0, currentFile.getName().lastIndexOf(".")));
                    } catch (Exception ex) {
                        Thread.getDefaultUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), ex);
                    }
                }
            }
        });

        run.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, meta));
        run.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!consolePane.isVisible()) consolePane.setVisible(true);
                console.run(new me.nrubin29.pogo.lang.Class(text.getText().split("\n")));
            }
        });

        closeConsole.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, meta + KeyEvent.SHIFT_DOWN_MASK));
        closeConsole.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                consolePane.setVisible(false);
            }
        });

        vertical.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean visible = consolePane.isVisible();
                if (visible) consolePane.setVisible(false);
                setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
                if (visible) consolePane.setVisible(true);
            }
        });

        horizontal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean visible = consolePane.isVisible();
                if (visible) consolePane.setVisible(false);
                setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
                if (visible) consolePane.setVisible(true);
            }
        });

        gitHub.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, meta + KeyEvent.SHIFT_DOWN_MASK));
        gitHub.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI("http://www.github.com/nrubin29/Pogo/wiki"));
                } catch (Exception ex) {
                    Thread.getDefaultUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), new Exception("Could not open page."));
                }
            }
        });

        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setSize(640, 480);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public Console getConsole() {
        return console;
    }
}