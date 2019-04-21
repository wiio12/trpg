package indi.wiio.controllers.player.full;

import indi.wiio.info.character.Resources;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;
import indi.wiio.controllers.player.*;

import java.net.URL;
import java.util.ResourceBundle;

public class PlayerStatistics implements Initializable {


    public ScrollPane scrollPane;
    public Button btn_save;
    public Button btn_cancel;
    private String mainResource;
    @FXML private HeaderPane headerPaneController;
    @FXML private BackgroundPane backgroundPaneController;
    @FXML private CompanionPane companionPaneController;
    @FXML private CthulhuPane cthulhuPaneController;
    @FXML private AssetPane assetPaneController;
    @FXML private ExperiencePane experiencePaneController;
    @FXML private FightPane fightPaneController;
    @FXML private ItemPane itemPaneController;
    @FXML private PlayerInfoPane playerInfoController;
    @FXML private PropertyPane propertyPaneController;
    @FXML private StatusPane statusPaneController;
    @FXML private WeaponPane weaponPaneController;
    @FXML private SkillTablePane skillTablePaneController;

    private PaneStatus paneStatus;

    public PlayerStatistics(PaneStatus paneStatus){
        this.paneStatus = paneStatus;
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        scrollPane.setVvalue(0.0);

        scrollPane.vvalueProperty().addListener(new ChangeListener<Number>() {
            private boolean flag = true;
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                if(flag) {
                    scrollPane.setVvalue(0);
                    flag = false;
                }
                if((double)t1 - (double)number > 0.1){
                    scrollPane.setVvalue((double)number);
                }
            }
        });

        initializePane(paneStatus);

        if(paneStatus == PaneStatus.EDIT_FULL) {
            btn_save.setOnAction(actionEvent -> {
                Resources.writeMapToFile();
                Stage stage = (Stage) btn_save.getScene().getWindow();
                stage.close();
            });
            btn_cancel.setOnAction(actionEvent -> {
                Resources.removeResources(mainResource);
                Stage stage = (Stage) btn_cancel.getScene().getWindow();
                stage.close();
            });
        }else if(paneStatus == PaneStatus.SHOW_FULL){
            btn_save.setVisible(false);
            btn_cancel.setVisible(false);
        }

    }

    @SuppressWarnings("Duplicates")
    private void initializePane(PaneStatus paneStatus){
        backgroundPaneController.setPaneStatus(paneStatus);
        backgroundPaneController.setPlayerStatistics(this);

        companionPaneController.setPaneStatus(paneStatus);
        companionPaneController.setPlayerStatistics(this);

        cthulhuPaneController.setPaneStatus(paneStatus);
        cthulhuPaneController.setPlayerStatistics(this);

        assetPaneController.setPaneStatus(paneStatus);
        assetPaneController.setPlayerStatistics(this);

        experiencePaneController.setPaneStatus(paneStatus);
        assetPaneController.setPlayerStatistics(this);

        fightPaneController.setPaneStatus(paneStatus);
        fightPaneController.setPlayerStatistics(this);

        itemPaneController.setPaneStatus(paneStatus);
        itemPaneController.setPlayerStatistics(this);

        playerInfoController.setPaneStatus(paneStatus);
        itemPaneController.setPlayerStatistics(this);

        propertyPaneController.setPaneStatus(paneStatus);
        itemPaneController.setPlayerStatistics(this);

        statusPaneController.setPaneStatus(paneStatus);
        itemPaneController.setPlayerStatistics(this);

        weaponPaneController.setPaneStatus(paneStatus);
        itemPaneController.setPlayerStatistics(this);

        skillTablePaneController.setPaneStatus(paneStatus);
        skillTablePaneController.setPlayerStatistics(this);

        headerPaneController.setPaneStatus(paneStatus);
        headerPaneController.setPlayerStatistics(this);
    }


    public String getMainResource() {
        return mainResource;
    }

    public void setMainResource(String mainResource) {
        this.mainResource = mainResource;
    }
}
