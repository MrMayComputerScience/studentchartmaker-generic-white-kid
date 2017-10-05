package sheets;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class MainWindowController {
    @FXML
    private String fileName;

    @FXML
    public void printSpreadsheet() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("spreadsheet.fxml"));
        try{
            loader.load();
        }
        catch(IOException e){
            e.printStackTrace();
        }

        SpreadsheetController controller = loader.getController();
        controller.print();
    }

    public void readFile() {
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bf = new BufferedReader(fileReader);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to open file \"" + fileName + "\"");
        }

    }
}