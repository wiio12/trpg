package player;

import info.Others;
import info.Self;
import info.character.Cthulhu;
import info.character.Resources;
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
import player.full.PlayerStatistics;
import player.small.PlayerStatisticsSmall;

import java.net.URL;
import java.util.ResourceBundle;

public class CthulhuPane implements Initializable {
    public VBox cthulhuTitlePane;
    public VBox cthulhuVBox;
    public TableView<Cthulhu> cthulhuTable;
    public TableColumn<Cthulhu, String> itemCol;
    public TableColumn<Cthulhu, String> godCol;
    public TextField itemTextField;
    public TextField godTextField;
    public TitledPane titlePane;
    public HBox editHBox;

    private PlayerStatisticsSmall playerStatisticsSmall;
    private PlayerStatistics playerStatistics;
    private PaneStatus paneStatus;
    private ObservableList<Cthulhu> cthulhus;
    private String kp_userName;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(Resources.getResources() != null){
            cthulhus = Resources.getResources().getCthulhus();
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
            cthulhus = others.getResources().getCthulhus();
            handleSmallStatusInit();
        }else{
            System.out.println("kp:cthulhus");
        }
    }

    @SuppressWarnings("Duplicates")
    private void handleSmallStatusInit() {
        initializeCthulhu();
        titlePane.setCollapsible(true);
        cthulhuTable.setFixedCellSize(25);
        cthulhuTable.prefHeightProperty().bind(Bindings.size(cthulhuTable.getItems()).multiply(cthulhuTable.getFixedCellSize()).add(28));
        cthulhuTitlePane.prefHeightProperty().bind(cthulhuTable.prefHeightProperty());

        cthulhuVBox.getChildren().remove(editHBox);

        cthulhuTable.setEditable(false);

    }

    private void handleShowFullStatusInit() {
        initializeCthulhu();
        titlePane.setCollapsible(false);
        cthulhuTable.setEditable(false);

        cthulhuVBox.getChildren().remove(editHBox);
    }

    private void handleEditStatusInit() {
        initializeCthulhu();
        titlePane.setCollapsible(false);
        cthulhuTable.setEditable(true);

    }


    private void initializeCthulhu() {

        itemCol.setCellValueFactory(new PropertyValueFactory<>("item" ));
        godCol.setCellValueFactory(new PropertyValueFactory<>("god" ));

        itemCol.setCellFactory(TextFieldTableCell.forTableColumn());
        godCol.setCellFactory(TextFieldTableCell.forTableColumn());

        cthulhuTable.setItems(cthulhus);

    }


    public void handleAdd(){
        if(itemTextField.getText().equals("") || godTextField.getText().equals("") ) return;
        String mod = itemTextField.getText();
        String des = godTextField.getText();
        itemTextField.clear();
        godTextField.clear();

        cthulhuTable.getItems().add(new Cthulhu(mod, des));
    }


    public void handleDelete(){
        ObservableList<Cthulhu> itemSelected, allCthulhus;
        allCthulhus = cthulhuTable.getItems();
        itemSelected = cthulhuTable.getSelectionModel().getSelectedItems();

        for (int i = 0; i < itemSelected.size(); i++) {
            allCthulhus.remove(itemSelected.get(i));
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
