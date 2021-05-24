package main.java.gameOfLife;

public class PausableThread extends Thread {
    private final GridGui GUI;
    private volatile boolean running = true;
    int delay;

    public PausableThread(GridGui GUI, int delay) {
        this.GUI = GUI;
        this.delay = delay;
    }

    @Override
    public void run() {
        while (running) {
            try {
                GUI.setBoard(Cykl.MakeACycle(GUI.getBoard()));
                Thread.sleep(this.delay);
                GUI.draw(10);
            } catch (InterruptedException ex) {
                System.out.println("Watek dostal sygnal interrupted");
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
