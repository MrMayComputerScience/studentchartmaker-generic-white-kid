package sheets;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

public class JSoupUtils {
    private Document document;

    public static void main(String[] args){
        JSoupUtils jsu = new JSoupUtils("src/sheets/spreadsheet.html");
        jsu.addChildElement("<tr><th>New Header</th></tr>", "test");
    }
    public JSoupUtils(String fileName){
        File file = new File(fileName);
        File copyFile = new File(file.getParentFile().getAbsolutePath()+"/copy-"+file.getName());
        try{
            FileOutputStream fos = new FileOutputStream(copyFile);
            Files.copy(file.toPath(), fos);
            document = Jsoup.parse(copyFile, "UTF-8");

        }
        catch(IOException e){
            e.printStackTrace();
            System.exit(1);
        }
    }
    void addChildElement(String element, String id){
        Element elem = document.getElementById(id);
        System.out.println("Elem: "+elem.toString());
        elem.append(element);
        System.out.println("Elem: "+elem.toString());
    }
}
