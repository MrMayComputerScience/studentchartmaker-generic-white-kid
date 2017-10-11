package sheets;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.*;

public class MainWindowController {
    @FXML private String fileName;
    @FXML private TextField fileField;
    @FXML private VBox outside;

    @FXML public void initialize(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("spreadsheet.fxml"));
        try{
            Parent node = loader.load();
            outside.getChildren().add(node);
        }
        catch(IOException e){
            e.printStackTrace();
        }

    }
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
    ///First value is students text, second = columns, third = header
    public String[] getFileContents(String[] fileNameArr){
        String[] arr = new String[3];
        arr[0] = readFileHelper(fileNameArr[0]);
        arr[1] = readFileHelper(fileNameArr[1]);
        arr[2] = readFileHelper(fileNameArr[2]);
        return arr;
    }
    public String readFileHelper(String fileName){
        String contents = "";
        File file = new File(fileName);

        return contents;
    }
    ///Returns an array where the first value is the students.txt name, second is the columns.txt, and 3rd is header.txt
    public String[] readFirstFile(){
        String line = null;
        String studentFile;
        String columnFile;
        String headerFile;
        String[] fileArray = new String[3];
        //TODO: Add file chooser
        FileChooser chooser = new FileChooser();
        chooser.setTitle("");
        File file = chooser.showOpenDialog(outside.getScene().getWindow());
        fileName = file.getName();
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
                else{
                    System.err.println("FILE NOT FORMATTED PROPERLY");
                }

            }
        }
        catch(FileNotFoundException e){
            System.out.println("File \"" + fileName + "\" not found");
        }
        catch(IOException e){
            System.out.println("Error reading file \"" + fileName + "\"");
        }
        return fileArray;
    }

}
