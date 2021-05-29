package main.java.gameOfLife;

import javafx.application.Platform;
import javafx.concurrent.Task;
import main.Main;

import java.util.Queue;

public class UpdateCanvas extends Task<Void> {
    private final int delay;
    private final int scale;
    private final GridGui gui;
    private final Queue<Board> boardQueue;

    public UpdateCanvas(int delay, int scale, GridGui gui, Queue<Board> boardQueue) {
        this.delay = delay;
        this.scale = scale;
        this.gui = gui;
        this.boardQueue = boardQueue;
    }

    @Override
    protected Void call() throws Exception {
        while (true) {
            if (boardQueue.isEmpty()) {
                Main.updateQueManually(100, this.boardQueue, this.gui);
                Thread.sleep(500);
                System.out.println("Update kolejki");
            }
            Platform.runLater(() -> {
                gui.draw(scale, boardQueue.remove());
            });

            Thread.sleep(delay);

            if (isCancelled())
                return null;
        }

    }

    @Override
    public boolean cancel(boolean b) {
        return super.cancel(b);
    }

    @Override
    public void updateProgress(double workDone, double max) {
        super.updateProgress(workDone, max);
    }

}