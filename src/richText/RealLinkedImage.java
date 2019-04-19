package richText;

import java.io.File;

import imageExplorer.ImageExplorer;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/**
 * A custom object which contains a file path to an image file.
 * When rendered in the rich text editor, the image is loaded from the
 * specified file.
 */
public class RealLinkedImage implements LinkedImage {

    private static double maxWidth = 80.0;
    private static double maxHeight = 60.0;
    private static Image magIcon = new Image("richText/resources/magnify.png");

    private final String imagePath;

    /**
     * Creates a new linked image object.
     *
     * @param imagePath The path to the image file.
     */
    public RealLinkedImage(String imagePath) {

        // if the image is below the current working directory,
        // then store as relative path name.
        String currentDir = System.getProperty("user.dir") + File.separatorChar;
        if (imagePath.startsWith(currentDir)) {
            imagePath = imagePath.substring(currentDir.length());
        }

        this.imagePath = imagePath;
    }

    @Override
    public boolean isReal() {
        return true;
    }

    @Override
    public String getImagePath() {
        return imagePath;
    }

    @Override
    public String toString() {
        return String.format("RealLinkedImage[path=%s]", imagePath);
    }

    @Override
    public Node createNode() {
        if(!new File(imagePath).exists()) return new EmptyLinkedImage().createNode();
        Image image = new Image("file:" + imagePath); // XXX: No need to create new Image objects each time -
                                                      // could be cached in the model layer
        ImageView result = new ImageView(image);
        result.setPreserveRatio(true);

        if(image.getHeight() > maxHeight || image.getWidth() > maxWidth){
            StackPane wrap = new StackPane() {
                @Override
                public double getBaselineOffset() {
                    return maxHeight;
                }
            };
            if(image.getWidth() > maxWidth) {
                result.setFitWidth(maxWidth);
            }
            if(image.getHeight() > maxHeight) {
                result.setFitHeight(maxHeight);
            }
            AnchorPane MagLayer = new AnchorPane();
            ImageView mag = new ImageView(magIcon);
            MagLayer.getChildren().add(mag);
            AnchorPane.setBottomAnchor(mag, 5.0);
            AnchorPane.setRightAnchor(mag, 5.0);
            wrap.getChildren().addAll(result,MagLayer);
            wrap.prefHeightProperty().bind(result.fitHeightProperty().add(2));
            wrap.prefWidthProperty().bind(result.fitWidthProperty().add(2));
            StackPane.setAlignment(result, Pos.CENTER);
            wrap.getStyleClass().addAll("imageWrap");
            wrap.setOnMouseClicked(this::handleMouseClicked);
            wrap.setCursor(Cursor.HAND);
            return wrap;
        }

        return result;
    }

    private void handleMouseClicked(MouseEvent event) {
        StackPane wrap = (StackPane) event.getSource();
        ImageView imageView =(ImageView)wrap.getChildrenUnmodifiable().get(0);
        Image image = imageView.getImage();

        Stage dialog = new Stage();
        dialog.setTitle(image.getUrl());

        VBox outer = new VBox();
        outer.setPrefSize(800, 600);
        Scene dialogScene = new Scene(outer,800,600);
        dialog.setScene(dialogScene);

        ImageExplorer imageExplorer = new ImageExplorer();
        imageExplorer.setImage(image);
        StackPane imageExplorerPane = imageExplorer.initialize(dialog);

        outer.getChildren().addAll(imageExplorerPane);
        outer.setAlignment(Pos.CENTER);
        imageExplorer.reset();

        dialog.showAndWait();


    }
}
