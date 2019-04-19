package player;

import info.Others;
import utils.Univ;
import info.character.Property;
import info.character.Resources;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import org.apache.commons.lang3.math.NumberUtils;
import player.full.PlayerStatistics;
import player.small.PlayerStatisticsSmall;

import java.net.URL;
import java.util.ResourceBundle;

import static utils.Univ.bindTextAndInt;

public class PropertyPane implements Initializable {

    public TextField tf_STR;
    public Label labUp_STR;
    public Label labDown_STR;


    public TextField tf_DEX;
    public Label labUp_DEX;
    public Label labDown_DEX;

    public TextField tf_POW;
    public Label labUp_POW;
    public Label labDown_POW;

    public TextField tf_CON;
    public Label labUp_CON;
    public Label labDown_CON;

    public TextField tf_APP;
    public Label labUp_APP;
    public Label labDown_APP;

    public TextField tf_EDU;
    public Label labUp_EDU;
    public Label labDown_EDU;

    public TextField tf_SIZ;
    public Label labUp_SIZ;
    public Label labDown_SIZ;

    public TextField tf_INT_IDEA;
    public Label labUp_INT_IDEA;
    public Label labDown_INT_IDEA;

    //TODO:完成这个部分的处理
    public TextField tf_MOV;
    public TextField tf_ADJ;

    public Label lab_SUMWithLuck;
    public Label lab_SUMWithoutLuck;
    public TitledPane titlePane;


    private PlayerStatisticsSmall playerStatisticsSmall;
    private PlayerStatistics playerStatistics;
    private PaneStatus paneStatus;
    private Property property;

    private String kp_userName;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(Resources.getResources() != null){
            property= Resources.getResources().getProperty();
        }

