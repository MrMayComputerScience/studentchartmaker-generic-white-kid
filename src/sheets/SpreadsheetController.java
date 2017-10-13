package sheets;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;

public class SpreadsheetController {
    @FXML private GridPane pane;
    @FXML private TableView sheet;
    @FXML private Text headhead;
    @FXML public void initialize(){

    }
    public void print(){
        Printer printer = null;
        for(Printer p : Printer.getAllPrinters()){
            System.out.println(p.getName());
            if(p.getName().contains("XPS"))
                printer = p;
        }
        PrinterJob pj = PrinterJob.createPrinterJob();
        pj.setPrinter(printer);
        Stage popup = new Stage();
        boolean boolContinue = false;
        boolContinue = pj.showPageSetupDialog(popup);
        if(boolContinue){
            popup.requestFocus();
            boolContinue = pj.showPrintDialog(popup);
            if(boolContinue){
                pj.printPage(pane);
            }
        }
        pj.endJob();
    }
    public void createColumns(int x)
    {
        for(int i = 0; i < x; i++)
        {
            sheet.getColumns().add(new TableColumn());
        }
    }

    public void createHeader(String header)
    {
        headhead.setText(header);
    }
}
