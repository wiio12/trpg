package indi.wiio.controllers.player;

import indi.wiio.info.Others;
import indi.wiio.info.character.Companion;
import indi.wiio.info.character.Resources;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import indi.wiio.controllers.player.full.PlayerStatistics;
import indi.wiio.controllers.player.small.PlayerStatisticsSmall;

import java.net.URL;
import java.util.ResourceBundle;

public class CompanionPane implements Initializable {
    public VBox companionTitlePane;
    public VBox companionVBox;
    public TableView<Companion> companionTable;
    public TableColumn<Companion, String> nameCol;
    public TableColumn<Companion, String> descriptionCol;
    public TextField nameTextField;
    public TextField descriptionTextField;
    public TitledPane titlePane;
    public HBox editHBox;

    private PlayerStatisticsSmall playerStatisticsSmall;
    private PlayerStatistics playerStatistics;

    private ObservableList<Companion> companions;
    private PaneStatus paneStatus;

    private String kp_userName;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(Resources.getResources() != null){
            companions = Resources.getResources().getCompanions();
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
            companions = others.getResources().getCompanions();
            handleSmallStatusInit();
        }else{
            System.out.println("kp:companions");
        }
    }

    @SuppressWarnings("Duplicates")
    private void handleSmallStatusInit() {
        initializeCompanion();
        titlePane.setCollapsible(true);
        companionTable.setFixedCellSize(50);
        companionTable.prefHeightProperty().bind(Bindings.size(companionTable.getItems()).multiply(companionTable.getFixedCellSize()).add(28));
        companionTitlePane.prefHeightProperty().bind(companionTable.prefHeightProperty());
        companionTable.setEditable(false);

        companionVBox.getChildren().remove(editHBox);


    }

    private void handleShowFullStatusInit() {
        initializeCompanion();
        titlePane.setCollapsible(false);
        companionTable.setEditable(false);
        companionVBox.getChildren().remove(editHBox);

    }

    private void handleEditStatusInit() {
        initializeCompanion();
        titlePane.setCollapsible(false);
        companionTable.setEditable(true);

    }

    private void initializeCompanion() {

        nameCol.setCellValueFactory(new PropertyValueFactory<>("name" ));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description" ));

        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        descriptionCol.setCellFactory(TextFieldTableCell.forTableColumn());

        companionTable.setItems(companions);

    }

    @SuppressWarnings("Duplicates")
    public void handleAdd(){
        if(nameTextField.getText().equals("") || descriptionTextField.getText().equals("") ) return;
        String mod = nameTextField.getText();
        String des = descriptionTextField.getText();
        if(des.length() > 19){
            des = des.substring(0, 19) + "\n" + des.substring(19);
        }
        nameTextField.clear();
        descriptionTextField.clear();

        Companion c = new Companion(mod, des);
        companions.add(c);
    }


    @SuppressWarnings("Duplicates")
    public void handleDelete(){
        ObservableList<Companion> itemSelected;
        itemSelected = companionTable.getSelectionModel().getSelectedItems();

        for (int i = 0; i < itemSelected.size(); i++) {
            companions.remove(itemSelected.get(i));
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
