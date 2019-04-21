package indi.wiio.controllers.player.full;

import indi.wiio.info.character.Resources;
import indi.wiio.info.character.Skill;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;
import indi.wiio.controllers.player.PaneStatus;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SkillTablePane implements Initializable {

    public TitledPane titlePane;
    private ArrayList<Skill> skills = Resources.getResources().getSkills();

    public TableView<Skill> skillTableView;

    public TableColumn<Skill, String> markCol;
    public TableColumn<Skill, String> skillNameCol;
    public TableColumn<Skill, String> professionCol;
    public TableColumn<Skill, Integer> basicCol;
    public TableColumn<Skill, Integer> growCol;
    public TableColumn<Skill, Integer> professionPointCol;
    public TableColumn<Skill, Integer> interestPointCol;
    public TableColumn<Skill, Integer> sR1Col;
    public TableColumn<Skill, Integer> sR2Col;
    public TableColumn<Skill, Integer> sR3Col;

    private PlayerStatistics playerStatistics;
    private PaneStatus paneStatus;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeSkill();
        Platform.runLater(this::initializeStatus);
    }

    private void initializeStatus() {
        if(paneStatus == PaneStatus.EDIT_FULL)
            handleEditStatusInit();
        else if(paneStatus == PaneStatus.SHOW_FULL)
            handleShowFullStatusInit();
    }

    private void handleShowFullStatusInit() {
        skillTableView.setEditable(false);
        titlePane.setCollapsible(false);
    }

    private void handleEditStatusInit() {
        skillTableView.setEditable(true);
        titlePane.setCollapsible(false);
    }

    private void initializeSkill() {
        markCol.setCellValueFactory(new PropertyValueFactory<>("mark"));
        skillNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        professionCol.setCellValueFactory(new PropertyValueFactory<>("profession"));
        basicCol.setCellValueFactory(new PropertyValueFactory<>("basic"));
        growCol.setCellValueFactory(new PropertyValueFactory<>("grow"));
        professionPointCol.setCellValueFactory(new PropertyValueFactory<>("professionPoint"));
        interestPointCol.setCellValueFactory(new PropertyValueFactory<>("interestPoint"));
        sR1Col.setCellValueFactory(new PropertyValueFactory<>("total"));
        sR2Col.setCellValueFactory(new PropertyValueFactory<>("total2"));
        sR3Col.setCellValueFactory(new PropertyValueFactory<>("total3"));

        professionPointCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        interestPointCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));

        skillTableView.setItems(getSkills());
    }

    private ObservableList<Skill> getSkills() {
        return FXCollections.observableArrayList(this.skills);
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
}
