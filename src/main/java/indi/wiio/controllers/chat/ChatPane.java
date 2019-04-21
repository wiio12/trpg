package indi.wiio.controllers.chat;

import indi.wiio.info.Others;
import indi.wiio.info.Self;
import indi.wiio.info.character.Resources;
import javafx.application.Platform;
import indi.wiio.utils.Univ;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.util.Pair;
import indi.wiio.network.client.ClientMain;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ChatPane implements Initializable {
    public BorderPane rootPane;
    public TextArea msgTextArea;
    public TabPane tabPane;

    private ArrayList<Pair<DialogPanel, Parent>> dialogPanelList = new ArrayList<>();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ClientMain.getClient().addMsgListener((fromUser, msgBody) -> {
            Platform.runLater(()->handleSendL(fromUser, msgBody));
        });

        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.SELECTED_TAB);

        addChatTab("#COMMON");  //默认建立这个COMMON区域，作为大家都会进入的



        msgTextArea.setOnKeyPressed( keyEvent -> {
            final KeyCombination kb = new KeyCodeCombination(KeyCode.ENTER, KeyCombination.CONTROL_DOWN);
            if(kb.match(keyEvent)){
                msgTextArea.appendText("\n");
            }else if(keyEvent.getCode() == KeyCode.ENTER){
                handleSendR();
            }
        });



    }

    public DialogPanel addChatTab(String tabName) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/chat/dialog_panel.fxml"));
        Parent scrollTab = null;
        try {
            scrollTab = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        DialogPanel dialogPanel = fxmlLoader.getController();
        Pair<DialogPanel, Parent> panelParentPair = new Pair<>(dialogPanel, scrollTab);

        //store the controller
        dialogPanel.setTopicName(tabName);
        dialogPanel.setTabOpend(true);
        dialogPanelList.add(panelParentPair);

        Tab tab = new Tab(tabName);
        if("#COMMON".equalsIgnoreCase(tabName)) tab.closableProperty().setValue(false);
        tab.setContent(scrollTab);
        tab.setOnClosed(event -> handleTabClose(dialogPanel));
        tabPane.getTabs().add(tab);

        return dialogPanel;
    }

    private void handleTabClose(DialogPanel dialogPanel) {
        dialogPanel.setTabOpend(false);
    }

    public void reOpenTab(DialogPanel dialogPanel){
        dialogPanel.setTabOpend(true);
        Tab tab = new Tab(dialogPanel.getTopicName());
        tab.setOnClosed(event -> handleTabClose(dialogPanel));
        tab.setContent(getDialogPanel(dialogPanel.getTopicName()).getValue());
        tabPane.getTabs().add(tab);
    }


    public void handleSendR(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/chat/player_dialog_right.fxml"));
            Parent rightDialog = fxmlLoader.load();
            PlayerDialogRight playerDialog = fxmlLoader.getController();
            String msg = msgTextArea.getText().trim();
            if(ClientMain.isPlayer())
                playerDialog.setName(Resources.getResources().getPlayerInfo().getName());
            else if(ClientMain.isKeeper())
                playerDialog.setName(Self.getSelf().getName());
            playerDialog.setMsg(msg);
            msgTextArea.setText("");

            String tabName = tabPane.getSelectionModel().getSelectedItem().getText();
            DialogPanel d = this.getDialogPanel(tabName).getKey();
            if( d != null){
                d.getChatVBox().getChildren().add(rightDialog);
            } else {
                Univ.printErrMessage("topic not exist");
            }

            // 服务器发送部分
            ClientMain.getClient().msg(tabName, msg);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleSendL(String fromUser, String msg){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/chat/player_dialog_left.fxml"));
            Parent leftDialog= fxmlLoader.load();
            PlayerDialogLeft playerDialog = fxmlLoader.getController();

            String [] names = StringUtils.split(fromUser, '@');
            String topicName = names[0];
            String userName = names[1];


            playerDialog.setAvatar(userName);
            playerDialog.setUserName(userName);
            System.out.println(Others.getOthersMap().get(userName));
            System.out.println(Others.getOthersMap().get(userName).getResources());
            System.out.println(Others.getOthersMap().get(userName).getResources().getPlayerInfo());
            System.out.println(Others.getOthersMap().get(userName).getResources().getPlayerInfo().getName());
            playerDialog.setName(Others.getOthersMap().get(userName).getResources().getPlayerInfo().getName());
            playerDialog.setChatPane(this);
            playerDialog.setMsg(msg.trim());

            Pair<DialogPanel, Parent> d = this.getDialogPanel(topicName);
            DialogPanel dialogPanel;
            if( d == null) dialogPanel = addChatTab(topicName);
            else dialogPanel = d.getKey();

            if(!dialogPanel.isTabOpend()) reOpenTab(dialogPanel);

            tabPane.getSelectionModel().selectLast();
            dialogPanel.getChatVBox().getChildren().add(leftDialog);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Pair<DialogPanel, Parent> getDialogPanel(String tabName){
        for(Pair<DialogPanel, Parent> p : dialogPanelList){
            if(p.getKey().getTopicName().equalsIgnoreCase(tabName))
                return p;
        }
        return null;
    }

}
