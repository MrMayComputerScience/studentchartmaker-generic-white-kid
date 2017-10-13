package sheets;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class MainWindowController {
    @FXML private String fileName;
    @FXML private TextField fileField;

    @FXML private TableView sheet;
    public void printSpreadsheet() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("spreadsheet.fxml"));
        SpreadsheetController controller = loader.getController();
        controller.print();
    }
    public void createColumns(String[] columns){
        for(int i = 0; i < columns.length; i++){
            sheet.getColumns().add(new TableColumn(columns[i]));
        }

    }

    public void createStudents(String[] students){
        for(int i = 0; i <students.length; i++){
            String[] temp = students[i].split(",");
            String student = temp[1] + temp[0];
            sheet.getItems().add(student);
        }
    }

    public String[] readFirstFile(){
        String line = null;
        String studentFile;
        String columnFile;
        String headerFile;
        String[] fileArray = new String[3];
        fileName = fileField.getText();
        if(fileName.length() <1){
            System.out.println("please enter a valid file name");
        }
        try{
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bf = new BufferedReader(fileReader);
            while((line = bf.readLine()) != null){
                if(line.contains("Students =")){
                    studentFile = line;
                    String[] studentFileSplit = studentFile.split("= ");
                    studentFile = studentFileSplit[1];
                    fileArray[0] = studentFile;
                }
                else if(line.contains("Columns =")){
                    columnFile = line;
                    String[] columnFileSplit = columnFile.split("= ");
                    columnFile = columnFileSplit[1];
                    fileArray[1] = columnFile;
                }
                else if(line.contains("Header =")){
                    headerFile = line;
                    String[] headerFileSplit = headerFile.split("= ");
                    headerFile = headerFileSplit[1];
                    fileArray[2] = headerFile;
                }

            }
        }
        catch(FileNotFoundException e){
            System.out.println("Unable to open file \"" + fileName + "\"");
        }
        catch(IOException e){
            System.out.println("Error reading file \"" + fileName + "\"");
        }
        return fileArray;
    }

}