        initializeProperty();
        Platform.runLater(this::initializeStatus);
    }

    private void initializeStatus() {
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
            property = others.getResources().getProperty();
            handleSmallStatusInit();
        }else{
            System.out.println("kp:property");
        }
    }

    private void setEdit(boolean editable){
        tf_STR.setEditable(editable);
        tf_DEX.setEditable(editable);
        tf_POW.setEditable(editable);
        tf_CON.setEditable(editable);
        tf_APP.setEditable(editable);
        tf_EDU.setEditable(editable);
        tf_SIZ.setEditable(editable);
        tf_INT_IDEA.setEditable(editable);

        tf_MOV.setEditable(false);
        tf_ADJ.setEditable(false);

        tf_MOV.textProperty().bind(property.MOVProperty().asString());
        tf_ADJ.textProperty().bind(property.ADJProperty().asString());

        lab_SUMWithLuck.textProperty().bind(property.SUMWithLuckProperty().asString());
        lab_SUMWithoutLuck.textProperty().bind(property.SUMWithoutLuckProperty().asString());
    }

    private void handleSmallStatusInit() {
        titlePane.setCollapsible(true);
        setEdit(false);
        tf_STR.textProperty().bind(property.STRProperty().asString());
        tf_DEX.textProperty().bind(property.DEXProperty().asString());
        tf_POW.textProperty().bind(property.POWProperty().asString());
        tf_CON.textProperty().bind(property.CONProperty().asString());
        tf_EDU.textProperty().bind(property.EDUProperty().asString());
        tf_SIZ.textProperty().bind(property.SIZProperty().asString());
        tf_APP.textProperty().bind(property.APPProperty().asString());
        tf_INT_IDEA.textProperty().bind(property.INT_IDEAProperty().asString());


    }

    private void handleShowFullStatusInit() {
        titlePane.setCollapsible(false);
        setEdit(false);
        tf_STR.textProperty().bind(property.STRProperty().asString());
        tf_DEX.textProperty().bind(property.DEXProperty().asString());
        tf_POW.textProperty().bind(property.POWProperty().asString());
        tf_CON.textProperty().bind(property.CONProperty().asString());
        tf_EDU.textProperty().bind(property.EDUProperty().asString());
        tf_SIZ.textProperty().bind(property.SIZProperty().asString());
        tf_APP.textProperty().bind(property.APPProperty().asString());
        tf_INT_IDEA.textProperty().bind(property.INT_IDEAProperty().asString());
    }

    private void handleEditStatusInit() {
        titlePane.setCollapsible(false);
        setEdit(true);

        tf_STR.textProperty().bind(property.STRProperty().asString());
        tf_DEX.textProperty().bind(property.DEXProperty().asString());
        tf_POW.textProperty().bind(property.POWProperty().asString());
        tf_CON.textProperty().bind(property.CONProperty().asString());
        tf_EDU.textProperty().bind(property.EDUProperty().asString());
        tf_SIZ.textProperty().bind(property.SIZProperty().asString());
        tf_APP.textProperty().bind(property.APPProperty().asString());
        tf_INT_IDEA.textProperty().bind(property.INT_IDEAProperty().asString());

        tf_STR.textProperty().unbind();
        tf_DEX.textProperty().unbind();
        tf_POW.textProperty().unbind();
        tf_CON.textProperty().unbind();
        tf_EDU.textProperty().unbind();
        tf_SIZ.textProperty().unbind();
        tf_APP.textProperty().unbind();
        tf_INT_IDEA.textProperty().unbind();

        Univ.bindTextAndInt(tf_STR, property.STRProperty());
        Univ.bindTextAndInt(tf_DEX, property.DEXProperty());
        Univ.bindTextAndInt(tf_POW, property.POWProperty());
        Univ.bindTextAndInt(tf_CON, property.CONProperty());
        Univ.bindTextAndInt(tf_EDU, property.EDUProperty());
        Univ.bindTextAndInt(tf_SIZ, property.SIZProperty());
        Univ.bindTextAndInt(tf_APP, property.APPProperty());
        Univ.bindTextAndInt(tf_INT_IDEA, property.INT_IDEAProperty());

    }


    @SuppressWarnings("Duplicates")
    private void initializeProperty() {

        tf_STR.textProperty().addListener((observable, oldValue, newValue) -> {
            handleProperty(tf_STR, labUp_STR,labDown_STR,oldValue,newValue);
        });

        tf_DEX.textProperty().addListener((observable, oldValue, newValue) -> {
            handleProperty(tf_DEX, labUp_DEX, labDown_DEX, oldValue, newValue);
        });

        tf_POW.textProperty().addListener((observable, oldValue, newValue) -> {
            handleProperty(tf_POW, labUp_POW, labDown_POW, oldValue, newValue);
            //playerStatisticsSmall.getStatusPane().updateMPLimit();
            //playerStatisticsSmall.getStatusPane().tf_San.setText(tf_POW.getText());

        });

        tf_CON.textProperty().addListener((observable, oldValue, newValue) -> {
            handleProperty(tf_CON, labUp_CON, labDown_CON, oldValue, newValue);
            //playerStatisticsSmall.getStatusPane().updateHpLimit();
            //updateSUM();

        });

        tf_APP.textProperty().addListener((observable, oldValue, newValue) -> {
            handleProperty(tf_APP, labUp_APP, labDown_APP, oldValue, newValue);
            //updateSUM();
        });

        tf_EDU.textProperty().addListener((observable, oldValue, newValue) -> {
            handleProperty(tf_DEX, labUp_EDU, labDown_EDU, oldValue, newValue);
            //updateSUM();
        });

        tf_SIZ.textProperty().addListener((observable, oldValue, newValue) -> {
            handleProperty(tf_SIZ, labUp_SIZ, labDown_SIZ, oldValue, newValue);
            //updateSUM();
            //playerStatisticsSmall.getStatusPane().updateHpLimit();
        });

        tf_INT_IDEA.textProperty().addListener((observable, oldValue, newValue) -> {
            handleProperty(tf_INT_IDEA, labUp_INT_IDEA, labDown_INT_IDEA, oldValue, newValue);
            //updateSUM();
        });

        tf_MOV.textProperty().addListener((observable, oldValue, newValue) -> {
            Univ.textFieldNumberOnlyLimiter(tf_MOV, oldValue, newValue);
        });
    }

    private void handleProperty(TextField tf, Label lUp, Label lDown, String oldValue, String newValue){
        int val = 0;
        if(!("".equals(newValue))){
            if(NumberUtils.isCreatable(newValue)) val = NumberUtils.createInteger(newValue);
            else {
                tf.setText(oldValue);
                if(!oldValue.equals(""))
                    val = NumberUtils.createInteger(tf.getText());
            }
        }
        lUp.setText("" + val / 2);
        lDown.setText("" + val / 5);
    }


//    public void updateSUM(){
//        int sumWithoutLuck = property.getSTR() + property.getDEX() + property.getPOW() +
//                property.getCON() + property.getAPP() + property.getEDU() + property.getSIZ() + property.getINT_IDEA();
//        int sumWithLuck = sumWithoutLuck + Resources.getResources().getPlayerStatus().getLuck();
//        lab_SUMWithLuck.setText("" + sumWithLuck);
//        lab_SUMWithoutLuck.setText("" + sumWithoutLuck);
//    }


    public PlayerStatisticsSmall getPlayerStatisticsSmall() {
        return playerStatisticsSmall;
    }

    public void setPlayerStatisticsSmall(PlayerStatisticsSmall playerStatisticsSmall) {
        this.playerStatisticsSmall = playerStatisticsSmall;
    }


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
