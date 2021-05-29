package main;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

/**
 * Klasa sluzy do uzywania okienek w celu zwrocenia klasy File
 */
public class LoadSaveClass {
    /**
     * @param primaryStage
     * @return zwraca File odebrany z widoku okienka
     * @throws Exception
     */
    public File useLoader(Stage primaryStage) throws NullPointerException {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extensionFilter);
        fileChooser.setTitle("Otworz plik tekstowy");
        return fileChooser.showOpenDialog(primaryStage);
    }

    /**
     * @param primaryStage
     * @return zwraca File wybrany z okienka zapisywania
     */

    public File useSaver(Stage primaryStage) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
        fileChooser.getExtensionFilters().add(extensionFilter);
        fileChooser.setTitle("Save");
        return fileChooser.showSaveDialog(primaryStage);
    }
}
