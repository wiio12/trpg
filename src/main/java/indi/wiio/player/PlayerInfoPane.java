package indi.wiio.player;

import indi.wiio.info.Others;
import indi.wiio.info.character.PlayerInfo;
import indi.wiio.info.character.Resources;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Pane;
import javafx.util.converter.NumberStringConverter;
import indi.wiio.player.full.PlayerStatistics;
import indi.wiio.player.small.PlayerStatisticsSmall;
import indi.wiio.utils.Univ;

import java.net.URL;
import java.util.ResourceBundle;


public class PlayerInfoPane implements Initializable {
    public TextField name;
    public TextField playerName;
    public ComboBox<String> years;
    public TextField profession;
    public TextField age;
    public TextField address;
    public TextField hometown;
    public TitledPane titlePane;
    public ComboBox<String> gender;


    private PlayerStatisticsSmall playerStatisticsSmall;
    private PlayerStatistics playerStatistics;
    private PlayerInfo playerInfo;
    private PaneStatus paneStatus;

    private String kp_userName;

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

    public PlayerInfo getPlayerInfo() {
        return playerInfo;
    }

    public void setPlayerInfo(PlayerInfo playerInfo) {
        this.playerInfo = playerInfo;
    }

    private String cardResource;

    public void setResource(String resource){
        playerInfo = Resources.getResources(resource).getPlayerInfo();
    }

    public void setResource(){
        playerInfo = Resources.getResources().getPlayerInfo();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Platform.runLater(()->{
            initializePlayerInfo();
            initializeStatus();
        });
    }

    private void initializePlayerInfo() {
        years.getItems().addAll("1920s","1980s","现代","其他");
        years.getSelectionModel().selectFirst();
        gender.getItems().addAll("男","女","扶她");
        gender.getSelectionModel().selectFirst();
    }


    private void initializeStatus() {
        if(paneStatus == PaneStatus.EDIT_FULL)
            handleEditStatusInit();
        else if(paneStatus == PaneStatus.SHOW_FULL)
            handleShowFullStatusInit();
        else if(paneStatus == PaneStatus.SMALL)
            handleSmallStatusInit();
        else if(paneStatus == PaneStatus.SHOW_CARD)
            handleShowCardInit();
        else if(paneStatus == PaneStatus.KP)
            handleKp();
    }

    private void handleKp() {
        Others others = Others.getOthersMap().get(kp_userName);
        if(others != null){
            playerInfo = others.getResources().getPlayerInfo();

            titlePane.setCollapsible(true);
            setEdit(false);
            name.textProperty().bind(playerInfo.nameProperty());
            playerName.textProperty().bind(playerInfo.playerNameProperty());
            years.valueProperty().bind(playerInfo.yearsProperty());
            profession.textProperty().bind(playerInfo.professionProperty());
            age.textProperty().bind(playerInfo.ageProperty().asString());
            address.textProperty().bind(playerInfo.addressProperty());
            hometown.textProperty().bind(playerInfo.hometownProperty());
            gender.valueProperty().bind(playerInfo.genderProperty());

        }else{
            System.out.println("kp:playerInfo");
        }
    }

    private void handleShowCardInit() {
        setResource(cardResource);

        Pane title = (Pane) titlePane.lookup(".title");
        if (title != null) {
            title.setVisible(false);
        }

        titlePane.setCollapsible(false);
        setEdit(false);
        name.textProperty().bind(playerInfo.nameProperty());
        playerName.textProperty().bind(playerInfo.playerNameProperty());
        years.valueProperty().bind(playerInfo.yearsProperty());
        profession.textProperty().bind(playerInfo.professionProperty());
        age.textProperty().bind(playerInfo.ageProperty().asString());
        address.textProperty().bind(playerInfo.addressProperty());
        hometown.textProperty().bind(playerInfo.hometownProperty());
        gender.valueProperty().bind(playerInfo.genderProperty());

    }

    private void setEdit(boolean editable){
        name.setEditable(editable);
        playerName.setEditable(editable);
        years.setDisable(!editable);
        profession.setEditable(editable);
        age.setEditable(editable);
        address.setEditable(editable);
        hometown.setEditable(editable);
        gender.setDisable(!editable);

    }

    private void handleSmallStatusInit() {
        setResource();
        titlePane.setCollapsible(true);
        setEdit(false);
        name.textProperty().bind(playerInfo.nameProperty());
        playerName.textProperty().bind(playerInfo.playerNameProperty());
        years.valueProperty().bind(playerInfo.yearsProperty());
        profession.textProperty().bind(playerInfo.professionProperty());
        age.textProperty().bind(playerInfo.ageProperty().asString());
        address.textProperty().bind(playerInfo.addressProperty());
        hometown.textProperty().bind(playerInfo.hometownProperty());
        gender.valueProperty().bind(playerInfo.genderProperty());
    }

    private void handleShowFullStatusInit() {
        setResource();
        titlePane.setCollapsible(false);
        setEdit(false);
        name.textProperty().bind(playerInfo.nameProperty());
        playerName.textProperty().bind(playerInfo.playerNameProperty());
        years.valueProperty().bind(playerInfo.yearsProperty());
        profession.textProperty().bind(playerInfo.professionProperty());
        age.textProperty().bind(playerInfo.ageProperty().asString());
        address.textProperty().bind(playerInfo.addressProperty());
        hometown.textProperty().bind(playerInfo.hometownProperty());
        gender.valueProperty().bind(playerInfo.genderProperty());

    }

    private void handleEditStatusInit() {
        setResource();
        setEdit(true);
        titlePane.setCollapsible(false);

        playerName.setEditable(false);

        name.textProperty().bind(playerInfo.nameProperty());
        playerName.textProperty().bind(playerInfo.playerNameProperty());
        years.valueProperty().bind(playerInfo.yearsProperty());
        profession.textProperty().bind(playerInfo.professionProperty());
        age.textProperty().bind(playerInfo.ageProperty().asString());
        address.textProperty().bind(playerInfo.addressProperty());
        hometown.textProperty().bind(playerInfo.hometownProperty());
        gender.valueProperty().bind(playerInfo.genderProperty());

        name.textProperty().unbind();
        playerName.textProperty().unbind();
        years.valueProperty().unbind();
        profession.textProperty().unbind();
        age.textProperty().unbind();
        address.textProperty().unbind();
        hometown.textProperty().unbind();
        gender.valueProperty().unbind();

        playerInfo.nameProperty().bind(name.textProperty());
        playerInfo.playerNameProperty().bind(playerName.textProperty());
        playerInfo.yearsProperty().bind(years.valueProperty());
        playerInfo.professionProperty().bind(profession.textProperty());

        Univ.bindTextAndInt(age,playerInfo.ageProperty());

        playerInfo.addressProperty().bind(address.textProperty());
        playerInfo.hometownProperty().bind(hometown.textProperty());
        playerInfo.genderProperty().bind(gender.valueProperty());

    }


    public PaneStatus getPaneStatus() {
        return paneStatus;
    }

    public void setPaneStatus(PaneStatus paneStatus) {
        this.paneStatus = paneStatus;
    }

    public String getCardResource() {
        return cardResource;
    }

    public void setCardResource(String cardResource) {
        this.cardResource = cardResource;
    }

    public String getKp_userName() {
        return kp_userName;
    }

    public void setKp_userName(String kp_userName) {
        this.kp_userName = kp_userName;
    }
}
