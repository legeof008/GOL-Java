package Main;

import Main.java.GridView;
import Main.java.LoadSaveClass;
import Main.java.SnapshotWriter;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.File;


public class Main extends Application {


    @Override
    public void start(Stage primaryStage) {
        GridView gridView = new GridView();
        final Button loadButton = new Button("Load");
        final Button saveButton = new Button("Save");

        ///Wczytywanie z przycisku
        loadButton.setOnMouseClicked(ae -> {
            try {
                LoadSaveClass loadSave = new LoadSaveClass();
                loadSave.useLoader(primaryStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        //Zapisywanie z przycisku
        saveButton.setOnMouseClicked(ae -> {
            try {
                LoadSaveClass loadSave = new LoadSaveClass();
                File save = loadSave.useSaver(primaryStage);

                SnapshotWriter sn = new SnapshotWriter(gridView.canvas, save);
                sn.saveImage(sn.currentStateSnapshot(1));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });



        ///De facto inicjowanie sceny

        gridView.add(loadButton, 4, 2);
        gridView.add(saveButton, 4, 3);

        primaryStage.setScene(new Scene(gridView, 750, 600));
        primaryStage.setResizable(false);

        primaryStage.show();
        gridView.draw();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
