package golGUI;

import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class GridView extends GridPane {
    private Button nextButton,startButton,stopButton,loadButton,saveButton;
    private TextField cycleField,delayField;
    private Canvas canvas;

    public GridView() {
        this.setAlignment(Pos.CENTER);
        this.setVgap(5);
        this.setHgap(5);

        this.nextButton = new Button("Next");
        this.add(nextButton,1,4);

        this.startButton = new Button("Start");
        this.add(startButton,3,4);

        this.stopButton = new Button("Stop");
        this.add(stopButton,4,4);

        this.loadButton = new Button("Load");
        this.add(loadButton,4,2);


        this.saveButton = new Button("Save");
        this.add(saveButton,4,3);

        this.cycleField = new TextField();
        this.cycleField.setMaxWidth(50);
        this.add(cycleField,0,4);


        this.delayField = new TextField();
        this.delayField.setMaxWidth(50);
        this.add(delayField,2,4);


        this.canvas = new Canvas(450,450);
        this.add(canvas,1,0);


    }
    public void draw(){
        GraphicsContext gC = this.canvas.getGraphicsContext2D();
        gC.setFill(Color.LIGHTGRAY);
        gC.fillRect(0,0,450,450);
    }
    public void setAlive(int x,int y, int s){
        GraphicsContext gC = this.canvas.getGraphicsContext2D();
        gC.setFill(Color.BLACK);
        gC.fillRect(x,y,1*s,1*s);
    }
}
