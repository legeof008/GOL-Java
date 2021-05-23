package main.java.gameOfLife;

import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import main.SnapshotWriter;

import java.util.concurrent.atomic.AtomicBoolean;

public class GridView extends GridPane {
    private static final int defDelay = 1000;
    private static final int defCycles = 1;

    private final TextField delayField;
    private int delay;
    private int cycles;
    public Canvas canvas;
    private SnapshotWriter sp;
    private boolean canDraw;
    private Board board;
    private MyThread th;
    private final AtomicBoolean isOk;
    public final Popup popup;

    /**
     * Tworzy podstawowy interfejs, inicjalizuje odpowiednie metody pod odpowiednie guziki
     */
    public GridView() {
        //Kontrolne podklasy
        isOk = new AtomicBoolean(true);
        popup = new Popup();
        popup.getContent().add(new Label("Niepoprawny format liczbowy !"));

        //Jak sie zaladuje to moze rysowac
        this.canDraw = false;
        this.delay = defDelay;

        this.setAlignment(Pos.CENTER);
        this.setVgap(8);
        this.setHgap(5);

        //ilosc cykli do przetworzenia bedzie zczytywana z tego pola
        TextField cycleField = new TextField();
        cycleField.setOnAction(ae -> {
            if(!cycleField.getText().isBlank()){
                popup.hide();
                try {
                    cycles = Integer.parseInt(cycleField.getText()) > 0 ? Integer.parseInt(cycleField.getText()) : defCycles;
                }
                catch (NumberFormatException ex){
                    popup.show(this.getScene().getWindow());
                    cycles = defCycles;
                }

            }

        });
        cycleField.setMaxWidth(50);
        this.add(cycleField, 0, 4);

        //Delay bedzie zczytywany z tego pola
        this.delayField = new TextField();
        delayField.setOnAction(ae -> {
            if (!delayField.getText().isBlank()) {
                try {
                    delay = Integer.parseInt(delayField.getText()) >= 0 ? Integer.parseInt(delayField.getText()) : defDelay;
                    isOk.set(true);

                } catch (NumberFormatException ex) {
                    popup.show(this.getScene().getWindow());
                    delay = defDelay;
                    isOk.set(false);
                }
                if (isOk.get())
                    popup.hide();
                System.out.println("Nowy delay :" + delay);
            } else {
                popup.hide();
            }
        });
        this.delayField.setMaxWidth(50);
        this.add(delayField, 2, 4);

        //wywolanie cyklu z next
        Button nextButton = new Button("Next");
        nextButton.setOnMouseClicked(ae -> {
            if (canDraw) {
                if(!cycleField.getText().isBlank()){
                    popup.hide();
                    try {
                        cycles = Integer.parseInt(cycleField.getText()) > 0 ? Integer.parseInt(cycleField.getText()) : defCycles;
                    }
                    catch (NumberFormatException ex){
                        popup.show(this.getScene().getWindow());
                        cycles = defCycles;
                    }

                }
                System.out.println("Cycles = " + cycles);
                for(int i = 0; i < cycles; i++)
                    setBoard(Cykl.MakeACycle(board));
                draw(10);
            }

        });
        this.add(nextButton, 1, 4);

        //zaczyna ciagly cykl o opuznieniu delay
        Button startButton = new Button("Start");
        startButton.setOnMouseClicked(ae -> {
            if (canDraw) {
                popup.hide();
                th = new MyThread(delay);
                if (!delayField.getText().isBlank()) {
                    try {
                        delay = Integer.parseInt(delayField.getText()) != 0 ? Integer.parseInt(delayField.getText()) : defDelay;
                        isOk.set(true);
                        th.start();

                    } catch (NumberFormatException ex) {
                        popup.show(this.getScene().getWindow());
                        isOk.set(false);
                        delay = defDelay;
                    }

                }



            }
        });
        this.add(startButton, 3, 4);

        //zatrzymuje ciag cykli
        Button stopButton = new Button("Stop");
        stopButton.setOnMouseClicked(ex -> {
            if (Thread.currentThread().isAlive()) {
                try {
                    if(th.isAlive())
                        th.pauseThread();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (isOk.get())
                popup.hide();


        });
        this.add(stopButton, 4, 4);


        this.canvas = new Canvas(450, 450);
        this.add(canvas, 1, 0);


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
                    Color cellColor = new Color(((float) board.getCell(i, j).getR()) / 255, ((float) board.getCell(i, j).getB()) / 255, ((float) board.getCell(i, j).getB()) / 255, 0);
                    setAlive(i, j, Color.rgb(board.getCell(i, j).getR(), board.getCell(i, j).getG(), board.getCell(i, j).getB()), scale);
                }
            }
            /*if (isOk.get())
                popup.hide();*/
        }
    }

    /**
     * @param board - najaktualniejszy board
     *              Ustawia board dla klasy, nie moze to byc w konstruktorze, gdyz GridView musi moc
     *              egzystowac nawet bez board.
     */
    public void setBoard(Board board) {
        if (board != null)
            this.board = board;

        canDraw = true;
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


    public class MyThread extends Thread {
        private volatile boolean running = true;
        int delay;

        /**
         * @param delay Thread ktory da sie zapalzowac
         */

        public MyThread(int delay) {
            this.delay = delay;
        }

        @Override
        public void run() {
            while (running) {
                try {
                    setBoard(Cykl.MakeACycle(board));
                    Thread.sleep(this.delay);
                    System.out.println("dupa ale runnin");
                    draw(10);
                } catch (InterruptedException ex) {
                    System.out.println("dupa");
                }
            }

        }

        public void resumeThread() {
            running = true;
        }

        public void pauseThread() throws InterruptedException {
            running = false;
        }
    }

}

