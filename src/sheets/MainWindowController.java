package sheets;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
    public void setConfigFile(){
        String[] fileNames = readFirstFile();
        String[] contents = getFileContents(fileNames);
        for(String s : contents){
            System.out.println(s);
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
    private String readFileHelper(String fileName){
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
        FileChooser chooser = new FileChooser();
        chooser.setTitle("");
        File file = chooser.showOpenDialog(outside.getScene().getWindow());
        if(file == null){
            System.out.println("please enter a valid file name");
            Platform.exit(); //TODO REPLACE this
        }
        try{
            FileReader fileReader = new FileReader(file);
            BufferedReader bf = new BufferedReader(fileReader);

            int count = 0;
            while((line = bf.readLine()) != null){
                line = line.toLowerCase();
                if(line.contains("students =")){
                    studentFile = line;
                    String[] studentFileSplit = studentFile.split("= ");
                    studentFile = studentFileSplit[1];
                    fileArray[0] = studentFile;
                    count++;
                }
                else if(line.contains("columns =")){
                    columnFile = line;
                    String[] columnFileSplit = columnFile.split("= ");
                    columnFile = columnFileSplit[1];
                    fileArray[1] = columnFile;
                    count++;
                }
                else if(line.contains("header =")){
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
            for(String s : fileArray)
                System.out.println(s);
        }
        return fileArray;
    }

}
