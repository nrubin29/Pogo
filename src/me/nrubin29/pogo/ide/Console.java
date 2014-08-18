package me.nrubin29.pogo.ide;

import javafx.application.Platform;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import me.nrubin29.pogo.lang2.IDEInstance;

public class Console extends TextArea {

    private boolean waiting = false;
    private String result = null;

    public Console() {
        setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.UP) {
                e.consume();
            } else if (e.getCode() == KeyCode.ENTER) {
                if (waiting) result = getText().split("\n")[getText().split("\n").length - 1];
            }
        });

        setEditable(false);
        setFont(Font.font("Sans serif", FontWeight.NORMAL, FontPosture.REGULAR, 16));
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
        Platform.runLater(() -> appendText(txt));
    }

    public enum MessageType {
        OUTPUT(Color.BLACK),
        ERROR(Color.RED);

        private final Color color;

        MessageType(Color color) {
            this.color = color;
        }

        public Color getColor() {
            return color;
        }
    }
}