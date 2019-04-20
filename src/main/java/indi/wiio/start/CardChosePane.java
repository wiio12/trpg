package indi.wiio.start;

import indi.wiio.info.character.Resources;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Pair;
import indi.wiio.player.PaneStatus;
import indi.wiio.player.full.PlayerStatistics;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;
import java.util.ResourceBundle;

public class CardChosePane implements Initializable {
    public HBox hb_cardContainer;
    public Region btn_newCard;
    public StackPane btn_stackPane;
    public Region btn_back;
    private Scene scene;
    private CreateRoomPane createRoomPane;

    private ArrayList<Pair<CardPane, Parent>> cardParentPair = new ArrayList<>();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //scene = hb_cardContainer.getScene();
        Resources.readMapFromFile();
        Platform.runLater(this::loadAll);

        btn_back.addEventHandler(MouseEvent.MOUSE_PRESSED , mouseEvent -> {
            ((Stage)btn_back.getScene().getWindow()).setScene(createRoomPane.getScene());
        });

        btn_stackPane.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
            String key = Resources.addResources();
            Resources.setMainResource(key);
            try{
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/player/full/player_statistics.fxml"));

                PlayerStatistics playerStatistics = new PlayerStatistics(PaneStatus.EDIT_FULL);
                playerStatistics.setMainResource(key);
                fxmlLoader.setController(playerStatistics);
                Parent parent = fxmlLoader.load();
                Stage stage = new Stage();
                stage.setTitle("创建人物卡");
                stage.setScene(new Scene(parent));
                stage.show();

            }catch (IOException ex){
               System.out.println("load 不到啊");
            }
        });

        Resources.addCharacterResourcesChangeListener(this::loadAll);
    }

    private void loadAll(){
        for(Pair<CardPane, Parent> paneParentPair : cardParentPair){
            hb_cardContainer.getChildren().remove(paneParentPair.getValue());
        }
        Map<String, Resources> resourcesMap = Resources.getResourcesMap();
        for(String k : resourcesMap.keySet()){
            System.out.println(k);
            Platform.runLater(()->{
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/start/card_pane.fxml"));
                    CardPane cardPane = new CardPane(k);
                    fxmlLoader.setController(cardPane);
                    Parent parent = fxmlLoader.load();
                    cardParentPair.add(new Pair<>(cardPane, parent));
                    hb_cardContainer.getChildren().add(parent);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public CreateRoomPane getCreateRoomPane() {
        return createRoomPane;
    }

    public void setCreateRoomPane(CreateRoomPane createRoomPane) {
        this.createRoomPane = createRoomPane;
    }
}
