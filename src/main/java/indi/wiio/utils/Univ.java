package indi.wiio.utils;

import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.converter.NumberStringConverter;
import org.apache.commons.lang3.math.NumberUtils;

public class Univ {
    public static void printErrMessage(String msg){
        System.out.println(msg);

    }

    public static void textFieldNumberOnlyLimiter(TextField tf, String oldValue, String newValue){
        if(!tf.getText().equals("")){
            if(!NumberUtils.isCreatable(newValue)) tf.setText(oldValue);
        }
    }

    public static void handleTriProperty(TextField tf, Label lUp, Label lDown, String oldValue, String newValue){
        Univ.textFieldNumberOnlyLimiter(tf,oldValue,newValue);
        int val = 0;
        if(!tf.getText().equals("") && NumberUtils.isCreatable(tf.getText()))
            try {
                val = NumberUtils.createInteger(tf.getText());
            }catch (Exception e){

            }
        lUp.setText("" + val / 2);
        lDown.setText("" + val / 5);
    }

//    public  static void tableViewSetAutoResize(TableView <?> tableView, int fixedSize, int add1, int add2){
//        tableView.setFixedCellSize(fixedSize);
//        tableView.prefHeightProperty().bind(Bindings.size(tableView.getItems()).multiply(tableView.getFixedCellSize()).add(add1));
//        tableView.prefHeightProperty().bind(tableView.prefHeightProperty().add(add2));
//
//    }
//
//    public  static void tableViewSetAutoResize(TableView <?> tableView, int fixedSize){
//        tableViewSetAutoResize(tableView, fixedSize, 28,27);
//    }

    public static void bindTextAndInt(TextField textField, IntegerProperty integerProperty){
        IntegerProperty tmp = new SimpleIntegerProperty();
        tmp.bind(integerProperty);
        tmp.unbind();
        Bindings.bindBidirectional(textField.textProperty(), tmp, new NumberStringConverter());
        integerProperty.bind(tmp);
    }

    public static void bindTextAndInt(Label textField, IntegerProperty integerProperty){
        IntegerProperty tmp = new SimpleIntegerProperty();
        Bindings.bindBidirectional(textField.textProperty(), tmp, new NumberStringConverter());
        integerProperty.bind(tmp);
    }

    public static String getFileType(String fileName){
        int i = fileName.lastIndexOf('.');
        return fileName.substring(i + 1, fileName.length());
    }


}
