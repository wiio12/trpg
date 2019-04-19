package player;

import info.Others;
import info.Self;
import info.character.Background;
import info.character.Resources;
import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import player.full.PlayerStatistics;
import player.small.PlayerStatisticsSmall;


import java.net.URL;
import java.util.ResourceBundle;

public class BackgroundPane implements Initializable {

    public TextArea ta_PersonalDescription;
    public TextArea ta_Beliefs;
    public TextArea ta_ImportantPerson;
    public TextArea ta_ImportantPlace;
    public TextArea ta_ImportantThings;
    public TextArea ta_Speciality;
    public TextArea ta_Wound;
    public TextArea ta_Phobia;
    public VBox sizePane;
    public TitledPane titlePane;
    public VBox vb_PersonalDescription;
    public VBox vb_Beliefs;
    public VBox vb_ImportantPerson;
    public VBox vb_ImportantPlace;
    public VBox vb_ImportantThings;
    public VBox vb_Speciality;
    public VBox vb_Wound;
    public VBox vb_Phobia;

    public Label lbl_PersonalDescription;
    public Label lbl_Beliefs;
    public Label lbl_ImportantPerson;
    public Label lbl_ImportantPlace;
    public Label lbl_ImportantThings;
    public Label lbl_Speciality;
    public Label lbl_Wound;
    public Label lbl_Phobia;


    private PlayerStatisticsSmall playerStatisticsSmall;
    private PlayerStatistics playerStatistics;

    private Background background;
    private PaneStatus paneStatus;

    private String kp_userName;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(Resources.getResources() != null){
            background = Resources.getResources().getBackground();
        }
        Platform.runLater(this::initializeStatus);

    }

    private void initializeStatus() {
        System.out.println(paneStatus);
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
            background = others.getResources().getBackground();
            handleSmallStatusInit();
        }else{
            System.out.println("kp:background");
        }
    }

    private void handleSmallStatusInit() {
        titlePane.setCollapsible(true);
        sizePane.setPrefWidth(350.0);

        lbl_PersonalDescription  = new Label();
        lbl_Beliefs = new Label();
        lbl_ImportantPerson = new Label();
        lbl_ImportantPlace = new Label();
        lbl_ImportantThings = new Label();
        lbl_Speciality = new Label();
        lbl_Phobia = new Label();
        lbl_Wound = new Label();

        lbl_PersonalDescription.textProperty().bind(background.personalDescriptionProperty());
        lbl_Beliefs.textProperty().bind(background.beliefsProperty());
        lbl_ImportantPerson.textProperty().bind(background.importantPersonProperty());
        lbl_ImportantPlace.textProperty().bind(background.importantPlaceProperty());
        lbl_ImportantThings.textProperty().bind(background.importantThingsProperty());
        lbl_Speciality.textProperty().bind(background.specialityProperty());
        lbl_Phobia.textProperty().bind(background.phobiaProperty());
        lbl_Wound.textProperty().bind(background.woundProperty());

        lbl_PersonalDescription.setWrapText(true);
        lbl_Beliefs.setWrapText(true);
        lbl_ImportantPerson.setWrapText(true);
        lbl_ImportantPlace.setWrapText(true);
        lbl_ImportantThings.setWrapText(true);
        lbl_Speciality.setWrapText(true);
        lbl_Phobia.setWrapText(true);
        lbl_Wound.setWrapText(true);

        vb_PersonalDescription.getChildren().remove(ta_PersonalDescription);
        vb_Beliefs.getChildren().remove(ta_Beliefs);
        vb_ImportantPerson.getChildren().remove(ta_ImportantPerson);
        vb_ImportantPlace.getChildren().remove(ta_ImportantPlace);
        vb_ImportantThings.getChildren().remove(ta_ImportantThings);
        vb_Speciality.getChildren().remove(ta_Speciality);
        vb_Phobia.getChildren().remove(ta_Phobia);
        vb_Wound.getChildren().remove(ta_Wound);

        vb_PersonalDescription.getChildren().add(lbl_PersonalDescription);
        vb_Beliefs.getChildren().add(lbl_Beliefs);
        vb_ImportantPerson.getChildren().add(lbl_ImportantPerson);
        vb_ImportantPlace.getChildren().add(lbl_ImportantPlace);
        vb_ImportantThings.getChildren().add(lbl_ImportantThings);
        vb_Speciality.getChildren().add(lbl_Speciality);
        vb_Phobia.getChildren().add(lbl_Phobia);
        vb_Wound.getChildren().add(lbl_Wound);




    }

    private void handleShowFullStatusInit() {
        titlePane.setCollapsible(false);
        sizePane.setPrefWidth(500.0);

        ta_PersonalDescription.textProperty().bind(background.personalDescriptionProperty());
        ta_PersonalDescription.setEditable(false);

        ta_Beliefs.textProperty().bind(background.beliefsProperty());
        ta_Beliefs.setEditable(false);

        ta_ImportantPerson.textProperty().bind(background.importantPersonProperty());
        ta_ImportantPerson.setEditable(false);

        ta_ImportantPlace.textProperty().bind(background.importantPlaceProperty());
        ta_ImportantPlace.setEditable(false);

        ta_ImportantThings.textProperty().bind(background.importantThingsProperty());
        ta_ImportantThings.setEditable(false);

        ta_Wound.textProperty().bind(background.woundProperty());
        ta_Wound.setEditable(false);

        ta_Speciality.textProperty().bind(background.specialityProperty());
        ta_Speciality.setEditable(false);

        ta_Phobia.textProperty().bind(background.phobiaProperty());
        ta_Phobia.setEditable(false);

    }

    private void handleEditStatusInit() {
        titlePane.setCollapsible(false);
        sizePane.setPrefWidth(500.0);

        ta_PersonalDescription.textProperty().bind(background.personalDescriptionProperty());
        ta_Beliefs.textProperty().bind(background.beliefsProperty());
        ta_ImportantPerson.textProperty().bind(background.importantPersonProperty());
        ta_ImportantPlace.textProperty().bind(background.importantPlaceProperty());
        ta_ImportantThings.textProperty().bind(background.importantThingsProperty());
        ta_Speciality.textProperty().bind(background.specialityProperty());
        ta_Phobia.textProperty().bind(background.phobiaProperty());
        ta_Wound.textProperty().bind(background.woundProperty());

        ta_PersonalDescription.textProperty().unbind();
        ta_Beliefs.textProperty().unbind();
        ta_ImportantPerson.textProperty().unbind();
        ta_ImportantPlace.textProperty().unbind();
        ta_ImportantThings.textProperty().unbind();
        ta_Speciality.textProperty().unbind();
        ta_Phobia.textProperty().unbind();
        ta_Wound.textProperty().unbind();

        background.personalDescriptionProperty().bind(ta_PersonalDescription.textProperty());
        background.beliefsProperty().bind(ta_Beliefs.textProperty());
        background.importantPersonProperty().bind(ta_ImportantPerson.textProperty());
        background.importantPlaceProperty().bind(ta_ImportantPlace.textProperty());
        background.importantThingsProperty().bind(ta_ImportantThings.textProperty());
        background.specialityProperty().bind(ta_Speciality.textProperty());
        background.woundProperty().bind(ta_Wound.textProperty());
        background.phobiaProperty().bind(ta_Phobia.textProperty());
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

    public Background getBackground() {
        return background;
    }

    public void setBackGround(Background backGround) {
        this.background = backGround;
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
