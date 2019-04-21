package indi.wiio.controllers.player;

import indi.wiio.info.Others;
import indi.wiio.info.Self;
import indi.wiio.info.character.Header;
import indi.wiio.info.character.Resources;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import indi.wiio.controllers.player.full.PlayerStatistics;
import indi.wiio.controllers.player.small.PlayerStatisticsSmall;


import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HeaderPane  implements Initializable {

    public ImageView profilePhoto;
    public Label nameLabel;
    public Region gender;
    public FlowPane tagPane;
    public VBox tagContainer;

    private PlayerStatisticsSmall playerStatisticsSmall;
    private PlayerStatistics playerStatistics;
    private Header header;
    private PaneStatus paneStatus;
    private File imgFile;
    private Image cardImg;
    private String cardRes;

    private String kp_userName;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(this::initializeStatus);
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
            header = others.getResources().getHeader();
        }else{
            System.out.println("kp:fight");
        }

        profilePhoto.setImage(others.getImage());
        commonInit();

    }

    private void setResource(String cardRes){
        header = Resources.getResources(cardRes).getHeader();
    }

    private void setResource(){
        header = Resources.getResources().getHeader();
    }

    private void commonInit(){
        nameLabel.textProperty().bind(header.playerNameProperty());
        if(header.getGender().equals("男")){
            gender.getStyleClass().clear();
            gender.getStyleClass().add("genderIconMale");
        }else if(header.getGender().equals("女")){
            gender.getStyleClass().clear();
            gender.getStyleClass().add("genderIconFemale");
        }else if(header.getGender().equals("扶她")){
            gender.getStyleClass().clear();
            gender.getStyleClass().add("genderIconNeutral");
        }

        header.genderProperty().addListener((observableValue, number, t1) -> {
            System.out.println(header.getGender());

            if(header.getGender().equals("男")){
                gender.getStyleClass().clear();
                gender.getStyleClass().add("genderIconMale");
            }else if(header.getGender().equals("女")){
                gender.getStyleClass().clear();
                gender.getStyleClass().add("genderIconFemale");
            }else if(header.getGender().equals("扶她")){
                gender.getStyleClass().clear();
                gender.getStyleClass().add("genderIconNeutral");
            }
        });
    }

    private void handleShowCardInit() {
        setResource(cardRes);
        loadHeaderPhoto();
        tagContainer.getChildren().remove(tagPane);
        nameLabel.setStyle("-fx-text-fill: #dadada");

        commonInit();

    }

    private void handleSmallStatusInit() {
        //TODO:小面板的时候不太一样，要点击能看到show那种完整版面
        setResource();
        loadHeaderPhoto();
        commonInit();

        profilePhoto.addEventHandler(MouseEvent.MOUSE_PRESSED, mouseEvent -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/player/full/player_statistics.fxml"));
                PlayerStatistics playerStatistics = new PlayerStatistics(PaneStatus.SHOW_FULL);
                playerStatistics.setMainResource(Resources.getMainResource());
                fxmlLoader.setController(playerStatistics);
                Parent parent = fxmlLoader.load();
                Stage stage = new Stage();
                stage.setTitle("查看人物卡");
                stage.setScene(new Scene(parent));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        });

    }

    private void handleShowFullStatusInit() {
        setResource();
       // tagContainer.getChildren().remove(tagPane);
        loadHeaderPhoto();

        commonInit();

    }

    private void setDefaultProfilePhoto(){
        profilePhoto.setImage(Self.getSelf().getProfilePhoto());
        header.setProfilePhotoPath(Self.getSelf().getProfilePhotoFile().getAbsolutePath());
    }

    private void loadHeaderPhoto(){
        Image image = new Image((new File(header.getProfilePhotoPath())).toURI().toString());
        profilePhoto.setImage(image);
    }

    private void handleEditStatusInit() {
        setResource();
        commonInit();
        System.out.println("header:" + Resources.getMainResource());
        tagContainer.getChildren().remove(tagPane);

        if(!header.isHeaderSet())
            setDefaultProfilePhoto();
        else
            loadHeaderPhoto();

        profilePhoto.setOnMouseClicked(mouseEvent -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open Resource File");
            File imageFile = fileChooser.showOpenDialog(profilePhoto.getScene().getWindow());
            if (imageFile != null) {
                imgFile = new File("./user/resources/" + imageFile.getName());
                header.setProfilePhotoPath(imageFile.getAbsolutePath());
                try {
                    FileUtils.copyFile(imageFile, imgFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                cardImg = new Image(imgFile.toURI().toString());
                profilePhoto.setImage(cardImg);
                header.setHeaderSet(true);
            }
        });
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

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public PaneStatus getPaneStatus() {
        return paneStatus;
    }

    public void setPaneStatus(PaneStatus paneStatus) {
        this.paneStatus = paneStatus;
    }

    public File getImgFile() {
        return imgFile;
    }


    public String getCardRes() {
        return cardRes;
    }

    public void setCardRes(String cardRes) {
        this.cardRes = cardRes;
    }

    public void setImgFile(File imgFile) {
        this.imgFile = imgFile;
    }

    public Image getCardImg() {
        return cardImg;
    }

    public void setCardImg(Image cardImg) {
        this.cardImg = cardImg;
    }

    public String getKp_userName() {
        return kp_userName;
    }

    public void setKp_userName(String kp_userName) {
        this.kp_userName = kp_userName;
    }
}
