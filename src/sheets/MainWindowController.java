package sheets;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

public class MainWindowController {

    @FXML
    public void printSpreadsheet(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("spreadsheet.fxml"));
        SpreadsheetController controller = loader.getController();
        controller.print();

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class MainWindowController {
    @FXML private String fileName;
    public void readFile(){
        try{
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bf = new BufferedReader(fileReader);
            while(()){

            }
        }
        catch(FileNotFoundException e){
            System.out.println("Unable to open file \"" + fileName + "\"");
        }
    }
}
