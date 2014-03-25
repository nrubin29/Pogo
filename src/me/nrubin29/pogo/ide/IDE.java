package me.nrubin29.pogo.ide;

import me.nrubin29.pogo.Utils;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.URI;

public class IDE extends JFrame {

    private final ProjectChooser pC;

    private JTabbedPane tabs;
    private Console console;
    private JToolBar consolePane;

    private JTextPane text;
    private Project currentProject;
    private File currentFile;

    public IDE() {
        super("Pogo IDE");

        add(pC = new ProjectChooser(this));

        setBackground(Color.WHITE);
        setSize(640, 480);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void setup() {
        remove(pC);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        tabs = new JTabbedPane();
        tabs.setPreferredSize(new Dimension(640, 280));

        tabs.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                text = (JTextPane) ((JScrollPane) tabs.getSelectedComponent()).getViewport().getView();
                currentFile = currentProject.getFile(tabs.getTitleAt(tabs.getSelectedIndex()));
            }
        });

        panel.add(tabs);

        console = new Console();

        JScrollPane consoleScroll = new JScrollPane(console);
        consoleScroll.setBorder(null);
        consoleScroll.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));

        consolePane = new JToolBar();
        consolePane.setFloatable(false);
        consolePane.setVisible(false);
        consolePane.add(consoleScroll);

        panel.add(consolePane);

        JMenuBar menuBar = new JMenuBar();

        final JMenu
                file = new JMenu("File"),
                project = new JMenu("Project"),
                settings = new JMenu("Settings"),
                orientation = new JMenu("Console Orientation"),
                help = new JMenu("Help");

        final JMenuItem
                saveFile = new JMenuItem("Save File"),
                removeFile = new JMenuItem("Remove File"),
                runProject = new JMenuItem("Run Project"),
                addFile = new JMenuItem("Add File"),
                closeConsole = new JMenuItem("Close Console"),
                horizontal = new JRadioButtonMenuItem("Horizontal"),
                vertical = new JRadioButtonMenuItem("Vertical"),
                gitHub = new JMenuItem("GitHub Wiki");

        menuBar.add(file);
        menuBar.add(project);
        menuBar.add(settings);
        menuBar.add(help);

        file.add(saveFile);
        file.add(removeFile);

        project.add(runProject);
        project.addSeparator();
        project.add(addFile);

        for (File f : currentProject.getFiles()) {
            tabs.addTab(f.getName().substring(0, f.getName().lastIndexOf(".")), createCodeTab(f));
        }

        updateTitle();

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

        saveFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, meta));
        saveFile.addActionListener(new ActionListener() {
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
                    Thread.getDefaultUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), new Utils.IDEException("Could not save file."));
                }
            }
        });

        removeFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, meta + KeyEvent.SHIFT_DOWN_MASK));
        removeFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentProject == null) return;

                String fileName = currentFile.getName().substring(0, currentFile.getName().lastIndexOf("."));

                currentProject.deleteFile(fileName);

                tabs.removeTabAt(tabs.getSelectedIndex());
            }
        });

        addFile.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, meta + KeyEvent.SHIFT_DOWN_MASK));
        addFile.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentProject == null) return;

                String in = JOptionPane.showInputDialog(IDE.this, "What would you like to name the file?");
                if (in == null) return;

                createCodeTab(currentProject.addFile(in));
            }
        });

        runProject.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, meta));
        runProject.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!consolePane.isVisible()) consolePane.setVisible(true);
                saveFile.doClick();
                console.run(currentProject);
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
                    Thread.getDefaultUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), new Utils.IDEException("Could not open page."));
                }
            }
        });

        add(panel);

        panel.validate();
        panel.repaint();

        validate();
        repaint();
    }

    private JScrollPane createCodeTab(File f) {
        JTextPane text = new JTextPane();
        text.setText(Utils.readFile(f, "\n"));
        text.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 16));

        JScrollPane scroll = new JScrollPane(text);
        scroll.setBackground(Color.WHITE);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));

        return scroll;
    }

    void doOpen() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setMultiSelectionEnabled(false);

        if (chooser.showOpenDialog(IDE.this) == JFileChooser.APPROVE_OPTION) {
            currentProject = new Project(chooser.getSelectedFile());
            setup();
        }
    }

    void doNewProject() {
        JFileChooser chooser = new JFileChooser("Choose Save Directory and Name");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);

        if (chooser.showSaveDialog(IDE.this) == JFileChooser.APPROVE_OPTION) {
            File toUse = chooser.getSelectedFile();

            try {
                toUse.mkdir();
                currentProject = new Project(toUse);
                setup();
            } catch (Exception ex) {
                Thread.getDefaultUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), new Utils.IDEException("Could not create new project."));
            }
        }
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