package me.nrubin29.pogo.ide;

import me.nrubin29.pogo.IDEException;
import me.nrubin29.pogo.Utils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.URI;

public class IDE extends JFrame {

    private final JTextPane text;
    private final Console console;
    private final JToolBar consolePane;

    private Project currentProject;
    private File currentFile;

    public IDE() {
        updateTitle();

        text = new JTextPane();
        text.setEditable(false);
        text.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));

        JScrollPane scroll = new JScrollPane(text);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));

        add(scroll);

        console = new Console();

        JScrollPane consoleScroll = new JScrollPane(console);
        consoleScroll.setBorder(null);
        consoleScroll.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));

        consolePane = new JToolBar();
        consolePane.setFloatable(false);
        consolePane.setVisible(false);
        consolePane.add(consoleScroll);

        add(consolePane);
        
        /*
        File			Project				Settings			Help
         Save File		 New Project 		 Close Console		 GitHub Wiki
         Run			 Load Project		 Orientation
	     			 	 Choose File          Horizontal
	     				 Add File		  	  Vertical
         */

        JMenuBar menuBar = new JMenuBar();

        final JMenu
                file = new JMenu("File"),
                project = new JMenu("Project"),
                files = new JMenu("Choose File"),
                settings = new JMenu("Settings"),
                orientation = new JMenu("Orientation"),
                help = new JMenu("Help");

        JMenuItem
                save = new JMenuItem("Save File"),
                run = new JMenuItem("Run File"),
                newProj = new JMenuItem("New Project"),
                load = new JMenuItem("Load Project"),
                addFile = new JMenuItem("Add File"),
                closeConsole = new JMenuItem("Close Console"),
                horizontal = new JRadioButtonMenuItem("Horizontal"),
                vertical = new JRadioButtonMenuItem("Vertical"),
                gitHub = new JMenuItem("GitHub Wiki");

        menuBar.add(file);
        menuBar.add(project);
        menuBar.add(settings);
        menuBar.add(help);

        file.add(save);
        file.add(run);

        project.add(newProj);
        project.add(load);
        project.addSeparator();
        project.add(files);
        project.add(addFile);

        final ButtonGroup filesGroup = new ButtonGroup();

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

        newProj.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, meta));
        newProj.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser("Choose Save Directory and Name");
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                chooser.setAcceptAllFileFilterUsed(false);

                if (chooser.showSaveDialog(IDE.this) == JFileChooser.APPROVE_OPTION) {
                    File toUse = chooser.getSelectedFile();

                    try {
                        toUse.mkdir();
                        currentProject = new Project(toUse);
                        for (File f : currentProject.getFiles()) addFileMenuItem(f, filesGroup, files);
                        updateTitle();
                    } catch (Exception ex) {
                        Thread.getDefaultUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), new IDEException("Could not create new project."));
                    }
                }
            }
        });

        save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, meta));
        save.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter(currentFile));

                    String[] lines = text.getText().split("\n");

                    for (int i = 0; i < lines.length; i++) {
                        writer.write(lines[i]);
                        if (i + 1 != lines.length) writer.newLine();
                    }

                    writer.close();
                } catch (Exception ex) {
                    Thread.getDefaultUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), new IDEException("Could not save file."));
                }
            }
        });

        load.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, meta));
        load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser chooser = new JFileChooser();
                chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                chooser.setMultiSelectionEnabled(false);

                if (chooser.showOpenDialog(IDE.this) == JFileChooser.APPROVE_OPTION) {
                    currentProject = new Project(chooser.getSelectedFile());
                    for (File f : currentProject.getFiles()) addFileMenuItem(f, filesGroup, files);
                    updateTitle();
                }
            }
        });

        addFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, meta + KeyEvent.SHIFT_DOWN_MASK));
        addFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentProject == null) return;
                File f = currentProject.addFile(JOptionPane.showInputDialog(IDE.this, "What would you like to name the file?"));
                addFileMenuItem(f, filesGroup, files);
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
                    Thread.getDefaultUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), new IDEException("Could not open page."));
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

    private void addFileMenuItem(final File f, ButtonGroup filesGroup, JMenu superMenu) {
        JMenuItem fItem = new JMenuItem(f.getName().substring(0, f.getName().lastIndexOf(".")));
        filesGroup.add(fItem);
        superMenu.add(fItem);
        fItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentFile = f;
                updateTitle();
                text.setEditable(true);
                text.setText(Utils.readFile(f, "\n"));
            }
        });
    }

    private void updateTitle() {
        setTitle(
                "Pogo IDE - " +
                        (currentProject != null ? currentProject.getName() : "No Project") +
                        " - " +
                        (currentFile != null ? currentFile.getName().substring(0, currentFile.getName().lastIndexOf(".")) : "No File")
        );
    }

    public Console getConsole() {
        return console;
    }
}