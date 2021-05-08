package golGUI;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class LoadSaveClass {
    public void useLoader(Stage primaryStage) throws Exception //tu tez bedzie na file
    {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extensionFilter);
        fileChooser.setTitle("Open text file");
        File file = fileChooser.showOpenDialog(primaryStage);
        if(file != null) {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        }
    }
    public File useSaver(Stage primaryStage){
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("PNG files (*.png)", "*.png");
        fileChooser.getExtensionFilters().add(extensionFilter);
        fileChooser.setTitle("Save");
        return fileChooser.showSaveDialog(primaryStage);
    }
}
