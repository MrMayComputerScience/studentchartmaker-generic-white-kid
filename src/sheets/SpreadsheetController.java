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
        //FOR EXAMPLE
        String[] columns = new String[4];
        columns[0]="10/1";
        columns[1]="10/1";
        columns[2]="10/1";
        columns[3]="10/1";

        String[] students = new String[4];
        students[0] = "Shaulis, CP";
        students[1] = "McCully, Mason";
        students[2] = "Mattesonasfafsafafafsaf, Trevor";
        students[3] = "Gay, Gay";

        String header = "header";

        createColumns(columns);
        createStudents(students);
        createHeader(header);
        
    }
    public void createColumns(String[] columns){
        for(int i = 0; i < columns.length; i++){
            sheet.getColumns().add(new TableColumn(columns[i]));
        }

    }

    public void createStudents(String[] students){
        if(students.length > 0) {
            for (int i = 0; i < students.length; i++) {
                if (students[i].contains(",")) {
                    String[] temp = students[i].split(" ");
                    temp[0] = temp[0].substring(0,temp[0].length()-1);
                    String student = temp[1] + " " + temp[0];
                    sheet.getItems().add(new Text(student));
                }
                else{
                    System.out.println("Student Names are not Formatted Correctly");
                }


            }
        }
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

}
