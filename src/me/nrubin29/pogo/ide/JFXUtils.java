package me.nrubin29.pogo.ide;

import javafx.application.Platform;
import javafx.scene.layout.*;

import java.util.concurrent.CountDownLatch;

public final class JFXUtils {

    public static Region region(double width, double height) {
        Region region = new Region();
        region.setMinSize(width, height);
        region.setPrefSize(width, height);
        region.setMaxSize(width, height);
        return region;
    }

    public static Region spacer(boolean h) {
        Region region = new Region();

        if (h) {
            HBox.setHgrow(region, Priority.ALWAYS);
        } else {
            VBox.setVgrow(region, Priority.ALWAYS);
        }

        return region;
    }

    public static RowConstraints rowConstraints(int percent) {
        RowConstraints constraints = new RowConstraints();
        constraints.setPercentHeight(percent);
        return constraints;
    }

    public static ColumnConstraints columnConstraints(int percent) {
        ColumnConstraints constraints = new ColumnConstraints();
        constraints.setPercentWidth(percent);
        return constraints;
    }

    /**
     * Runs the specified {@link Runnable} on the
     * JavaFX application thread and waits for completion.
     *
     * @param action the {@link Runnable} to run
     * @throws NullPointerException if {@code action} is {@code null}
     */
    public static void runAndWait(Runnable action) {
        if (action == null)
            throw new NullPointerException("action");

        // run synchronously on JavaFX thread
        if (Platform.isFxApplicationThread()) {
            action.run();
            return;
        }

        // queue on JavaFX thread and wait for completion
        final CountDownLatch doneLatch = new CountDownLatch(1);
        Platform.runLater(() -> {
            try {
                action.run();
            } finally {
                doneLatch.countDown();
            }
        });

        try {
            doneLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}