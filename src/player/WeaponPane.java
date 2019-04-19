package player;

import info.Others;
import info.character.Resources;
import info.character.Weapon;
import info.character.WeaponType;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import player.full.PlayerStatistics;
import player.small.PlayerStatisticsSmall;

import javax.print.attribute.standard.PrinterName;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class WeaponPane implements Initializable {

    //武器列表部分
    public TableView<Weapon> weaponTable;
    public TableColumn<Weapon, String> weaponNameCol;
    public TableColumn<Weapon, String> weaponTypeCol;
    public TableColumn<Weapon, String> weaponSkillCol;
    public TableColumn<Weapon, Integer> sR1Col;
    public TableColumn<Weapon, Integer> sR2Col;
    public TableColumn<Weapon, Integer> sR3Col;
    public TableColumn<Weapon, String> damageCol;
    public TableColumn<Weapon, String> basicRangeCol;
    public TableColumn<Weapon, String> penetrateCol;
    public TableColumn<Weapon, String> timesCol;
    public TableColumn<Weapon, String> loadAmountCol;
    public TableColumn<Weapon, String> functionalityCol;
    public VBox weaponVBox;
    public VBox weaponTitlePane;
    public ComboBox<String> weaponTypeComboBox;
    public TextField weaponNameTextField;
    public TitledPane titlePane;
    public HBox editHBox;

    private PlayerStatisticsSmall playerStatisticsSmall;
    private PlayerStatistics playerStatistics;
    private PaneStatus paneStatus;
    private ObservableList<Weapon> weapons;

    private String kp_userName;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(Resources.getResources() != null){
            weapons = Resources.getResources().getWeapons();
        }

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
            weapons = others.getResources().getWeapons();
            handleSmallStatusInit();
        }else{
            System.out.println("kp:weapons");
        }

    }

    private void handleSmallStatusInit() {
        initializeWeapon();
        titlePane.setCollapsible(true);
        weaponTitlePane.setPrefWidth(350.0);
        weaponTable.setFixedCellSize(25);
        weaponTable.prefHeightProperty().bind(Bindings.size(weaponTable.getItems()).multiply(weaponTable.getFixedCellSize()).add(28));
        weaponTitlePane.prefHeightProperty().bind(weaponTable.prefHeightProperty().add(27));

    }

    private void handleShowFullStatusInit() {
        initializeWeapon();
        titlePane.setCollapsible(false);
        weaponTitlePane.setPrefWidth(900.0);
        weaponTitlePane.setPrefHeight(300.0);


    }

    private void handleEditStatusInit() {
        initializeWeapon();
        titlePane.setCollapsible(false);
        weaponTitlePane.setPrefWidth(900.0);
        weaponTitlePane.setPrefHeight(300.0);


    }

    private void initializeWeapon() {

        weaponNameCol.setCellValueFactory(new PropertyValueFactory<>("weaponName" ));
        weaponTypeCol.setCellValueFactory(new PropertyValueFactory<>("weaponType"));
        weaponSkillCol.setCellValueFactory(new PropertyValueFactory<>("weaponSkill"));
        sR1Col.setCellValueFactory(new PropertyValueFactory<>("successRate1"));
        sR2Col.setCellValueFactory(new PropertyValueFactory<>("successRate2"));
        sR3Col.setCellValueFactory(new PropertyValueFactory<>("successRate3"));
        damageCol.setCellValueFactory(new PropertyValueFactory<>("damage"));
        basicRangeCol.setCellValueFactory(new PropertyValueFactory<>("basicRange"));
        penetrateCol.setCellValueFactory(new PropertyValueFactory<>("penetrate"));
        timesCol.setCellValueFactory(new PropertyValueFactory<>("times"));
        loadAmountCol.setCellValueFactory(new PropertyValueFactory<>("loadAmount"));
        functionalityCol.setCellValueFactory(new PropertyValueFactory<>("functionality"));

        weaponTable.setItems(weapons);
        weaponNameCol.setCellFactory(TextFieldTableCell.forTableColumn());

        ArrayList<WeaponType> weaponTypes = new ArrayList<>(Arrays.asList(WeaponType.values()));
        for(WeaponType wt : weaponTypes){
            weaponTypeComboBox.getItems().add(wt.getWeapon().getWeaponType());
        }






    }

    public void handleEquip(){
        String selected = weaponTypeComboBox.getSelectionModel().getSelectedItem();
        Weapon weapon = getWeapon(selected);
        String name = "-";
        if(weaponNameTextField.getText() != null)
            name = weaponNameTextField.getText();
        weapon.setWeaponName(name);
        weaponNameTextField.clear();
        weaponTypeComboBox.getSelectionModel().clearSelection();

        weaponTable.getItems().add(weapon);

    }


    public void handleDrop(){
        ObservableList<Weapon> weaponSelected, allWeapons;
        allWeapons = weaponTable.getItems();
        weaponSelected = weaponTable.getSelectionModel().getSelectedItems();

        weaponSelected.forEach(allWeapons::remove);
    }

    private Weapon getWeapon(String weaponType) {
        ArrayList<WeaponType> weaponTypes = new ArrayList<>(Arrays.asList(WeaponType.values()));
        for(WeaponType w: weaponTypes){
            if(weaponType.equals(w.getWeapon().getWeaponType())){
                return w.getWeapon();
            }
        }
        return null;
    }

    private ObservableList<Weapon> getWeapons() {
        //TODO:数据接口
        ObservableList<Weapon> weapons = FXCollections.observableArrayList();
        weapons.add(new Weapon("徒手格斗","肉搏","斗殴",25,"1D3+DB",
                "-", "X","1","-","-"));
        ArrayList<WeaponType> weaponTypes = new ArrayList<>(Arrays.asList(WeaponType.values()));
        for( WeaponType w: weaponTypes){
            weapons.add(w.getWeapon());
        }


        return  weapons;
    }


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

    public void setWeapons(ObservableList<Weapon> weapons) {
        this.weapons = weapons;
    }

    public String getKp_userName() {
        return kp_userName;
    }

    public void setKp_userName(String kp_userName) {
        this.kp_userName = kp_userName;
    }
}
