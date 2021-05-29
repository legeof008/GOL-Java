package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Popup;
import javafx.stage.Stage;
import main.java.gameOfLife.*;

import java.io.File;
import java.util.LinkedList;
import java.util.Queue;


public class Main extends Application {
    public Board board;
    public int SCALE = 10;
    Thread queThread;
    Thread canvasThread;
    public GridGui gridView;
    public Queue<Board> boardQueue;
    public UpdateCanvas canvasTask;

    @Override
    public void start(Stage primaryStage) {

        gridView = new GridGui();


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

                boardQueue = new LinkedList<>();
                boardQueue.add(board);
                gridView.setBoard(board);
                gridView.draw(SCALE, gridView.getBoard());
                updateQueManually(2, boardQueue, gridView);

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
        /**
         * Inicjowanie akcji dla odpowiednich przyciskow GridGui'a
         */

        gridView.setOnActionButtonStart(ae -> {
            if (gridView.canDraw && !gridView.delayField.getText().isBlank()) {
                try {
                    gridView.setDelay(Integer.parseInt(gridView.delayField.getText()) >= 100 ? Integer.parseInt(gridView.delayField.getText()) : GridGui.defDelay);
                    gridView.isOk.set(true);

                } catch (NumberFormatException ex) {
                    popup.show(primaryStage.getScene().getWindow());
                    gridView.setDelay(GridGui.defDelay);
                    gridView.isOk.set(false);
                }
                if (gridView.isOk.get())
                    popup.hide();

                if (canvasTask == null || canvasTask.isCancelled()) {

                    canvasTask = new UpdateCanvas(gridView.getDelay(), SCALE, gridView, boardQueue);

                    canvasThread = new Thread(canvasTask);

                    canvasThread.setDaemon(true);
                    canvasThread.start();

                } else if (canvasTask.isRunning()) {
                    popuper.show(primaryStage.getScene().getWindow());
                }


            }
        });

        gridView.setOnActionButtonStop(ae -> {
            if (canvasTask != null && canvasTask.isRunning())
                canvasTask.cancel();
        });

        gridView.setOnActionButtonNext(ae -> {
            if (gridView.canDraw) {
                if (!gridView.cycleField.getText().isBlank()) {
                    popup.hide();
                    popuper.hide();
                    try {
                        gridView.setCycles(Integer.parseInt(gridView.cycleField.getText()) > 0 ? Integer.parseInt(gridView.cycleField.getText()) : GridGui.defCycles);
                    } catch (NumberFormatException ex) {
                        popup.show(primaryStage.getScene().getWindow());
                        gridView.setCycles(GridGui.defCycles);
                    }

                }

                updateQueManually(gridView.getCycles(), boardQueue, gridView);
                if (!boardQueue.isEmpty()) {
                    for (int i = 0; i < gridView.getCycles(); i++)
                        gridView.draw(SCALE, boardQueue.remove());
                }
            }

        });

        gridView.setOnActionTextCycle(ae -> {
            if (!gridView.cycleField.getText().isBlank()) {
                popup.hide();
                popuper.hide();
                try {
                    gridView.setCycles(Integer.parseInt(gridView.cycleField.getText()) > 0 ? Integer.parseInt(gridView.cycleField.getText()) : GridGui.defCycles);
                } catch (NumberFormatException ex) {
                    popup.show(primaryStage.getScene().getWindow());
                    gridView.setCycles(GridGui.defCycles);
                }

            }

        });

        gridView.setOnActionTextDelay(ae -> {
            if (!gridView.delayField.getText().isBlank()) {
                try {
                    gridView.setDelay(Integer.parseInt(gridView.delayField.getText()) >= 100 ? Integer.parseInt(gridView.delayField.getText()) : GridGui.defDelay);
                    gridView.isOk.set(true);

                } catch (NumberFormatException ex) {
                    popup.show(primaryStage.getScene().getWindow());
                    gridView.setDelay(GridGui.defDelay);
                    gridView.isOk.set(false);
                }
                if (gridView.isOk.get())
                    popup.hide();
            } else {
                popup.hide();
            }
            popuper.hide();

        });


        ///De facto inicjowanie sceny

        gridView.add(loadButton, 4, 2);
        gridView.add(saveButton, 4, 3);

        primaryStage.setScene(new Scene(gridView, 750, 600));
        primaryStage.setResizable(false);


        primaryStage.show();

    }

    public static void updateQueManually(int cycles, Queue<Board> boardQueue, GridGui gridView) {
        for (int i = 0; i < cycles; i++) {
            boardQueue.add(gridView.getBoard());
            gridView.setBoard(Cykl.MakeACycle(gridView.getBoard()));
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}
