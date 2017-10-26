package sheets;

import org.jsoup.nodes.Document;

import java.io.*;

@Deprecated
public class PrintToHtml {
    private File html;
    PrintToHtml(String fileName){
        html = new File(fileName);
    }
    public static void main(String[] args){
        PrintToHtml printer = new PrintToHtml("src/sheets/spreadsheet.html");
        printer.addParameterToElement("class", "centered", "test");
    }
    boolean addElementToElement(String element, String id){
        boolean success = false;
        try(
                FileReader fis = new FileReader(html);
                BufferedReader in = new BufferedReader(fis);
                FileOutputStream fos = new FileOutputStream(html);
                PrintWriter out = new PrintWriter(fos)
        ){
            String line = "";
            String newDoc = "";
            while((line = in.readLine()) != null){
                line = line.replaceAll("'", "\"");
                if(line.contains("id=\""+id+"\"")){
                    String newLine = line + "\n";
                    newLine += element;
                    newDoc += newLine + "\n";
                    success = true;
                }
                else{
                    newDoc += line + "\n";
                }
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
                System.out.println("INt the loop");
                line = line.replaceAll("'", "\"");
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
            System.out.println(newDoc);
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
