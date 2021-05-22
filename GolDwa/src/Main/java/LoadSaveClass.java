package Main.java;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class LoadSaveClass {
    public File useLoader(Stage primaryStage) throws Exception {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extensionFilter);
        fileChooser.setTitle("Otworz plik tekstowy");
        return fileChooser.showOpenDialog(primaryStage);
    }

    public File useSaver(Stage primaryStage) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
        fileChooser.getExtensionFilters().add(extensionFilter);
        fileChooser.setTitle("Save");
        return fileChooser.showSaveDialog(primaryStage);
    }
}
