package sheets;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

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
    ///Order of values is always students, columns, header
    public String[] getFileContents(String[] fileNameArr){
        String[] contents = new String[fileNameArr.length];
        for(int i = 0; i < contents.length; i++){
            contents[i] = readFileHelper(fileNameArr[i]);
        }
        return contents;
    }
    public String readFileHelper(String fileName){
        String contents = "";
        File file = new File(fileName);
        try(FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr)){
            while(br.readLine() != null){
                contents += br.readLine() + "\n";
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return contents;
    }
    ///Returns an array where the order of values is always students.txt, columns.txt, header.txt
    public String[] readFirstFile(){
        String line = null;
        String studentFile;
        String columnFile;
        String headerFile;
        List<String> list = new LinkedList<String>();
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
            int count = 0;
            while((line = bf.readLine()) != null){
                if(line.contains("Students =")){
                    studentFile = line;
                    String[] studentFileSplit = studentFile.split("= ");
                    studentFile = studentFileSplit[1];
                    fileArray[0] = studentFile;
                    count++;
                }
                else if(line.contains("Columns =")){
                    columnFile = line;
                    String[] columnFileSplit = columnFile.split("= ");
                    columnFile = columnFileSplit[1];
                    fileArray[1] = columnFile;
                    count++;
                }
                else if(line.contains("Header =")){
                    headerFile = line;
                    String[] headerFileSplit = headerFile.split("= ");
                    headerFile = headerFileSplit[1];
                    fileArray[2] = headerFile;
                    count++;
                }
                else if(count == 3){
                    for(String s : fileArray){
                        list.add(s);
                    }
                    count = 0;
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
        if(list.size() > 0){
            list.toArray(fileArray);
        }
        return fileArray;
    }

}
