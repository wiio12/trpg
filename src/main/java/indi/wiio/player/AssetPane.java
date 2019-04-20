package indi.wiio.player;


import indi.wiio.info.Others;
import indi.wiio.info.character.Asset;
import org.apache.commons.lang3.math.NumberUtils;
import indi.wiio.utils.Univ;
import indi.wiio.info.character.Resources;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import indi.wiio.player.full.PlayerStatistics;
import indi.wiio.player.small.PlayerStatisticsSmall;

import java.net.URL;
import java.util.ResourceBundle;


public class AssetPane implements Initializable {

    public TextField textField;
    public Label labelUp;
    public Label labelDown;
    public TextField tf_LifeStandard;

    public ComboBox<String> cb_Currency;

    public TextField tf_Cash;
    public TextField tf_AssetsTotal;
    public TextField tf_ConsumptionLevel;
    public TextArea ta_Description;
    public TitledPane titlePane;

    private PlayerStatistics playerStatistics;
    private PlayerStatisticsSmall playerStatisticsSmall;

    private Asset asset;
    private PaneStatus paneStatus;

    private String kp_userName;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(Resources.getResources() != null){
            asset = Resources.getResources().getAsset();
        }
        initializeAssetPane();
        Platform.runLater(this::initializeStatus);

    }

    private void initializeStatus() {
        if(paneStatus == PaneStatus.EDIT_FULL)
            handleEditStatusInit();
        else if(paneStatus == PaneStatus.SHOW_FULL)
            handleShowFullStatusInit();
        else if(paneStatus == PaneStatus.SMALL)
            handleSmallStatusInit();
        else if(paneStatus == PaneStatus.KP){
            handleKp();
        }
    }

    private void handleKp() {
       Others others = Others.getOthersMap().get(kp_userName);
       if(others != null){
           asset = others.getResources().getAsset();
           handleSmallStatusInit();
       }else{
           System.out.println("kp:asset");
       }

    }

    private void commonInit(){
        textField.setEditable(false);
        tf_LifeStandard.setEditable(false);
        tf_Cash.setEditable(false);
        tf_AssetsTotal.setEditable(false);
        tf_ConsumptionLevel.setEditable(false);

        textField.textProperty().bind(asset.creditProperty().asString().concat("%"));
        tf_LifeStandard.textProperty().bind(asset.lifeStandardProperty());
        tf_Cash.textProperty().bind(asset.cashProperty());
        tf_AssetsTotal.textProperty().bind(asset.assetTotalProperty());
        tf_ConsumptionLevel.textProperty().bind(asset.consumptionLevelProperty());


    }

    private void handleSmallStatusInit() {
        commonInit();
        ta_Description.setEditable(false);
        cb_Currency.setDisable(true);
        titlePane.setCollapsible(true);

        cb_Currency.valueProperty().bind(asset.currencyProperty());
        ta_Description.textProperty().bind(asset.descriptionProperty());
    }

    private void handleShowFullStatusInit() {
        commonInit();
        ta_Description.setEditable(false);
        cb_Currency.setDisable(true);
        titlePane.setCollapsible(false);

        cb_Currency.valueProperty().bind(asset.currencyProperty());
        ta_Description.textProperty().bind(asset.descriptionProperty());

    }

    private void handleEditStatusInit() {
        commonInit();
        ta_Description.setEditable(true);

        titlePane.setCollapsible(false);

        ta_Description.textProperty().bind(asset.descriptionProperty());
        ta_Description.textProperty().unbind();

        cb_Currency.valueProperty().bind(asset.currencyProperty());
        cb_Currency.valueProperty().unbind();

        asset.descriptionProperty().bind(ta_Description.textProperty());
        asset.currencyProperty().bind(cb_Currency.valueProperty());
    }

    private void initializeAssetPane() {
        textField.textProperty().addListener(observable -> handleCredit(textField,labelUp, labelDown));

        cb_Currency.getItems().addAll("美元","日元","人民币");
        cb_Currency.getSelectionModel().selectFirst();

    }

    public static void handleCredit(TextField tf, Label lUp, Label lDown){
        if(tf.getText().equals(""))return;
        String text = tf.getText();
        String sub = text.substring(0, text.length() - 1);
        int val = 0;
        try {
            val = NumberUtils.createInteger(sub);
        }catch (Exception e){

        }
        lUp.setText(val / 2 + "%");
        lDown.setText(val / 5 + "%");
    }

    public PlayerStatistics getPlayerStatistics() {
        return playerStatistics;
    }

    public void setPlayerStatistics(PlayerStatistics playerStatistics) {
        this.playerStatistics = playerStatistics;
    }

    public PlayerStatisticsSmall getPlayerStatisticsSmall() {
        return playerStatisticsSmall;
    }

    public void setPlayerStatisticsSmall(PlayerStatisticsSmall playerStatisticsSmall) {
        this.playerStatisticsSmall = playerStatisticsSmall;
    }

    public Asset getAsset() {
        return asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
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
