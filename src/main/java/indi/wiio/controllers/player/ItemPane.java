package indi.wiio.controllers.player;

import indi.wiio.info.Others;
import indi.wiio.info.character.ItemEquiped;
import indi.wiio.info.character.Resources;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import indi.wiio.controllers.player.full.PlayerStatistics;
import indi.wiio.controllers.player.small.PlayerStatisticsSmall;

import java.net.URL;
import java.util.ResourceBundle;

public class ItemPane implements Initializable {

    public TableView<ItemEquiped> itemTable;
    public TableColumn<ItemEquiped, String> itemCol;

    public VBox itemVBox;
    public VBox itemTitlePane;
    public TextField itemTextField;
    public TitledPane titlePane;
    public HBox editHBox;

    private PlayerStatisticsSmall playerStatisticsSmall;
    private PlayerStatistics playerStatistics;
    private ObservableList<ItemEquiped> items;
    private PaneStatus paneStatus;

    private String kp_userName;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(Resources.getResources() != null){
            items = Resources.getResources().getItemEquipeds();
        }
        initializeItemEquiped();
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
            items = others.getResources().getItemEquipeds();
            handleSmallStatusInit();
        }else{
            System.out.println("kp:items");
        }
    }

    private void handleSmallStatusInit() {
        initializeItemEquiped();
        titlePane.setCollapsible(true);
        itemTable.setFixedCellSize(25);
        itemTable.prefHeightProperty().bind(Bindings.size(itemTable.getItems()).multiply(itemTable.getFixedCellSize()).add(28));
        itemTitlePane.prefHeightProperty().bind(itemTable.prefHeightProperty());

       itemVBox.getChildren().remove(editHBox);

        itemTable.setEditable(false);
    }

    private void handleShowFullStatusInit() {
        initializeItemEquiped();
        titlePane.setCollapsible(false);
        itemTable.setEditable(false);
        itemTable.setPrefHeight(330);

        itemVBox.getChildren().remove(editHBox);
    }

    private void handleEditStatusInit() {
        initializeItemEquiped();
        titlePane.setCollapsible(false);
        itemTable.setEditable(true);
        itemTable.setPrefHeight(330);
    }

    private void initializeItemEquiped() {

        itemCol.setCellValueFactory(new PropertyValueFactory<>("itemDescription" ));

        itemCol.setCellFactory(TextFieldTableCell.forTableColumn());

        itemTable.setItems(items);
    }

    public void handleAdd(){
        if(itemTextField.getText().equals("")) return;
        String text = itemTextField.getText();
        itemTextField.clear();

        itemTable.getItems().add(new ItemEquiped(text));
    }


    public void handleDelete(){
        ObservableList<ItemEquiped> itemSelected, allItemEquipeds;
        allItemEquipeds = itemTable.getItems();
        itemSelected = itemTable.getSelectionModel().getSelectedItems();

        for (int i = 0; i < itemSelected.size(); i++) {
            allItemEquipeds.remove(itemSelected.get(i));
        }
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

    public ObservableList<ItemEquiped> getItems() {
        return items;
    }

    public void setItems(ObservableList<ItemEquiped> items) {
        this.items = items;
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
