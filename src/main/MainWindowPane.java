package main;

import info.Others;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Pair;
import player.PaneStatus;
import player.small.PlayerStatisticsSmall;
import richText.RichText;
import chat.ChatPane;
import showcase.ShowcaseTabs;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowPane implements Initializable {

    public StackPane sp_richTextContainer;
    public StackPane sp_playerStatisticsSmall;
    //    @FXML private PlayerStatisticsSmall playerStatisticsSmallController;
    @FXML private ChatPane ChatPaneController;
    @FXML private ShowcaseTabs showcaseTabsController;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(()->{
            VBox box = new RichText().initialize(((Stage) sp_richTextContainer.getScene().getWindow()));
            sp_richTextContainer.getChildren().add(box);

            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/player/small/player_statistics_small.fxml"));
            Parent parent = null;
            PlayerStatisticsSmall playerStatisticsSmall = new PlayerStatisticsSmall(PaneStatus.SMALL);
            fxmlLoader.setController(playerStatisticsSmall);
            try {
                parent = fxmlLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            sp_playerStatisticsSmall.getChildren().add(parent);


        });

    }
}
