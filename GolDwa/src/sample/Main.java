package sample;

import golGUI.GridView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        GridView gridView = new GridView();
        primaryStage.setScene(new Scene(gridView,750,600));
        primaryStage.show();

        gridView.draw();
        //gridView.setAlive(2,2,20);

    }


    public static void main(String[] args) {
        launch(args);
    }
}
