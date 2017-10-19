package sheets;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.util.Arrays;


public class SpreadsheetController {
    @FXML private GridPane pane;
    @FXML private TableView sheet;
    @FXML private Text headhead;
    private int longestLength;
    private Stage myStage;

    @FXML public void initialize(){
        pane.sceneProperty().addListener((observable, oldVal, newVal)->{
            pane.getScene().windowProperty().addListener(((observable1, oldValue, newValue) -> {
                myStage = (Stage)newValue;
                pane.prefWidthProperty().bind(myStage.widthProperty());
                sheet.prefWidthProperty().bind(pane.widthProperty().subtract(5));
                TableColumn stuCol = (TableColumn) sheet.getColumns().get(0);
                stuCol.setPrefWidth(longestLength*18);
            }));
        });

        longestLength = -1;
    }
    public void format(String[] students, String[] columns, String header){
        createStudents(students);
        createColumns(columns);
        createHeader(header);

    }
    public void format(String[] students, int columns, String header){
        createStudents(students);
        createColumns(columns);
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
                    //Arrays.sort(students);
                    String[] temp = students[i].split(" ");
                    temp[0] = temp[0].substring(0,temp[0].length()-1);
                    String student = temp[1] + " " + temp[0];
                    if(student.length() > longestLength)
                        longestLength = student.length();
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

    private PageLayout createPageLayoutLandscape(Printer printer){
        PageLayout makeItLandscape = printer.createPageLayout(printer.getDefaultPageLayout().getPaper(), PageOrientation.LANDSCAPE,
                printer.getDefaultPageLayout().getLeftMargin(),
                printer.getDefaultPageLayout().getRightMargin(),
                printer.getDefaultPageLayout().getTopMargin(),
                printer.getDefaultPageLayout().getBottomMargin());
        return makeItLandscape;
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
                double width = pj.getJobSettings().getPageLayout().getPrintableWidth();
                double height = pj.getJobSettings().getPageLayout().getPrintableHeight();
                pane.prefWidth(width);
                pane.prefHeight(height);


                pj.printPage(pane);
            }
        }

        pj.endJob();

    }

}
