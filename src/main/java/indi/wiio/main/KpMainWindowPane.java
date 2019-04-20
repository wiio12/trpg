package indi.wiio.main;

import indi.wiio.info.Others;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Pair;
import indi.wiio.network.client.Client;
import indi.wiio.network.client.ClientMain;
import indi.wiio.player.PaneStatus;
import indi.wiio.player.full.PlayerStatistics;
import indi.wiio.player.small.PlayerStatisticsSmall;
import indi.wiio.richText.RichText;
import indi.wiio.chat.ChatPane;
import indi.wiio.showcase.ShowcaseTabs;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class KpMainWindowPane implements Initializable {
    public StackPane sp_richTextContainer;
    public TabPane tp_playerStatisticContainer;

    private ArrayList<Pair<PlayerStatisticsSmall, Parent>> pairs = new ArrayList<>();

    @FXML
    private PlayerStatisticsSmall playerStatisticsSmallController;
    @FXML
    private ChatPane ChatPaneController;
    @FXML
    private ShowcaseTabs showcaseTabsController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Platform.runLater(() -> {
            VBox box = new RichText().initialize(((Stage) sp_richTextContainer.getScene().getWindow()));
            sp_richTextContainer.getChildren().add(box);

            refreshPlayerTab();

        });


    }

    public void refreshPlayerTab(){
        tp_playerStatisticContainer.getTabs().clear();
        for(String userName: Others.getOthersMap().keySet()){
            addPlayerTab(userName);
        }
    }

    private void addPlayerTab(String userName) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/player/small/player_statistics_small.fxml"));
        Parent parent = null;
        PlayerStatisticsSmall playerStatisticsSmall = new PlayerStatisticsSmall(PaneStatus.KP);
        playerStatisticsSmall.setKp_userName(userName);
        fxmlLoader.setController(playerStatisticsSmall);
        try {
            parent = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Pair<PlayerStatisticsSmall, Parent> panelParentPair = new Pair<>(playerStatisticsSmall, parent);
        pairs.add(panelParentPair);
        String tabName = Others.getOthersMap().get(userName).getResources().getPlayerInfo().getName();
        Tab tab = new Tab(tabName);
        tab.setContent(parent);
        tp_playerStatisticContainer.getTabs().add(tab);

    }
}
