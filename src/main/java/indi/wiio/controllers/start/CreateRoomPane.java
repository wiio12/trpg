package indi.wiio.controllers.start;


import indi.wiio.App;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.util.Pair;
import indi.wiio.controllers.mainwindow.KpMainWindowPane;
import indi.wiio.network.client.ClientMain;
import indi.wiio.network.client.UserStatusListener;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class CreateRoomPane implements Initializable {
    public FlowPane fp_online;
    public Button btn_keeper;
    public Button btn_player;
    public Region btn_back;

    private ArrayList<Pair<OnlinePerson, Parent>> onlinePeople = new ArrayList<>();
    private Scene scene;
    private StartPane startPane;
    private ServerListPane serverListPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //scene = btn_back.getScene();

        ClientMain.getClient().addUserStatusListener(new UserStatusListener() {
            @Override
            public void onUserOnline(String user, Image image) {

                Platform.runLater(() -> {
                    for(Pair<OnlinePerson, Parent> personParentPair: onlinePeople){
                        if(user.equalsIgnoreCase(personParentPair.getKey().getName())){
                            return;
                        }
                    }

                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/start/online_person.fxml"));
                    Parent parent = null;
                    try {
                        parent = fxmlLoader.load();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    OnlinePerson onlinePerson = fxmlLoader.getController();
                    onlinePeople.add(new Pair<>(onlinePerson, parent));
                    onlinePerson.img_profilePhoto.setImage(image);
                    onlinePerson.setName(user);
                    onlinePerson.lbl_playerName.setText(user);
                    fp_online.getChildren().add(parent);
                });
            }

            @Override
            public void onUserOffline(String user) {
                Platform.runLater(()->{
                    Pair<OnlinePerson, Parent> personParentPair = getOnlinePerson(user);
                    if (personParentPair != null) {
                        onlinePeople.remove(personParentPair);
                        fp_online.getChildren().remove(personParentPair.getValue());
                    }
                });
            }
        });


        btn_player.setOnAction(actionEvent -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/start/card_chose_pane.fxml"));
                Parent parent = fxmlLoader.load();
                CardChosePane cardChosePane = fxmlLoader.getController();
                cardChosePane.setCreateRoomPane(this);
                ClientMain.setPlayer(true);
                ((Stage)btn_player.getScene().getWindow()).setScene(new Scene(parent));

            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        btn_keeper.setOnAction(actionEvent -> {
            try {
                ClientMain.setKeeper(true);
                ClientMain.getClient().sendSelf();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/main/kp_main_window_pane.fxml"));
                Parent parent = fxmlLoader.load();
                KpMainWindowPane kpMainWindowPane = fxmlLoader.getController();
                App.setKpMainWindowPane(kpMainWindowPane);

                ClientMain.getClient().requestPlayerStatistic();
                ((Stage)btn_player.getScene().getWindow()).setScene(new Scene(parent));

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        btn_back.addEventHandler(MouseEvent.MOUSE_PRESSED, mouseEvent -> {
            if(startPane != null){
                try {

                    ClientMain.getClient().logout();
                    ((Stage)btn_back.getScene().getWindow()).setScene(startPane.getScene());
                    startPane = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(serverListPane != null){
                try {
                    ClientMain.getClient().logout();

                    ((Stage)btn_back.getScene().getWindow()).setScene(serverListPane.getScene());
                    serverListPane.handleTest();
                    serverListPane = null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private Pair<OnlinePerson, Parent> getOnlinePerson(String user) {
        for (Pair<OnlinePerson, Parent> personParentPair : onlinePeople) {
            if (personParentPair.getKey().getName().equalsIgnoreCase(user)) {
                return personParentPair;
            }
        }
        return null;
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

    public ServerListPane getServerListPane() {
        return serverListPane;
    }

    public void setServerListPane(ServerListPane serverListPane) {
        this.serverListPane = serverListPane;
    }
}
