package main.java;

import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class GridView extends GridPane {
    private final Button nextButton;
    private final Button startButton;
    private final Button stopButton;
    private final TextField cycleField;
    private final TextField delayField;
    public Canvas canvas;
    private SnapshotWriter sp;

    public GridView() {
        this.setAlignment(Pos.CENTER);
        this.setVgap(8);
        this.setHgap(5);

        this.nextButton = new Button("Next");
        this.nextButton.setOnMouseClicked(ae -> {
            //wywolanie cyklu z next

        });
        this.add(nextButton, 1, 4);

        this.startButton = new Button("Start");
        this.startButton.setOnMouseClicked(ae -> {
            //cokolwiek robi start
            setAlive(10, 10, Color.RED, 10);
        });
        this.add(startButton, 3, 4);

        this.stopButton = new Button("Stop");
        this.stopButton.setOnMouseClicked(ex -> {
            //Cokolwiek robi stop

        });
        this.add(stopButton, 4, 4);


        this.cycleField = new TextField();
        //ilosc cykli do przetworzenia bedzie zczytywana z tego pola
        this.cycleField.setMaxWidth(50);
        this.add(cycleField, 0, 4);


        this.delayField = new TextField();
        //Delay bedzie zczytywany z tego pola
        this.delayField.setMaxWidth(50);
        this.add(delayField, 2, 4);


        this.canvas = new Canvas(450, 450);
        this.add(canvas, 1, 0);


    }

    public void draw() {
        GraphicsContext gC = this.canvas.getGraphicsContext2D();
        gC.setFill(Color.LIGHTGRAY);
        gC.fillRect(0, 0, 450, 450);
    }

    private void setAlive(int x, int y, Color color, int scale) {
        GraphicsContext gC = this.canvas.getGraphicsContext2D();
        gC.setFill(color);
        gC.fillRect(x, y, scale, scale);
    }
}
