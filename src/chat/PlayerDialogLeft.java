package chat;

import info.Others;
import info.Self;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.util.Pair;
import network.client.ClientMain;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class PlayerDialogLeft implements Initializable {

    private String imageUrl;

    private DialogPanel dialogPanel;
    private ChatPane chatPane;
    private String userName;


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
            selectableText.getStyleClass().add("selectableTextMessageLeft");
            msgBox.setLeft(selectableText);
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


        avatar.setOnContextMenuRequested( event -> {
            ContextMenu contextMenu = new ContextMenu();
            MenuItem item = new MenuItem("发起聊天");
            item.setOnAction( e -> {
                String name1 = userName;
                String name2 = Self.getSelf().getName();
                String topicName;
                if(name1.compareTo(name2) > 0){
                    topicName = "#" + name1 + '/' + name2;
                } else {
                    topicName = "#" + name2 + '/' + name1;
                }

                //如果已经打开过
                ObservableList<Tab> tabs = chatPane.tabPane.getTabs();
                for( Tab t : tabs){
                   if( t.getText().equalsIgnoreCase(topicName) ){
                       chatPane.tabPane.getSelectionModel().select(t);
                       return;
                   }
                }

                Pair<DialogPanel, Parent> p = chatPane.getDialogPanel(topicName);
                if(p != null) {// 如果已经打开过然后删了：
                    chatPane.reOpenTab(p.getKey());
                    chatPane.tabPane.getSelectionModel().selectLast();
                    return;
                }

                // 如果是全新打开的：
                chatPane.addChatTab(topicName);
                chatPane.tabPane.getSelectionModel().selectLast();

                try {
                    ClientMain.getClient().joinTopic(topicName);
                    ClientMain.getClient().callTopic(topicName, userName);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

            });
            MenuItem item2 = new MenuItem("查看人物卡");
            contextMenu.getItems().addAll(item, item2);

            contextMenu.show(avatar, event.getScreenX(), event.getScreenY());
        });
    }


    public DialogPanel getDialogPanel() {
        return dialogPanel;
    }

    public void setDialogPanel(DialogPanel dialogPanel) {
        this.dialogPanel = dialogPanel;
    }

    public ChatPane getChatPane() {
        return chatPane;
    }

    public void setChatPane(ChatPane chatPane) {
        this.chatPane = chatPane;
    }

    public void setAvatar(String userName) {
        Others others = Others.getOthersMap().get(userName);
        avatar.setImage(others.getImage());
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


}
