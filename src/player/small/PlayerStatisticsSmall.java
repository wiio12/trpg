package player.small;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import player.*;
import player.full.PlayerStatistics;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PlayerStatisticsSmall implements Initializable {

    public FlowPane playerStatisticsSmallContainer;
    public ScrollPane scrollPane;

    private PlayerInfoPane playerInfoPane;
    private PropertyPane propertyPane;
    private StatusPane statusPane;
    private SkillPane skillPane;
    private WeaponPane weaponPane;
    private FightPane fightPane;
    private HeaderPane headerPane;
    private AssetPane assetPane;
    private BackgroundPane backgroundPane;
    private CompanionPane companionPane;
    private CthulhuPane cthulhuPane;
    private ExperiencePane experiencePane;
    private ItemPane itemPane;

    private PaneStatus paneStatus;
    private String kp_userName;

    public PlayerStatisticsSmall(PaneStatus paneStatus){
        this.paneStatus = paneStatus;
    }

    public void setKp_userName(String kp_userName) {
        this.kp_userName = kp_userName;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playerStatisticsSmallContainer.setVgap(20);

        try {
            initializeHeader();
            initializePlayerInfo();
            initializeStatus();
            initializeProperty();
            initializeSkill();
            initializeWeapon();
            initializeFight();
            initializeAsset();
            initializeBackground();
            initializeCompanion();
            initializeCthulhu();
            initializeItem();
            initializeExperience();
        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    private void initializeItem() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../item_pane.fxml"));
        Parent parent = fxmlLoader.load();
        itemPane = fxmlLoader.getController();
        itemPane.setPlayerStatisticsSmall(this);
        itemPane.setKp_userName(kp_userName);
        itemPane.setPaneStatus(paneStatus);
        playerStatisticsSmallContainer.getChildren().add(parent);
    }

    private void initializeExperience() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../experience_pane.fxml"));
        Parent parent = fxmlLoader.load();
        experiencePane = fxmlLoader.getController();
        experiencePane.setPlayerStatisticsSmall(this);
        experiencePane.setKp_userName(kp_userName);
        experiencePane.setPaneStatus(paneStatus);
        playerStatisticsSmallContainer.getChildren().add(parent);
    }

    private void initializeCthulhu() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../cthulhu_pane.fxml"));
        Parent parent = fxmlLoader.load();
        cthulhuPane = fxmlLoader.getController();
        cthulhuPane.setPaneStatus(paneStatus);
        cthulhuPane.setKp_userName(kp_userName);
        cthulhuPane.setPlayerStatisticsSmall(this);
        playerStatisticsSmallContainer.getChildren().add(parent);

    }

    @SuppressWarnings("Duplicates")
    private void initializeCompanion() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../companion_pane.fxml"));
        Parent parent = fxmlLoader.load();
        companionPane = fxmlLoader.getController();
        companionPane.setPlayerStatisticsSmall(this);
        companionPane.setKp_userName(kp_userName);
        companionPane.setPaneStatus(paneStatus);
        playerStatisticsSmallContainer.getChildren().add(parent);
    }

    private void initializeBackground() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../background_pane.fxml"));
        Parent parent = fxmlLoader.load();
        backgroundPane = fxmlLoader.getController();
        backgroundPane.setPlayerStatisticsSmall(this);
        backgroundPane.setPaneStatus(paneStatus);
        backgroundPane.setKp_userName(kp_userName);
        playerStatisticsSmallContainer.getChildren().add(parent);
    }

    private void initializeAsset() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../asset_pane.fxml"));
        Parent parent = fxmlLoader.load();
        assetPane = fxmlLoader.getController();
        assetPane.setPlayerStatisticsSmall(this);
        assetPane.setKp_userName(kp_userName);
        playerStatisticsSmallContainer.getChildren().add(parent);
        assetPane.setPaneStatus(paneStatus);
    }

    private void initializeHeader() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../header_pane.fxml"));
        Parent parent = fxmlLoader.load();
        headerPane = fxmlLoader.getController();
        headerPane.setPlayerStatisticsSmall(this);
        headerPane.setPaneStatus(paneStatus);
        headerPane.setKp_userName(kp_userName);
        playerStatisticsSmallContainer.getChildren().add(parent);
    }

    private void initializeFight() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../fight_pane.fxml"));
        Parent parent = fxmlLoader.load();
        fightPane = fxmlLoader.getController();
        fightPane.setPlayerStatisticsSmall(this);
        fightPane.setPaneStatus(paneStatus);
        fightPane.setKp_userName(kp_userName);
        playerStatisticsSmallContainer.getChildren().add(parent);
    }

    private void initializePlayerInfo() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../player_info_pane.fxml"));
        Parent parent = fxmlLoader.load();
        playerInfoPane = fxmlLoader.getController();
        playerInfoPane.setPlayerStatisticsSmall(this);
        playerInfoPane.setPaneStatus(paneStatus);
        playerInfoPane.setKp_userName(kp_userName);
        playerStatisticsSmallContainer.getChildren().add(parent);
    }

    private void initializeWeapon() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../weapon_pane.fxml"));
        Parent parent = fxmlLoader.load();
        weaponPane = fxmlLoader.getController();
        weaponPane.setPlayerStatisticsSmall(this);
        weaponPane.setPaneStatus(paneStatus);
        weaponPane.setKp_userName(kp_userName);
        playerStatisticsSmallContainer.getChildren().add(parent);

    }

    private void initializeSkill() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("skill_pane.fxml"));
        Parent parent = fxmlLoader.load();
        skillPane = fxmlLoader.getController();
        skillPane.setPlayerStatisticsSmall(this);
        skillPane.setPaneStatus(paneStatus);
        skillPane.setKp_userName(kp_userName);
        playerStatisticsSmallContainer.getChildren().add(parent);

    }


    private void initializeStatus() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../status_pane.fxml"));
        Parent parent = fxmlLoader.load();
        statusPane = fxmlLoader.getController();
        statusPane.setKp_userName(kp_userName);
        statusPane.setPlayerStatisticsSmall(this);
        statusPane.setPaneStatus(paneStatus);
        playerStatisticsSmallContainer.getChildren().add(parent);

    }

    private void initializeProperty() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../property_pane.fxml"));
        Parent parent = fxmlLoader.load();
        propertyPane = fxmlLoader.getController();
        propertyPane.setPlayerStatisticsSmall(this);
        propertyPane.setPaneStatus(paneStatus);
        propertyPane.setKp_userName(kp_userName);
        playerStatisticsSmallContainer.getChildren().add(parent);

    }



    public PlayerInfoPane getPlayerInfoPane() {
        return playerInfoPane;
    }

    public PropertyPane getPropertyPane() {
        return propertyPane;
    }

    public StatusPane getStatusPane() {
        return statusPane;
    }

    public SkillPane getSkillPane() {
        return skillPane;
    }

    public WeaponPane getWeaponPane() {
        return weaponPane;
    }

    public FlowPane getPlayerStatisticsSmallContainer() {
        return playerStatisticsSmallContainer;
    }

    public FightPane getFightPane() {
        return fightPane;
    }

    public HeaderPane getHeaderPane() {
        return headerPane;
    }

    public AssetPane getAssetPane() {
        return assetPane;
    }

    public BackgroundPane getBackgroundPane() {
        return backgroundPane;
    }

    public CompanionPane getCompanionPane() {
        return companionPane;
    }

    public CthulhuPane getCthulhuPane() {
        return cthulhuPane;
    }

    public ExperiencePane getExperiencePane() {
        return experiencePane;
    }

    public ItemPane getItemPane() {
        return itemPane;
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


}
