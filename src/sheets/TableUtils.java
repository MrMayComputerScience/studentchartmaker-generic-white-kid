package sheets;

import com.sun.javafx.scene.control.skin.TableViewSkin;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TableUtils{
    private static Method columnResizeMethod;
    private static Method setTableSkin;
    static{
        try{
            columnResizeMethod = TableViewSkin.class.getDeclaredMethod("resizeColumnToFitContent", TableColumn.class, int.class);
            columnResizeMethod.setAccessible(true);
        }
        catch(NoSuchMethodException e){
            System.err.println("No such method in TableUtils");
            e.printStackTrace();
        }
        try{
            setTableSkin = TableView.class.getDeclaredMethod("createDefaultSkin");
            setTableSkin.setAccessible(true);
        }
        catch(NoSuchMethodException e){
            e.printStackTrace();
        }
    }
    static void fitTableToContents(TableView tableView){
        tableView.getItems().addListener((ListChangeListener)c ->{
            for(Object col : tableView.getColumns()){
                TableColumn column = (TableColumn) col;
                try{
                    System.out.println("Skin is null? "+(tableView.getSkin() == null));
                    columnResizeMethod.invoke(tableView.getSkin(), column, -1);
                }
                catch(InvocationTargetException | IllegalAccessException | NullPointerException e){
                    System.err.println("Error formatting column with name: "+column.getText());
                    e.printStackTrace();
                }
            }
        });
    }
    static void setDefaultTableSkin(TableView tableView){
        if(tableView.getSkin() == null){
            try{
                tableView.setSkin((TableViewSkin)setTableSkin.invoke(tableView));
            }
            catch (InvocationTargetException | IllegalAccessException e){
                e.printStackTrace();
            }

        }
    }
}
