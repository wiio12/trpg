package indi.wiio.chat;

import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

public class DialogPanel implements Initializable {
    public ScrollPane chatScrollPane;
    public VBox chatVBox;

    private String topicName;

    public boolean isTabOpend() {
        return tabOpend;
    }

    public void setTabOpend(boolean tabOpend) {
        this.tabOpend = tabOpend;
    }

    private boolean tabOpend;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        chatScrollPane.setFitToWidth(true);

        chatVBox.heightProperty().addListener((observableValue, oldValue, newValue) ->
                chatScrollPane.setVvalue((Double)newValue));
    }

    public ScrollPane getChatScrollPane() {
        return chatScrollPane;
    }

    public VBox getChatVBox() {
        return chatVBox;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

}
