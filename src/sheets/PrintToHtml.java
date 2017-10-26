package sheets;

import java.io.*;

public class PrintToHtml {
    private File html;
    PrintToHtml(String fileName){
        html = new File(fileName);
    }
    boolean addParameterToElement(String parameter, String value, String id){
        try(
                FileReader fis = new FileReader(html);
                BufferedReader in = new BufferedReader(fis);
                FileOutputStream fos = new FileOutputStream(html);
                PrintWriter out = new PrintWriter(fos)
        ){
            String line = "";
            int currentLine = 0;
            while((line = in.readLine()) != null){
                currentLine++;
                int index = line.indexOf("id=\""+id+"\"");
                if(index >= 0){
                    index--;

                }
            }
        }
        catch(FileNotFoundException e){

        }
        catch(IOException e){

        }
    }
}
