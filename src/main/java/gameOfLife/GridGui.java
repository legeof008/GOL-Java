package gameOfLife;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.util.concurrent.atomic.AtomicBoolean;

public class GridGui extends GridPane {
    public static final int defDelay = 1000;
    public static final int defCycles = 1;

    public final TextField delayField;
    public final TextField cycleField;

    private final Button nextButton;
    private final Button stopButton;
    private final Button startButton;

    private int delay;
    private int cycles;

    public Canvas canvas;
    private SnapshotWriter sp;

    public boolean canDraw;
    public boolean started;

    private Board board;


    public final AtomicBoolean isOk;


    /**
     * Tworzy podstawowy interfejs, inicjalizuje odpowiednie metody pod odpowiednie guziki
     */
    public GridGui() {
        //Kontrolne podklasy
        isOk = new AtomicBoolean(true);


        this.canDraw = false;
        this.started = false;
        this.delay = defDelay;


        this.setAlignment(Pos.CENTER);
        this.setVgap(8);
        this.setHgap(5);

        this.cycleField = new TextField();
        this.delayField = new TextField();

        this.nextButton = new Button("Next");
        this.stopButton = new Button("Stop");
        this.startButton = new Button("Start");

        this.cycleField.setMaxWidth(50);
        this.delayField.setMaxWidth(50);

        this.add(cycleField, 0, 4);
        this.add(delayField, 2, 4);
        this.add(nextButton, 1, 4);
        this.add(startButton, 3, 4);
        this.add(stopButton, 4, 4);


        this.canvas = new Canvas(450, 450);
        this.add(canvas, 1, 0);


    }

    /**
     *
     * @param event
     * Funkcje ustawiaja event na kazdy z elementow
     */
    public void setOnActionButtonNext(EventHandler<javafx.event.Event> event) {
        nextButton.setOnMouseClicked(event);
    }

    public void setOnActionButtonStart(EventHandler<javafx.event.Event> event) {
        startButton.setOnMouseClicked(event);
    }

    public void setOnActionButtonStop(EventHandler<javafx.event.Event> event) {
        stopButton.setOnMouseClicked(event);
    }

    public void setOnActionTextCycle(EventHandler<ActionEvent> event) {
        cycleField.setOnAction(event);
    }

    public void setOnActionTextDelay(EventHandler<ActionEvent> event) {
        delayField.setOnAction(event);
    }

    public void setCycles(int cycles) {
        this.cycles = cycles;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public void setBoard(Board board) {
        if (board != null)
            this.board = board;

        canDraw = true;
    }

    public Board getBoard() {
        return this.board;
    }

    public int getDelay() {
        return this.delay;
    }

    public int getCycles() {
        return this.cycles;
    }


    /**
     * @param scale - skala dla planszy
     *              Metoda rysuje plansze z odpowiedniego objektu board.
     */


    public void draw(int scale) {
        if (canDraw) {
            GraphicsContext gC = this.canvas.getGraphicsContext2D();
            gC.setFill(Color.BLACK);
            gC.fillRect(0, 0, 450, 450);
            for (int i = 0; i < board.width; i++) {
                for (int j = 0; j < board.height; j++) {
                    setAlive(i, j, Color.rgb(board.getCell(i, j).getR(), board.getCell(i, j).getG(), board.getCell(i, j).getB()), scale);
                }
            }
        }
    }


    /**
     * @param x
     * @param y
     * @param color
     * @param scale Rysuje komorke na objekcie Canvas
     */


    private void setAlive(int x, int y, Color color, int scale) {
        GraphicsContext gC = this.canvas.getGraphicsContext2D();
        gC.setFill(color);
        gC.fillRect(x * scale, y * scale, scale, scale);
    }


}