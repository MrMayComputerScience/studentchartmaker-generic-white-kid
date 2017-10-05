package sheets;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

public class MainWindowController {

    @FXML
    public void printSpreadsheet(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("spreadsheet.fxml"));
        SpreadsheetController controller = loader.getController();
        controller.print();
    }
}
