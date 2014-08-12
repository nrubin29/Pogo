package me.nrubin29.pogo.ide;

import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

class ProjectChooser extends VBox {

    public ProjectChooser(final IDE ide) {
        Text title = new Text("Pogo");
        Button newProj = new Button("New Project"), open = new Button("Open Project");

        title.setFont(Font.font("Sans serif", FontWeight.NORMAL, FontPosture.REGULAR, 64));

        newProj.setCursor(Cursor.HAND);
        newProj.setOnAction(e -> ide.doNewProject());

        open.setCursor(Cursor.HAND);
        open.setOnAction(e -> ide.doOpen());

        getChildren().add(title);
        getChildren().add(newProj);
        getChildren().add(open);

        setPrefSize(640, 200);
        setVisible(true);
    }
}