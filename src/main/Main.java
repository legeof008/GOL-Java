package main.java.gameOfLife;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Popup;
import javafx.stage.Stage;
import main.java.gameOfLife.*

import java.io.File;
import java.io.IOException;

public class Main extends Application {
    public Board board;
    PausableThread th;

    private Label errorMessagelabel;

    @Override
    public void start(Stage primaryStage) {

        GridGui gridView = new GridGui();

        final Button loadButton = new Button("Load");
        final Button saveButton = new Button("Save");

        ///Wczytywanie z przycisku
        loadButton.setOnMouseClicked(ae -> {
            try {
                errorMessagelabel.setText("");

                LoadSaveClass loadSave = new LoadSaveClass();
                File load = loadSave.useLoader(primaryStage);
                if(load != null) {
                    board = FirstBoard.readBoard(load);

                    gridView.setBoard(board);
                    gridView.draw(10);
                }

            } catch (CellParametersException | BoardParametersException ex)
            {
                errorMessagelabel.setText("Wybrany plik posiada niepoprawny format");
            }
            catch (IOException ex)
            {
                errorMessagelabel.setText("Nie można otworzyć wybranego pliku: " + ex.getMessage());
            }
            catch (Exception ex) {
                errorMessagelabel.setText("Nieznany błąd: " + ex.getMessage());
            }
        });

        //Zapisywanie z przycisku
        saveButton.setOnMouseClicked(ae -> {
            try {
                errorMessagelabel.setText("");

                LoadSaveClass loadSave = new LoadSaveClass();
                File save = loadSave.useSaver(primaryStage);

                SnapshotWriter sn = new SnapshotWriter(gridView.canvas, save);
                sn.saveImage(sn.currentStateSnapshot(1));
            } catch (IOException ex) {
                errorMessagelabel.setText("Nie zapisać planszy w postaci pliku: " + ex.getMessage());
            }
        });

        // Inicjowanie akcji dla odpowiednich przyciskow GridGui'a
        // Start Button
        gridView.setOnActionButtonStart(ae -> {
            if (gridView.canDraw) {
                if(!gridView.started) {
                    errorMessagelabel.setText("");

                    gridView.started = true;
                    if (!gridView.delayField.getText().isBlank()) {
                        try {
                            int delay = Integer.parseInt(gridView.delayField.getText());
                            if (delay < 100 || delay > 10000) {
                                errorMessagelabel.setText("Podana wartość nie jest właściwa");

                                delay = GridGui.defDelay;
                                gridView.delayField.setText(Integer.toString(delay));
                            }
                            gridView.setDelay(delay);
                            gridView.isOk.set(true);
                        } catch (NumberFormatException ex) {
                            errorMessagelabel.setText("Niepoprawny format liczbowy");

                            gridView.isOk.set(false);
                            gridView.delayField.setText(Integer.toString(GridGui.defDelay));
                            gridView.setDelay(GridGui.defDelay);
                        }
                    } else {
                        errorMessagelabel.setText("Nie podano żadnej wartości");

                        gridView.delayField.setText(Integer.toString(GridGui.defDelay));
                        gridView.setDelay(GridGui.defDelay);
                        gridView.isOk.set(true);
                    }
                    //TODO: nwm czy będzie dobrze działać
                    if (th != null)
                        th.interrupt();

                    th = new PausableThread(gridView, gridView.getDelay());
                    th.start();
                } else {
                    errorMessagelabel.setText("Należy wcześniej wcisnąć Stop");
                }
            } else {
                errorMessagelabel.setText("Należy wcześniej wczytać planszę");
            }
        });

        // Stop Button
        gridView.setOnActionButtonStop(ae -> {
            if (Thread.currentThread().isAlive()) {
                try {
                    if (th.isAlive()) {
                        th.pauseThread();
                        gridView.started = false;
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // Next Button
        gridView.setOnActionButtonNext(ae -> {
            if (gridView.canDraw) {
                errorMessagelabel.setText("");

                if (!gridView.cycleField.getText().isBlank()) {
                    try {
                        int cycleCount = Integer.parseInt(gridView.cycleField.getText());
                        if(cycleCount < 1 || cycleCount > 1000) {
                            errorMessagelabel.setText("Podana wartość nie jest właściwa");

                            cycleCount = GridGui.defCycles;
                            gridView.cycleField.setText(Integer.toString(cycleCount));
                        }
                        gridView.setCycles(cycleCount);
                    } catch (NumberFormatException ex) {
                        errorMessagelabel.setText("Niepoprawny format liczbowy");

                        gridView.cycleField.setText(Integer.toString(GridGui.defCycles));
                        gridView.setCycles(GridGui.defCycles);
                    }
                } else {
                    errorMessagelabel.setText("Nie podano żadnej wartości");

                    gridView.cycleField.setText(Integer.toString(GridGui.defCycles));
                    gridView.setCycles(GridGui.defCycles);
                }

                int cycles = gridView.getCycles();
                for (int i = 0; i < cycles; i++)
                    gridView.setBoard(Cykl.MakeACycle(gridView.getBoard()));
                gridView.draw(10);
            } else {
                errorMessagelabel.setText("Należy wcześniej wczytać planszę");
            }

        });

        // Cycle Text
        gridView.setOnActionTextCycle(ae -> {
            if (!gridView.cycleField.getText().isBlank()) {
                errorMessagelabel.setText("");

                try {
                    int cycleCount = Integer.parseInt(gridView.cycleField.getText());
                    if(cycleCount < 1 || cycleCount > 1000) {
                        errorMessagelabel.setText("Podana wartość nie jest właściwa");

                        cycleCount = GridGui.defCycles;
                        gridView.cycleField.setText(Integer.toString(cycleCount));
                    }
                    gridView.setCycles(cycleCount);
                } catch (NumberFormatException ex) {
                    errorMessagelabel.setText("Niepoprawny format liczbowy");

                    gridView.cycleField.setText(Integer.toString(GridGui.defCycles));
                    gridView.setCycles(GridGui.defCycles);
                }

            }

        });

        // Delay Text
        gridView.setOnActionTextDelay(ae -> {
            if (!gridView.delayField.getText().isBlank()) {
                errorMessagelabel.setText("");

                try {
                    int delay = Integer.parseInt(gridView.delayField.getText());
                    if (delay < 100 || delay > 10000) {
                        errorMessagelabel.setText("Podana wartość nie jest właściwa");

                        delay = GridGui.defDelay;
                        gridView.delayField.setText(Integer.toString(delay));
                    }
                    gridView.setDelay(delay);
                    gridView.isOk.set(true);
                } catch (NumberFormatException ex) {
                    errorMessagelabel.setText("Niepoprawny format liczbowy");

                    gridView.isOk.set(false);
                    gridView.delayField.setText(Integer.toString(GridGui.defDelay));
                    gridView.setDelay(GridGui.defDelay);
                }
            }
        });


        ///De facto inicjowanie sceny

        gridView.add(loadButton, 4, 2);
        gridView.add(saveButton, 4, 3);


        errorMessagelabel = new Label();
        errorMessagelabel.setMaxWidth(450);
        errorMessagelabel.setMaxHeight(35);
        errorMessagelabel.setWrapText(true);
        errorMessagelabel.setTextFill(Color.rgb(255, 0, 0));

        gridView.add(errorMessagelabel, 1, 2);

        primaryStage.setScene(new Scene(gridView, 750, 600));
        primaryStage.setResizable(false);


        primaryStage.show();

    }
    public static void main(String[] args) {
        launch(args);
    }


}
