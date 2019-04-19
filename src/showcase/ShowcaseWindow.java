package showcase;

import imageExplorer.ImageExplorer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import network.client.ClientMain;
import utils.Univ;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ShowcaseWindow implements Initializable {

    public Tab tab;
    public TextField nameField;
    public Region resetButton;
    public Region uploadButton;
    public Pane imageExplorerContainer;

    private ShowcaseItem showcaseItem = new ShowcaseItem();
    private ImageExplorer imageExplorer = new ImageExplorer();

    private Boolean active = true;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Tooltip.install(resetButton, new Tooltip("重置图片浏览器视图"));
        Tooltip.install(uploadButton, new Tooltip("发送图片"));


        //TODO：改bind?
        nameField.textProperty().addListener((observableValue, s, t1) -> {
            showcaseItem.setName(t1);
        });
        tab.textProperty().bind(nameField.textProperty());
        nameField.setText("未命名");

        uploadButton.addEventHandler(MouseEvent.MOUSE_PRESSED, mouseEvent -> {
            if(showcaseItem.getImage() != null){
                try {
                    ClientMain.getClient().sendShowCaseImage(showcaseItem);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        resetButton.addEventHandler(MouseEvent.MOUSE_PRESSED, mouseEvent -> {
            if(imageExplorer != null) {
                imageExplorer.reset();
            }
        });

//
//        ContextMenu clientList = new ContextMenu();
//        MenuItem write = new MenuItem("write");
//        MenuItem read = new MenuItem("read");
//        clientList.getItems().addAll(write, read);
//        write.setOnAction( e -> {
//            if(showcaseItem.getImage() != null)
//                showcaseItem.writeMapToFile();
//            else
//                System.out.println("no content displayed!!!!");
//        });
//        read.setOnAction(e -> {
//            showcaseItem.readMapFromFile();
//            if(active) {
//                addImage(showcaseItem.getImage());
//            } else {
//                imageExplorer.setImage(showcaseItem.getImage());
//                imageExplorer.reset();
//            }
//            nameField.setText(showcaseItem.getName());
//        });
//
//        uploadButton.setContextMenu(clientList);




    }


    @FXML
    void inputImage(MouseEvent event) throws IOException {
        if(!active) return;

        String initialDir = System.getProperty("user.dir");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Insert image");
        fileChooser.setInitialDirectory(new File(initialDir));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("images","*.jpg","*.png","*.gif"));
        File selectedFile = fileChooser.showOpenDialog((Stage)imageExplorerContainer.getScene().getWindow());
        if (selectedFile != null) {
            String imagePath = selectedFile.getAbsolutePath();
            imagePath = imagePath.replace('\\',  '/');
            showcaseItem.setImage(new Image("File:"+imagePath));
            showcaseItem.setImageType(Univ.getFileType(imagePath));

            addImage(showcaseItem.getImage());


        }

    }

    public void setAll(ShowcaseItem showcaseItem){
        this.showcaseItem = showcaseItem;
        addImage(showcaseItem.getImage());
        nameField.setText(showcaseItem.getName());

    }

    private void addImage(Image image) {
        StackPane imageExplorerPane = imageExplorer.initialize((Stage)imageExplorerContainer.getScene().getWindow());
        imageExplorerContainer.getChildren().addAll(imageExplorerPane);
        imageExplorer.setImage(image);
        imageExplorer.reset();
        imageExplorerContainer.getStyleClass().remove("imageExplorerContainerActive");

        imageExplorerContainer.heightProperty().addListener( (observableValue, number, t1) -> {
            imageExplorer.reset();
        });

        imageExplorerContainer.widthProperty().addListener( (observableValue, number, t1) -> {
            imageExplorer.reset();
        });

        active = false;
    }
//
//    @FXML
//    void reset(MouseEvent event) {
//        if(imageExplorer != null) {
//            imageExplorer.reset();
//        }
//    }

    public ShowcaseItem getShowcaseItem() {
        return showcaseItem;
    }

    public void upload(MouseEvent mouseEvent) {
        //TODO:upload
    }

}
