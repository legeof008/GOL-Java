package main;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.WritableImage;
import javafx.scene.transform.Transform;

import javax.imageio.ImageIO;
import java.io.File;

/**
 * Klasa zapisuje stan z Canvas w plik .png
 */
public class SnapshotWriter {
    Canvas canvas;
    File file;

    public SnapshotWriter(Canvas canvas, File file) {
        this.canvas = canvas;
        this.file = file;
    }

    /**
     * @param pixelScale
     * @return Zwraca snapshot potrzebny do zapisania z obrazu w formacie WritableImage
     */

    public WritableImage currentStateSnapshot(double pixelScale) {

        WritableImage writableImage = new WritableImage((int) Math.rint(pixelScale * this.canvas.getWidth()), (int) Math.rint(pixelScale * this.canvas.getHeight()));
        SnapshotParameters spa = new SnapshotParameters();

        spa.setTransform(Transform.scale(pixelScale, pixelScale));

        return canvas.snapshot(spa, writableImage);
    }

    /**
     * @param source
     * @throws Exception Zapisuje z WritableImage do pliku .png
     */
    public void saveImage(WritableImage source) throws Exception {

        if (file != null)
            ImageIO.write(SwingFXUtils.fromFXImage(source, null), "png", this.file);

    }
}
