package me.nrubin29.pogo.ide;

import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyCharacterCombination;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import me.nrubin29.pogo.lang2.Utils;
import org.controlsfx.dialog.Dialogs;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.StyleSpans;
import org.fxmisc.richtext.StyleSpansBuilder;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.URI;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static javafx.scene.input.KeyCombination.META_DOWN;
import static javafx.scene.input.KeyCombination.SHIFT_DOWN;

public class IDE {

    private Stage stage;
    private Scene scene;

    private HashMap<String, ScrollPane> files;
    private ListView<String> list;
    private Console console;

    private CodeArea currentCode;
    private Project currentProject;
    private File currentFile;

    public void start(Stage stage) {
        this.stage = stage;

        stage.setScene(scene = new Scene(new ProjectChooser(this), 640, 480));
        stage.setTitle("Pogo IDE");
        stage.show();
    }

    private void setup() {
        VBox vBox = new VBox();

        GridPane box = new GridPane();
        box.getColumnConstraints().addAll(JFXUtils.columnConstraints(33), JFXUtils.columnConstraints(1), JFXUtils.columnConstraints(66));

        files = new HashMap<>();

        list = new ListView<>();

        list.setOnMouseClicked(e -> {
            String selected = list.getSelectionModel().getSelectedItem();

            if (currentCode != null) {
                box.getChildren().remove(currentCode);
            }

            currentCode = (CodeArea) files.get(selected).getContent();
            currentFile = currentProject.getFile(selected);

            box.add(currentCode, 2, 0);

            updateTitle();
        });

        box.add(list, 0, 0);
        box.add(JFXUtils.region(0, 0), 1, 0);

        console = new Console();

        MenuBar menuBar = new MenuBar();

        final Menu
                file = new Menu("File"),
                project = new Menu("Project"),
                help = new Menu("Help");

        final MenuItem
                saveFile = new MenuItem("Save File"),
                removeFile = new MenuItem("Remove File"),
                runProject = new MenuItem("Run Project"),
                addFile = new MenuItem("Add File"),
                gitHub = new MenuItem("GitHub Wiki");

        menuBar.getMenus().add(file);
        menuBar.getMenus().add(project);
        menuBar.getMenus().add(help);

        file.getItems().add(saveFile);
        file.getItems().add(removeFile);

        project.getItems().add(runProject);
//        project.getItems().add() // Separator
        project.getItems().add(addFile);

        for (File f : currentProject.getFiles()) {
            files.put(f.getName().substring(0, f.getName().lastIndexOf(".")), createCodeTab(f));
            list.getItems().add(f.getName().substring(0, f.getName().lastIndexOf(".")));
        }

        updateTitle();

        help.getItems().add(gitHub);

        saveFile.setAccelerator(new KeyCharacterCombination("S", META_DOWN));
        saveFile.setOnAction(e -> {
            try {
                if (currentFile == null) {
                    return;
                }

                BufferedWriter writer = new BufferedWriter(new FileWriter(currentFile));

                String[] lines = currentCode.getText().split("\n");

                for (int i = 0; i < lines.length; i++) {
                    writer.write(lines[i]);
                    if (i + 1 != lines.length) writer.newLine();
                }

                writer.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        removeFile.setAccelerator(new KeyCharacterCombination("R", META_DOWN, SHIFT_DOWN));
        removeFile.setOnAction(e -> {
            if (currentProject == null) return;

            String fileName = currentFile.getName().substring(0, currentFile.getName().lastIndexOf("."));

            currentProject.deleteFile(fileName);

            list.getItems().remove(list.getSelectionModel().getSelectedIndex());
        });

        addFile.setAccelerator(new KeyCharacterCombination("A", META_DOWN, SHIFT_DOWN));
        addFile.setOnAction(e -> {
            if (currentProject == null) return;

            Optional<String> response = Dialogs.create()
                    .owner(stage)
                    .title("Add File")
                    .masthead("Add File")
                    .message("What would you like to name the file?")
                    .showTextInput();

            if (response.isPresent()) {
                createCodeTab(currentProject.addFile(response.get()));
            }
        });

        runProject.setAccelerator(new KeyCharacterCombination("R", META_DOWN));
        runProject.setOnAction(e -> {
            saveFile.getOnAction().handle(e);
            console.run(currentProject);
        });

        gitHub.setAccelerator(new KeyCharacterCombination("H", META_DOWN, SHIFT_DOWN));
        gitHub.setOnAction(e -> {
            try {
                Desktop.getDesktop().browse(new URI("http://www.github.com/nrubin29/Pogo/wiki"));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        vBox.getChildren().addAll(menuBar, box, console);
        scene.setRoot(vBox);
    }

    private ScrollPane createCodeTab(File f) {
        CodeArea text = new CodeArea();
        text.setParagraphGraphicFactory(LineNumberFactory.get(text));
        text.textProperty().addListener((obs, oldText, newText) -> text.setStyleSpans(0, computeHighlighting(newText)));
        text.replaceText(0, 0, Utils.readFile(f, true, "\n"));
        text.getStylesheets().add(getClass().getResource("/res/keywords.css").toExternalForm());

        ScrollPane scroll = new ScrollPane(text);
        scroll.setBorder(null);

        return scroll;
    }

    private static final String[] KEYWORDS = new String[]{
            "boolean", "class", "double", "else",
            "end", "for", "foreach", "if",
            "instance", "integer", "method", "new",
            "private", "public", "return", "string",
            "this", "var", "void", "while"
    };

    private static final Pattern KEYWORD_PATTERN = Pattern.compile("\\b(" + String.join("|", KEYWORDS) + ")\\b");

    private static StyleSpans<Collection<String>> computeHighlighting(String text) {
        Matcher matcher = KEYWORD_PATTERN.matcher(text);
        int lastKwEnd = 0;
        StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
        while (matcher.find()) {
            spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
            spansBuilder.add(Collections.singleton("keyword"), matcher.end() - matcher.start());
            lastKwEnd = matcher.end();
        }
        spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);
        return spansBuilder.create();
    }

    void doOpen() {
        DirectoryChooser chooser = new DirectoryChooser();

        File toUse = chooser.showDialog(stage);

        if (toUse != null) {
            currentProject = new Project(toUse);
            setup();
        }
    }

    void doNewProject() {
        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Choose Save Directory and Name");

        File toUse = chooser.showDialog(stage);

        if (toUse != null) {
            try {
                toUse.mkdir();
                currentProject = new Project(toUse);
                setup();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    private void updateTitle() {
        stage.setTitle(
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