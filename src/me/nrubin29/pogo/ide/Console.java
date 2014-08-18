package me.nrubin29.pogo.ide;

import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import me.nrubin29.pogo.lang2.IDEInstance;
import org.fxmisc.richtext.StyleClassedTextArea;

public class Console extends StyleClassedTextArea {

    private boolean waiting = false;
    private String result = null;

    public Console() {
        setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.UP) {
                e.consume();
            } else if (e.getCode() == KeyCode.ENTER) {
                if (waiting) {
//                    result = getText().split("\n")[getText().split("\n").length - 1];
                    result = getDocument().getParagraphs().get(getDocument().getParagraphs().size() - 1).fullText();
                }
            }
        });

        setEditable(false);
        getStylesheets().add(getClass().getResource("/res/console.css").toExternalForm());
    }

    public void run(final Project project) {
        new Thread(() -> {
            try {
                IDEInstance.createInstance(project);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public String prompt() {
        setVisible(true);

        waiting = true;
        setEditable(true);

        while (result == null) {
            try {
                Thread.sleep(100);
            } catch (Exception ignored) {
            }
        }

        waiting = false;
        setEditable(false);

        String localResult = result;
        result = null;
        return localResult;
    }

    public void write(final String txt, final MessageType messageType) {
        Platform.runLater(() -> {
            appendText(txt + "\n");
            setStyleClass(getLength() - txt.length() - 1, getLength(), messageType.name().toLowerCase());
        });
    }

    public enum MessageType {
        OUTPUT, ERROR
    }
}