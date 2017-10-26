package sheets;

import java.io.*;

public class PrintToHtml {
    private File html;
    PrintToHtml(String fileName){
        html = new File(fileName);
    }
    boolean addParameterToElement(String parameter, String value, String id){
        boolean success = false;
        try(
                FileReader fis = new FileReader(html);
                BufferedReader in = new BufferedReader(fis);
                FileOutputStream fos = new FileOutputStream(html);
                PrintWriter out = new PrintWriter(fos)
        ){

            String toAdd = parameter + "=\'" + value + "\' ";
            String line = "";
            String newDoc = "";
            while((line = in.readLine()) != null){
                int index = line.indexOf("id=\""+id+"\"");
                if(index >= 0){
                    String before = line.substring(0,index);
                    String after = line.substring(index, line.length());
                    String newLine = before + toAdd + after;
                    newDoc += newLine + "\n";
                    success = true;
                }
                else
                    newDoc += line + "\n";
            }
            out.write(newDoc);

        }
        catch(FileNotFoundException e){
            System.err.println("HTML File not found: "+html.getAbsolutePath());
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        return success;
    }
}
