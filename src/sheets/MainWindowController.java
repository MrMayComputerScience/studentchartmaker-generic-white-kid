package sheets;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MainWindowController {
    @FXML private String fileName;
    @FXML private TextField fileField;
    @FXML private VBox outside;
    private List<Node> sheets;
    private List<SpreadsheetController> controllers;
    @FXML public void initialize(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("spreadsheet.fxml"));
        controllers = new ArrayList<SpreadsheetController>();
        sheets = new ArrayList<Node>();
        try{
            Parent node = loader.load();
            sheets.add(node);
            outside.getChildren().add(node);
            controllers.add(loader.getController());
        }
        catch(IOException e){
            e.printStackTrace();
        }

        

    }
    @FXML
    public void setConfigFile(){
        String[] fileNames = readFirstFile();
        String[] contents;
        try{
            contents = getFileContents(fileNames);
                String[][] stu = new String[contents.length/3][0];
                String[][] col = new String[contents.length/3][0];
                String header = "";
                for(int i = 0; i<contents.length; i++){
                    if(i%3 == 0){
                        String raw = contents[i];
                        String[] data = raw.split("\n");
                        stu[i/3] = data;
                    }
                    else if(i%3 == 1){
                        String raw = contents[i];
                        String[] data = raw.split("\n");
                        col[i/3] = data;
                    }
                    else{
                        header = contents[i];
                    }

                }
            controllers.get(0).format(stu[0],col[0],header);
            for(int i = 0; i<stu.length; i++){
                //controllers.get(i).format(stu[i],col[i],header);

            }
        }
        catch(NullPointerException e){
            System.out.println("This only happens when you cancel the file select");
        }


    }
    @FXML
    public void printSpreadsheet() {
        for(SpreadsheetController c : controllers){
            c.print();
        }
    }

    ///Order of values is always students, columns, header
    ///Each value is separated by a newline char \n
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
            String line = "";
            while((line = br.readLine()) != null){
                contents += line + "\n";
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return contents;
    }
    ///Returns an array where the order of values is always students.txt, columns.txt, header.txt
    public String[] readFirstFile(){
        String line = "";
        String studentFile;
        String columnFile;
        String headerFile;
        List<String> list = new LinkedList<String>();
        String[] fileArray = new String[3];
        FileChooser chooser = new FileChooser();
        Stage popup = new Stage();
        chooser.setTitle("");
        File file = chooser.showOpenDialog(popup);
        if(file == null){

          //  Platform.exit(); //TODO REPLACE this
        }
        if(!file.exists()){
            System.out.println("please enter a valid file name");
        }

            try {
                FileReader fileReader = new FileReader(file);
                BufferedReader bf = new BufferedReader(fileReader);
                String path = file.getParentFile().getPath() + "\\";
                System.out.println("Path = " + path);
                int count = 0;
                while ((line = bf.readLine()) != null) {
                    line = line.toLowerCase();
                    if (line.contains("students =")) {
                        studentFile = line;
                        String[] studentFileSplit = studentFile.split("= ");
                        studentFile = studentFileSplit[1];
                        fileArray[0] = path + studentFile;
                        count++;
                    } else if (line.contains("columns =")) {
                        columnFile = line;
                        String[] columnFileSplit = columnFile.split("= ");
                        columnFile = columnFileSplit[1];
                        fileArray[1] = path + columnFile;
                        count++;
                    } else if (line.contains("header =")) {
                        headerFile = line;
                        String[] headerFileSplit = headerFile.split("= ");
                        headerFile = headerFileSplit[1];
                        fileArray[2] = path + headerFile;
                        count++;
                    } else if(!line.trim().equals("")){
                        System.err.println("FILE NOT FORMATTED PROPERLY");
                    }
                    if (count == 3) {
                        System.out.println("OIts 3s");
                        for (String s : fileArray) {
                            list.add(s);
                        }
                        count = 0;
                    }


                }
            } catch (FileNotFoundException e) {
                System.out.println("File \"" + fileName + "\" not found");
            } catch (IOException e) {
                System.out.println("Error reading file \"" + fileName + "\"");
            }
            if (list.size() > 0) {
                fileArray = new String[list.size()];
                list.toArray(fileArray);
                for (String s : fileArray)
                    System.out.println("(readFirstFile)Filename: " + s);
            }

        return fileArray;
    }
    @FXML public void printOptions(){
        for(SpreadsheetController c : controllers){
            c.printOptions();
        }
    }

}
