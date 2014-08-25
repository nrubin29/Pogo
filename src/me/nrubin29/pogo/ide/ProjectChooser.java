package me.nrubin29.pogo.ide;

import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import static me.nrubin29.pogo.ide.JFXUtils.region;
import static me.nrubin29.pogo.ide.JFXUtils.spacer;

class ProjectChooser extends HBox {

    public ProjectChooser(final IDE ide) {
        Text title = new Text("Pogo");
        Button newProj = new Button("New Project"), open = new Button("Open Project");

        title.setFont(Font.font("Sans serif", FontWeight.NORMAL, FontPosture.REGULAR, 64));

        newProj.setCursor(Cursor.HAND);
        newProj.setOnAction(e -> ide.doNewProject());

        open.setCursor(Cursor.HAND);
        open.setOnAction(e -> ide.doOpen());

        VBox panel = new VBox();
        panel.setMaxSize(400, 400);
        panel.setAlignment(Pos.CENTER);
        panel.getChildren().addAll(region(0, 50), title, region(0, 5), newProj, region(0, 5), open);

        getChildren().addAll(spacer(true), panel, spacer(true));
        setPrefSize(640, 200);
        setVisible(true);
    }
}