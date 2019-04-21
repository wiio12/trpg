package indi.wiio.controllers.start;

import indi.wiio.info.character.Resources;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import indi.wiio.controllers.mainwindow.MainWindowPane;
import indi.wiio.network.client.ClientMain;
import indi.wiio.controllers.player.HeaderPane;
import indi.wiio.controllers.player.PaneStatus;
import indi.wiio.controllers.player.PlayerInfoPane;
import indi.wiio.controllers.player.full.PlayerStatistics;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class CardPane implements Initializable {
    public VBox vb_card;
    public HBox hb_close;
    public Region btn_delete;
    public HBox hb_buttons;
    public Button btn_edit;
    public Button btn_start;
    @FXML private HeaderPane headerPaneController;
    @FXML private PlayerInfoPane playerInfoPaneController;
    private String mainRes;
    private HBox container;

    public CardPane(String mainRes){
        this.mainRes = mainRes;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playerInfoPaneController.setCardResource(mainRes);
        playerInfoPaneController.setPaneStatus(PaneStatus.SHOW_CARD);

        headerPaneController.setCardRes(mainRes);
        headerPaneController.setPaneStatus(PaneStatus.SHOW_CARD);

        hb_buttons.setVisible(false);
        hb_close.setVisible(false);


        vb_card.addEventHandler(MouseEvent.MOUSE_ENTERED, mouseEvent -> {
            hb_buttons.setVisible(true);
            hb_close.setVisible(true);
        });

        vb_card.addEventHandler(MouseEvent.MOUSE_EXITED, mouseEvent -> {
            hb_buttons.setVisible(false);
            hb_close.setVisible(false);
        });


        btn_delete.addEventHandler(MouseEvent.MOUSE_PRESSED, mouseEvent -> {
            Resources.getResourcesMap().remove(mainRes);
            Resources.writeMapToFile();
            try {
                //TODO:这里会报错的，搞定一下吧
                ((HBox) vb_card.getParent()).getChildren().remove(vb_card.getParent());
            }catch (Exception e){
                System.out.println("card 被删了");
            }
        });

        btn_edit.setOnAction(actionEvent -> {
            Resources.setMainResource(mainRes);
            Resources.setupAllRelation();

            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/player/full/player_statistics.fxml"));
                PlayerStatistics playerStatistics = new PlayerStatistics(PaneStatus.EDIT_FULL);
                playerStatistics.setMainResource(mainRes);
                fxmlLoader.setController(playerStatistics);
                Parent parent = fxmlLoader.load();
                Stage stage = new Stage();
                stage.setTitle("编辑人物卡");
                stage.setScene(new Scene(parent));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

        btn_start.setOnAction(actionEvent -> {
            Resources.setMainResource(mainRes);
            Resources.setupAllRelation();
          //  Others.writeMySelfToFile();
            try {
                ClientMain.getClient().sendSelf();
                ClientMain.getClient().requestPlayerStatistic();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/main/main_window_pane.fxml"));
                Parent parent = fxmlLoader.load();
                MainWindowPane mainWindowPane = fxmlLoader.getController();
                ((Stage)btn_start.getScene().getWindow()).setScene(new Scene(parent));
            } catch (IOException e) {
                e.printStackTrace();
            }


        });
    }

    public String getMainRes() {
        return mainRes;
    }

    public void setMainRes(String mainRes) {
        this.mainRes = mainRes;
    }
}
