package indi.wiio.controllers.player;

import indi.wiio.info.Others;
import indi.wiio.utils.Univ;
import indi.wiio.info.character.PlayerStatus;
import indi.wiio.info.character.Resources;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.util.converter.NumberStringConverter;
import indi.wiio.controllers.player.full.PlayerStatistics;
import indi.wiio.controllers.player.small.PlayerStatisticsSmall;

import java.net.URL;
import java.util.ResourceBundle;

public class StatusPane implements Initializable {
    public TextField tf_HP;
    public Label lbl_HPLimit;

    public TextField tf_San;
    public Label lbl_SanLimit;

    public TextField tf_Luck;
    public Label lbl_LuckLimit;

    public TextField tf_MP;
    public Label lbl_MPLimit;
    public TitledPane titlePane;

    public PlayerStatisticsSmall getPlayerStatisticsSmall() {
        return playerStatisticsSmall;
    }

    public void setPlayerStatisticsSmall(PlayerStatisticsSmall playerStatisticsSmall) {
        this.playerStatisticsSmall = playerStatisticsSmall;
    }
    private PlayerStatisticsSmall playerStatisticsSmall;
    private PlayerStatistics playerStatistics;
    private PaneStatus paneStatus;
    private PlayerStatus playerStatus;

    private String kp_userName;
    

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(Resources.getResources()!= null)
            playerStatus = Resources.getResources().getPlayerStatus();

        Platform.runLater(this::initializePaneStatus);
    }

    private void initializePaneStatus() {
        if(paneStatus == PaneStatus.EDIT_FULL)
            handleEditStatusInit();
        else if(paneStatus == PaneStatus.SHOW_FULL)
            handleShowFullStatusInit();
        else if(paneStatus == PaneStatus.SMALL)
            handleSmallStatusInit();
        else if(paneStatus == PaneStatus.KP)
            handleKp();
    }

    private void handleKp() {
        Others others = Others.getOthersMap().get(kp_userName);
        if(others != null){
            playerStatus = others.getResources().getPlayerStatus();
            handleSmallStatusInit();
        }else{
            System.out.println("kp:playerStatus");
        }

    }

    private void commonInit(){
        lbl_HPLimit.textProperty().bind(playerStatus.HPLimitProperty().asString());
        //lbl_SanLimit.textProperty().bind(playerStatus.sanityProperty().asString());
        lbl_MPLimit.textProperty().bind((playerStatus.MPLimitProperty().asString()));

        Bindings.bindBidirectional(tf_HP.textProperty(), playerStatus.hitPointProperty(), new NumberStringConverter());
        Bindings.bindBidirectional(tf_MP.textProperty(), playerStatus.magicPointProperty(), new NumberStringConverter());
        Bindings.bindBidirectional(tf_Luck.textProperty(), playerStatus.luckProperty(), new NumberStringConverter());
        Bindings.bindBidirectional(tf_San.textProperty(), playerStatus.sanityProperty(), new NumberStringConverter());



    }

    private void handleSmallStatusInit() {
        initializeStatus();
        titlePane.setCollapsible(true);

    }

    private void handleShowFullStatusInit() {
        initializeStatus();
        titlePane.setCollapsible(false);
    }

    private void handleEditStatusInit() {
        initializeStatus();
        titlePane.setCollapsible(false);
    }


    @SuppressWarnings("Duplicates")
    private void initializeStatus() {
        tf_HP.textProperty().addListener((obj, oldValue, newValue) -> Univ.textFieldNumberOnlyLimiter(tf_HP, oldValue, newValue));
        tf_San.textProperty().addListener((obj, oldValue, newValue) -> Univ.textFieldNumberOnlyLimiter(tf_San, oldValue, newValue));
        tf_Luck.textProperty().addListener((obj, oldValue, newValue) -> {
            Univ.textFieldNumberOnlyLimiter(tf_Luck, oldValue, newValue);
        });
        tf_MP.textProperty().addListener((obj, oldValue, newValue) -> Univ.textFieldNumberOnlyLimiter(tf_MP, oldValue, newValue));


        commonInit();
    }


//    public void updateHpLimit() {
//        TextField tf_CON = playerStatisticsSmall.getPropertyPane().tf_CON;
//        TextField tf_SIZ = playerStatisticsSmall.getPropertyPane().tf_SIZ;
//        int val = 0;
//        if(!tf_CON.getText().equals("") && !tf_SIZ.getText().equals("")){
//            val = ( NumberUtils.createInteger(tf_CON.getText()) + NumberUtils.createInteger(tf_SIZ.getText()) )/ 10;
//        }
//        lbl_HPLimit.setText("" + val);
//        tf_HP.setText("" + val);
//
//    }
//
//    public void updateMPLimit() {
//        TextField tf_POW = playerStatisticsSmall.getPropertyPane().tf_POW;
//        int val = 0;
//        if(!tf_POW.getText().equals("")){
//            val = NumberUtils.createInteger(tf_POW.getText()) / 5;
//
//        }
//        lbl_MPLimit.setText("" + val);
//        tf_MP.setText("" + val);
//
//    }

    public PlayerStatistics getPlayerStatistics() {
        return playerStatistics;
    }

    public void setPlayerStatistics(PlayerStatistics playerStatistics) {
        this.playerStatistics = playerStatistics;
    }

    public PaneStatus getPaneStatus() {
        return paneStatus;
    }

    public void setPaneStatus(PaneStatus paneStatus) {
        this.paneStatus = paneStatus;
    }

    public String getKp_userName() {
        return kp_userName;
    }

    public void setKp_userName(String kp_userName) {
        this.kp_userName = kp_userName;
    }
}
