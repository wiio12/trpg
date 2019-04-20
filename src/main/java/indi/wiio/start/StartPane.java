package indi.wiio.start;

import indi.wiio.info.Self;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Pair;
import indi.wiio.network.client.Client;
import indi.wiio.network.client.ClientMain;
import indi.wiio.network.client.SystemMessageListener;
import org.apache.commons.io.FileUtils;
import indi.wiio.utils.Univ;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StartPane implements Initializable {

    public TextField tf_playerName;
    public ImageView img_profilePhoto;
    public Button btn_createRoom;
    public Button btn_joinRoom;

    private Stage parentStage;
    private File imgFile;
    private Image image;
    private Scene scene;
    private Pair<CreateRoomPane, Parent> createRoomPaneParentPair;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Self.readMapFromFile();

        tf_playerName.setText(Self.getSelf().getName());
        imgFile = Self.getSelf().getProfilePhotoFile();
        image = Self.getSelf().getProfilePhoto();

        Self.getSelf().nameProperty().bind(tf_playerName.textProperty());
        img_profilePhoto.setImage(image);


        img_profilePhoto.setOnMouseClicked(mouseEvent -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            File imageFile = fileChooser.showOpenDialog(parentStage);
            if (imageFile != null) {
                imgFile = new File("./user/resources/" + imageFile.getName());
                try {
                    FileUtils.copyFile(imageFile, imgFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                image = new Image(imgFile.toURI().toString());
                img_profilePhoto.setImage(image);
                Self.getSelf().setProfilePhoto(image);
                Self.getSelf().setProfilePhotoFile(imgFile);
            }
        });


    }

    public Stage getParentStage() {
        return parentStage;
    }

    public void setParentStage(Stage parentStage) {
        this.parentStage = parentStage;
    }

    private boolean handleLogin() {

        String name = tf_playerName.getText();
        Image image = img_profilePhoto.getImage();
        try {
            ClientMain.getClient().connect("localhost", 8818);
            ClientMain.getClient().login(name, image, Univ.getFileType(imgFile.getName()));
            ClientMain.getClient().joinTopic("#COMMON");
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public void handleCreate(ActionEvent actionEvent) {
        if(tf_playerName.getText().equals("")){
            tf_playerName.setPromptText("请填写名字");
            return;
        }
        if(handleLogin()){
            Self.writeMapToFile();
            createRoomPaneParentPair.getKey().setStartPane(this);

            if(createRoomPaneParentPair.getKey().getScene() != null)
                ((Stage)tf_playerName.getScene().getWindow()).setScene(createRoomPaneParentPair.getKey().getScene());
            else {
                Scene scene = new Scene(createRoomPaneParentPair.getValue());
                createRoomPaneParentPair.getKey().setScene(scene);
                ((Stage) tf_playerName.getScene().getWindow()).setScene(scene);
            }
        }

    }

    public void handleJoin(ActionEvent actionEvent) {
        if(tf_playerName.getText().equals("")){
            tf_playerName.setPromptText("请填写名字");
            return;
        }
        try {
            Self.writeMapToFile();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/start/server_list_pane.fxml"));
            Parent parent = fxmlLoader.load();
            ServerListPane serverListPane = fxmlLoader.getController();
            serverListPane.setCreateRoomPaneParentPair(createRoomPaneParentPair);
            serverListPane.setParentStage(parentStage);
            serverListPane.setStartPane(this);
            Scene scene = new Scene(parent);
            serverListPane.setScene(scene);
            parentStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public Pair<CreateRoomPane, Parent> getCreateRoomPaneParentPair() {
        return createRoomPaneParentPair;
    }

    public void setCreateRoomPaneParentPair(Pair<CreateRoomPane, Parent> createRoomPaneParentPair) {
        this.createRoomPaneParentPair = createRoomPaneParentPair;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }
}