package showcase;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.Tooltip;
import network.client.ClientMain;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ShowcaseTabs implements Initializable {

    public TabPane showcaseTabPane;
    public Tab showcaseNewTab;

    private List<ShowcaseItem> itemList = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        try {
            addTab();
        } catch (IOException e) {
            e.printStackTrace();
        }
        showcaseTabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.equals(showcaseNewTab) || showcaseTabPane.getTabs().size() <= 1) {
                try {
                    addTab();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        ClientMain.getClient().addShowcaseImageListener((image, imageName, imageType) -> {
            ShowcaseItem showcaseItem = new ShowcaseItem();
            showcaseItem.setImageType(imageType);
            showcaseItem.setName(imageName);
            showcaseItem.setImage(image);
            Platform.runLater(()->{
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("showcaseWindow.fxml"));
                    Tab newTab = null;
                    newTab = fxmlLoader.load();
                    showcaseTabPane.getTabs().add(1, newTab);
                    showcaseTabPane.getSelectionModel().select(1);
                    newTab.setOnCloseRequest(event -> {
                        showcaseTabPane.getSelectionModel().selectNext();
                    });
                    ShowcaseWindow showcaseWindow = fxmlLoader.getController();
                    showcaseWindow.setAll(showcaseItem);
                    itemList.add(showcaseItem);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        });
    }

    private void addTab() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getResource("showcaseWindow.fxml"));
        Tab newTab = fxmlLoader.load();
        showcaseTabPane.getTabs().add(1, newTab);
        showcaseTabPane.getSelectionModel().select(1);
        newTab.setOnCloseRequest(event -> {
            showcaseTabPane.getSelectionModel().selectNext();
        });
        itemList.add(0, ((ShowcaseWindow)fxmlLoader.getController()).getShowcaseItem());
    }


}
