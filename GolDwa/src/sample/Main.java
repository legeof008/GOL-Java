package sample;

import golGUI.GridView;
import golGUI.LoadSaveClass;
import golGUI.SnapshotWriter;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;


public class Main extends Application {


    @Override
    public void start(Stage primaryStage) {
        GridView gridView = new GridView();
        final Button loadButton = new Button("Load");
        final Button saveButton = new Button("Save");

        ///Inicjowanie pop upow
        loadButton.setOnMouseClicked(ae->{
            try {
                LoadSaveClass loadSave = new LoadSaveClass();
                loadSave.useLoader(primaryStage);
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        });
        saveButton.setOnMouseClicked(ae -> {
            try {
                LoadSaveClass loadSave = new LoadSaveClass();
                File save = loadSave.useSaver(primaryStage);

                SnapshotWriter sn = new SnapshotWriter(gridView.canvas,save);
                sn.saveImage(sn.currentStateSnapshot(1));
            }catch (Exception ex){
                ex.printStackTrace();
            }
        });


        ///De facto inicjowanie sceny

        gridView.add(loadButton,4,2);
        gridView.add(saveButton,4,3);

        primaryStage.setScene(new Scene(gridView,750,600));

        primaryStage.show();
        gridView.draw();
    }



    public static void main(String[] args) {
        launch(args);
    }
}
