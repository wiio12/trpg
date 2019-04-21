package indi.wiio.controllers.player;

import indi.wiio.info.Others;
import indi.wiio.info.character.Experience;
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

public class ExperiencePane implements Initializable {
    public VBox experienceTitlePane;
    public VBox experienceVBox;
    public TableView<Experience> experienceTable;
    public TableColumn<Experience, String> moduleCol;
    public TableColumn<Experience, String> descriptionCol;
    public TextField moduleTextField;
    public TextField descriptionTextField;
    public TitledPane titlePane;
    public HBox editHBox;

    private PlayerStatisticsSmall playerStatisticsSmall;
    private PlayerStatistics playerStatistics;
    private ObservableList<Experience> experiences;
    private PaneStatus paneStatus;

    private String kp_userName;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(Resources.getResources() != null){
            experiences = Resources.getResources().getExperiences();
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
            experiences = others.getResources().getExperiences();
            handleSmallStatusInit();
        }else{
            System.out.println("kp:experience");
        }
    }


    @SuppressWarnings("Duplicates")
    private void handleSmallStatusInit() {
        initializeExperience();
        titlePane.setCollapsible(true);
        experienceTable.setFixedCellSize(25);
        experienceTable.prefHeightProperty().bind(Bindings.size(experienceTable.getItems()).multiply(experienceTable.getFixedCellSize()).add(28));
        experienceTitlePane.prefHeightProperty().bind(experienceTable.prefHeightProperty());

        experienceVBox.getChildren().remove(editHBox);

        experienceTable.setEditable(false);
    }

    private void handleShowFullStatusInit() {
        initializeExperience();
        titlePane.setCollapsible(false);
        experienceTable.setEditable(false);

        experienceVBox.getChildren().remove(editHBox);
    }

    private void handleEditStatusInit() {
        initializeExperience();
        titlePane.setCollapsible(false);
        experienceTable.setEditable(true);
    }

    @SuppressWarnings("Duplicates")
    private void initializeExperience() {

        moduleCol.setCellValueFactory(new PropertyValueFactory<>("module" ));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description" ));

        moduleCol.setCellFactory(TextFieldTableCell.forTableColumn());
        descriptionCol.setCellFactory(TextFieldTableCell.forTableColumn());

        experienceTable.setItems(experiences);



    }


    public void handleAdd(){
        if(moduleTextField.getText().equals("") || descriptionTextField.getText().equals("") ) return;
        String mod = moduleTextField.getText();
        String des = descriptionTextField.getText();
        moduleTextField.clear();
        descriptionTextField.clear();

        experienceTable.getItems().add(new Experience(mod, des));
    }


    public void handleDelete(){
        ObservableList<Experience> itemSelected, allExperiences;
        allExperiences = experienceTable.getItems();
        itemSelected = experienceTable.getSelectionModel().getSelectedItems();

        for (int i = 0; i < itemSelected.size(); i++) {
            allExperiences.remove(itemSelected.get(i));
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
