package indi.wiio.player;


import indi.wiio.info.Others;
import indi.wiio.utils.Univ;
import indi.wiio.info.character.Fight;
import indi.wiio.info.character.Resources;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.util.converter.NumberStringConverter;
import indi.wiio.player.full.PlayerStatistics;
import indi.wiio.player.small.PlayerStatisticsSmall;

import java.net.URL;
import java.util.ResourceBundle;

public class FightPane implements Initializable {

    public TextField tf_Damage;
    public TextField tf_Build;
    public TextField tf_Dodge;
    public Label lbl_Dodge_Up;
    public Label lbl_Dodge_Down;
    public TextField tf_Armor;
    public TitledPane titlePane;

    public PlayerStatisticsSmall getPlayerStatisticsSmall() {
        return playerStatisticsSmall;
    }

    public void setPlayerStatisticsSmall(PlayerStatisticsSmall playerStatisticsSmall) {
        this.playerStatisticsSmall = playerStatisticsSmall;
    }

    private PlayerStatisticsSmall playerStatisticsSmall;
    private PlayerStatistics playerStatistics;
    private Fight fight ;
    private PaneStatus paneStatus;

    private String kp_userName;

    public PlayerStatistics getPlayerStatistics() {
        return playerStatistics;
    }

    public void setPlayerStatistics(PlayerStatistics playerStatistics) {
        this.playerStatistics = playerStatistics;
    }

    public Fight getFight() {
        return fight;
    }

    public void setFight(Fight fight) {
        this.fight = fight;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(Resources.getResources() != null){
            fight = Resources.getResources().getFight();
        }
        initializeFight();
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
            fight = others.getResources().getFight();
            handleSmallStatusInit();
        }else{
            System.out.println("kp:fight");
        }
    }

    private void commonInit(){
        tf_Dodge.setEditable(false);
        tf_Damage.setEditable(false);
        tf_Build.setEditable(false);


        tf_Dodge.textProperty().bind(fight.dodgeProperty().asString());
        tf_Damage.textProperty().bind(fight.damageProperty());
        tf_Build.textProperty().bind(fight.buildProperty());
    }

    private void handleSmallStatusInit() {
        titlePane.setCollapsible(true);
        tf_Armor.setEditable(false);
        commonInit();

        tf_Armor.textProperty().bind(fight.armorProperty().asString());

    }

    private void handleShowFullStatusInit() {
        titlePane.setCollapsible(false);
        tf_Armor.setEditable(false);
        commonInit();
        tf_Armor.textProperty().bind(fight.armorProperty().asString());
    }

    private void handleEditStatusInit() {
        titlePane.setCollapsible(false);
        tf_Armor.setEditable(true);
        commonInit();

        tf_Armor.textProperty().bind(fight.armorProperty().asString());
        tf_Armor.textProperty().unbind();

        Univ.bindTextAndInt(tf_Armor,fight.armorProperty());

//        IntegerProperty tmp = new SimpleIntegerProperty();
//        Bindings.bindBidirectional(tf_Armor.textProperty(),tmp, new NumberStringConverter());
//        fight.armorProperty().bind(tmp);
    }

    private void initializeFight() {
        tf_Armor.textProperty().addListener(((observableValue, s, t1) -> Univ.textFieldNumberOnlyLimiter(tf_Armor, s, t1)));

        tf_Dodge.textProperty().addListener(((observableValue, s, t1) -> {
            Univ.handleTriProperty(tf_Dodge, lbl_Dodge_Up, lbl_Dodge_Down, s, t1);
        }));


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
