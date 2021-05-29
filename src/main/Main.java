package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import main.java.BoardParametersException;
import main.java.gameOfLife.*;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;


public class Main extends Application {
    public Board board;
    public int SCALE = 2;
    Thread canvasThread;
    public GridGui gridView;
    public Queue<Board> boardQueue;
    public UpdateCanvas canvasTask;

    @Override
    public void start(Stage primaryStage) {

        gridView = new GridGui();

        final Button loadButton = new Button("Load");
        final Button saveButton = new Button("Save");

        ///Wczytywanie z przycisku
        loadButton.setOnMouseClicked(ae -> {
            if (canvasTask == null || !canvasTask.isRunning()) {

                try {
                    gridView.setWarningLabelText("");
                    LoadSaveClass loadSave = new LoadSaveClass();
                    File load = loadSave.useLoader(primaryStage);
                    board = FirstBoard.readBoard(load);

                    boardQueue = new LinkedList<>();
                    boardQueue.add(board);

                    gridView.setBoard(board);
                    SCALE = getScale(board,gridView);
                    gridView.draw(SCALE, gridView.getBoard());

                    updateQueManually(2, boardQueue, gridView);

                } catch (BoardParametersException | IOException | CellParametersException ex1) {
                    gridView.setWarningLabelText("Zly\nformat\npliku !");
                }
                catch (NullPointerException ex2){
                    gridView.setWarningLabelText("Brak\npliku !");
                }
            } else
                gridView.setWarningLabelText("Nalezy\nzatrzymac watek!\n");
        });

        //Zapisywanie z przycisku
        saveButton.setOnMouseClicked(ae -> {
            if (canvasTask == null || !canvasTask.isRunning()) {

                try {
                    gridView.setWarningLabelText("");
                    LoadSaveClass loadSave = new LoadSaveClass();
                    File save = loadSave.useSaver(primaryStage);

                    SnapshotWriter sn = new SnapshotWriter(gridView.canvas, save);
                    sn.saveImage(sn.currentStateSnapshot(1));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else
                gridView.setWarningLabelText("Nalezy\nzatrzymac watek!\n");
        });
        /**
         * Inicjowanie akcji dla odpowiednich przyciskow GridGui'a
         */

        gridView.setOnActionButtonStart(ae -> {
            if (gridView.canDraw && !gridView.delayField.getText().isBlank()) {
                try {
                    gridView.setDelay(Integer.parseInt(gridView.delayField.getText()) >= 100 ? Integer.parseInt(gridView.delayField.getText()) : GridGui.defDelay);

                } catch (NumberFormatException ex) {

                    gridView.setDelay(GridGui.defDelay);
                    gridView.setWarningLabelText("Niepoprawny\nformat liczb\nUstawiono\nwartosc:" + GridGui.defDelay);

                }

                if (canvasTask == null || canvasTask.isCancelled()) {


                    canvasTask = new UpdateCanvas(gridView.getDelay(), SCALE, gridView, boardQueue);

                    canvasThread = new Thread(canvasTask);

                    canvasThread.setDaemon(true);
                    canvasThread.start();

                } else if (canvasTask.isRunning()) {
                    gridView.setWarningLabelText("Trwa animacja\nnalezy wcisnac\nSTOP");
                }


            }
        });

        gridView.setOnActionButtonStop(ae -> {
            if (canvasTask != null && canvasTask.isRunning()) {
                canvasTask.cancel();
                gridView.setWarningLabelText("");
            }
        });

        gridView.setOnActionButtonNext(ae -> {
            if (gridView.canDraw) {
                if (!gridView.cycleField.getText().isBlank()) {
                    gridView.setWarningLabelText("");
                    try {
                        gridView.setCycles(Integer.parseInt(gridView.cycleField.getText()) > 0 ? Integer.parseInt(gridView.cycleField.getText()) : GridGui.defCycles);
                    } catch (NumberFormatException ex) {
                        gridView.setWarningLabelText("Niepoprawny\nformat liczb\nUstawiono wartosc: " + GridGui.defCycles);
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
                gridView.setWarningLabelText("");
                try {
                    gridView.setCycles(Integer.parseInt(gridView.cycleField.getText()) > 0 ? Integer.parseInt(gridView.cycleField.getText()) : GridGui.defCycles);
                } catch (NumberFormatException ex) {
                    gridView.setWarningLabelText("Niepoprawny\nformat liczb\nUstawiono wartosc: " + GridGui.defCycles);
                    gridView.setCycles(GridGui.defCycles);
                }

            }

        });

        gridView.setOnActionTextDelay(ae -> {
            if (!gridView.delayField.getText().isBlank()) {
                try {
                    gridView.setDelay(Integer.parseInt(gridView.delayField.getText()) >= 100 ? Integer.parseInt(gridView.delayField.getText()) : GridGui.defDelay);

                } catch (NumberFormatException ex) {
                    gridView.setWarningLabelText("Niepoprawny\nformat liczb\nUstawiono wartosc: " + GridGui.defDelay);
                    gridView.setDelay(GridGui.defDelay);
                }
            } else {
                gridView.setWarningLabelText("");
            }
            gridView.setWarningLabelText("");

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
    public static int getScale(Board board, GridGui gui){
        return Math.min(gui.getCanvasHeight()/board.getHeight(), gui.getCanvasWidth()/board.getWidth());
    }

    public static void main(String[] args) {
        launch(args);
    }

}
