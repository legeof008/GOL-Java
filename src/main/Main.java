package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Popup;
import javafx.stage.Stage;
import main.java.gameOfLife.*;

import java.io.File;

public class Main extends Application {
    public Board board;
    PausableThread th;

    @Override
    public void start(Stage primaryStage) {

        GridGui gridView = new GridGui();
        Popup popup = new Popup();
        Popup popuper = new Popup();
        popup.getContent().add(new Label("Niepoprawny format liczbowy !"));
        popuper.getContent().add(new Label("Nalezy kliknac stop !"));

        final Button loadButton = new Button("Load");
        final Button saveButton = new Button("Save");

        ///Wczytywanie z przycisku
        loadButton.setOnMouseClicked(ae -> {
            try {
                popup.hide();
                LoadSaveClass loadSave = new LoadSaveClass();
                File load = loadSave.useLoader(primaryStage);
                board = FirstBoard.readBoard(load);
                gridView.setBoard(board);
                gridView.draw(10);

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        //Zapisywanie z przycisku
        saveButton.setOnMouseClicked(ae -> {
            try {
                popup.hide();
                popuper.hide();
                LoadSaveClass loadSave = new LoadSaveClass();
                File save = loadSave.useSaver(primaryStage);

                SnapshotWriter sn = new SnapshotWriter(gridView.canvas, save);
                sn.saveImage(sn.currentStateSnapshot(1));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        gridView.setOnActionButtonStart(ae -> {
            if (gridView.canDraw && !gridView.started) {
                popup.hide();
                popuper.hide();

                gridView.started = true;
                if (!gridView.delayField.getText().isBlank()) {
                    try {
                        gridView.setDelay(Integer.parseInt(gridView.delayField.getText()) >= 100 ? Integer.parseInt(gridView.delayField.getText()) : gridView.defDelay);
                        gridView.isOk.set(true);

                    } catch (NumberFormatException ex) {
                        popup.show(primaryStage.getScene().getWindow());
                        gridView.isOk.set(false);
                        gridView.setDelay(gridView.defDelay);
                    }
                }
                th = new PausableThread(gridView,gridView.getDelay());
                th.start();
            }
            else if(gridView.started) {
                popuper.show(primaryStage.getScene().getWindow());
            }
        });

        gridView.setOnActionButtonStop( ae-> {
            if (Thread.currentThread().isAlive()) {
                try {
                    if(th.isAlive()) {
                        th.pauseThread();
                        gridView.started = false;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (gridView.isOk.get()){
                popup.hide();
                popuper.hide();
            }

        });
        gridView.setOnActionButtonNext(ae -> {
            if (gridView.canDraw) {
                if(!gridView.cycleField.getText().isBlank()){
                    popup.hide();
                    popuper.hide();
                    try {
                        gridView.setCycles(Integer.parseInt(gridView.cycleField.getText()) > 0 ? Integer.parseInt(gridView.cycleField.getText()) : gridView.defCycles);
                    }
                    catch (NumberFormatException ex){
                        popup.show(primaryStage.getScene().getWindow());
                        gridView.setCycles(GridGui.defCycles);
                    }

                }
                int cycles = gridView.getCycles();
                System.out.println("Cukle: " + cycles);
                for(int i = 0; i < cycles; i++)
                    gridView.setBoard(Cykl.MakeACycle(gridView.getBoard()));
                gridView.draw(10);
            }

        });
        gridView.setOnActionTextCycle( ae-> {
            if(!gridView.cycleField.getText().isBlank()){
                popup.hide();
                popuper.hide();
                try {
                    gridView.setCycles(Integer.parseInt(gridView.cycleField.getText()) > 0 ? Integer.parseInt(gridView.cycleField.getText()) : gridView.defCycles);
                }
                catch (NumberFormatException ex){
                    popup.show(primaryStage.getScene().getWindow());
                    gridView.setCycles(gridView.defCycles);
                }

            }

        });
        gridView.setOnActionTextDelay( ae -> {
            if (!gridView.delayField.getText().isBlank()) {
                try {
                    gridView.setDelay(Integer.parseInt(gridView.delayField.getText()) >= 100 ? Integer.parseInt(gridView.delayField.getText()) : gridView.defDelay);
                    gridView.isOk.set(true);

                } catch (NumberFormatException ex) {
                    popup.show(primaryStage.getScene().getWindow());
                    gridView.setDelay(gridView.defDelay);
                    gridView.isOk.set(false);
                }
                if (gridView.isOk.get())
                    popup.hide();
                    popuper.hide();
            } else {
                popup.hide();
                popuper.hide();
            }

        });



        ///De facto inicjowanie sceny

        gridView.add(loadButton, 4, 2);
        gridView.add(saveButton, 4, 3);

        primaryStage.setScene(new Scene(gridView, 750, 600));
        primaryStage.setResizable(false);


        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
