package player.small;


import utils.Univ;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class SkillTemplate implements Initializable {
    private String name;
    public Label nameLabel;
    public TextField textField;
    public Label labelUp;
    public Label labelDown;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        textField.textProperty().addListener(((observableValue, s, t1) -> {
            Univ.handleTriProperty(textField, labelUp, labelDown, s, t1);
        }));

    }

    public void setAll(String labelName, String name){
        nameLabel.setText(labelName);
        this.name = name;
    }

    public void setLabelName(String name){
        this.nameLabel.setText(name);
    }

    public String getLabelName(){
        return nameLabel.getText();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



}
