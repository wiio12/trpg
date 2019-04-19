package start;

import info.Self;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.util.Pair;
import network.client.ClientMain;
import org.apache.commons.lang3.math.NumberUtils;
import utils.Univ;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ServerListPane implements Initializable {

    public TextField tf_serverName;
    public TextField tf_serverPort;
    public Label lbl_status;
    public Button btn_test;
    public Button btn_add;
    public Region btn_back;

    private Pair<CreateRoomPane, Parent> createRoomPaneParentPair;
    private Stage parentStage;
    private Scene scene;
    private StartPane startPane;


    public void handleTest() {
        if(tf_serverPort.getText().equals("") || tf_serverName.getText().equals("")){
            lbl_status.setText("请填写服务器名称和端口");
            return;
        }
        String serverName = tf_serverName.getText();
        int serverPort = NumberUtils.createInteger(tf_serverPort.getText());

        if(ClientMain.getClient().connect(serverName, serverPort)) {
            lbl_status.setText("连接成功！");
            btn_add.setDisable(false);
        }
		else
			lbl_status.setText("连接失败");
    }

    private void handleLogin() {
        String name = Self.getSelf().getName();
        Image image = Self.getSelf().getProfilePhoto();

        try {
            ClientMain.getClient().login(name, image, Univ.getFileType(Self.getSelf().getProfilePhotoFile().getName()));
            ClientMain.getClient().joinTopic("#COMMON");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleAdd(ActionEvent actionEvent) {
        handleLogin();
        createRoomPaneParentPair.getKey().setServerListPane(this);

        if(createRoomPaneParentPair.getKey().getScene() != null)
            ((Stage)btn_back.getScene().getWindow()).setScene(createRoomPaneParentPair.getKey().getScene());
        else {
            Scene scene = new Scene(createRoomPaneParentPair.getValue());
            createRoomPaneParentPair.getKey().setScene(scene);
            ((Stage)btn_back.getScene().getWindow()).setScene(scene);
        }
        //parentStage.setScene(new Scene(createRoomPaneParentPair.getValue(), 1200,700));

    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tf_serverPort.textProperty().addListener((observableValue, s, t1) ->
                Univ.textFieldNumberOnlyLimiter(tf_serverPort,s,t1));
        btn_add.setDisable(true);

        btn_back.addEventHandler(MouseEvent.MOUSE_PRESSED, mouseEvent -> {
            ((Stage)tf_serverPort.getScene().getWindow()).setScene(startPane.getScene());
        });
    }


    public Pair<CreateRoomPane, Parent> getCreateRoomPaneParentPair() {
        return createRoomPaneParentPair;
    }

    public void setCreateRoomPaneParentPair(Pair<CreateRoomPane, Parent> createRoomPaneParentPair) {
        this.createRoomPaneParentPair = createRoomPaneParentPair;
    }

    public Stage getParentStage() {
        return parentStage;
    }

    public void setParentStage(Stage parentStage) {
        this.parentStage = parentStage;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public StartPane getStartPane() {
        return startPane;
    }

    public void setStartPane(StartPane startPane) {
        this.startPane = startPane;
    }
}
