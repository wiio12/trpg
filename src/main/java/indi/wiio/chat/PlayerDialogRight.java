package indi.wiio.chat;

import indi.wiio.info.Self;
import indi.wiio.info.character.Resources;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import indi.wiio.network.client.ClientMain;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;


public class PlayerDialogRight implements Initializable {

    private String imageUrl;
    public ImageView avatar;
    public Label playerName;
    public Label msgText;
    public BorderPane msgBox;


    public void setName(String name) {
        playerName.setText(name);
    }

    public String getName(){
        return playerName.getText();
    }

    @SuppressWarnings("Duplicates")
    public void setMsg(String msg){
        msgText.setText(msg);
        selectablize();
    }

    @SuppressWarnings("Duplicates")
    private void selectablize() {
        if(msgText.getWidth() > 0 && msgText.getHeight() > 0) {
            TextArea selectableText = new TextArea(msgText.getText());
            selectableText.setPrefHeight(msgText.getHeight());
            selectableText.setPrefWidth(msgText.getWidth());
            selectableText.setFont(msgText.getFont());
            selectableText.setWrapText(true);
            selectableText.setEditable(false);
            selectableText.getStyleClass().add("selectableTextMessageRight");
            msgBox.setRight(selectableText);
        }
        else {
            Platform.runLater(this::selectablize);
        }
    }

    public String getMsg(){
        return msgText.getText();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(ClientMain.isPlayer())
            avatar.setImage(new Image(
                    new File(Resources.getResources().getHeader().getProfilePhotoPath()).toURI().toString()));
        else if(ClientMain.isKeeper()){
            avatar.setImage(new Image(new File(Self.getSelf().getProfilePhotoFile().getAbsolutePath()).toURI().toString()));
        }
    }
}
