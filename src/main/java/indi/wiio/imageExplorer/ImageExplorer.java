package indi.wiio.imageExplorer;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.NonInvertibleTransformException;



public class ImageExplorer {

    private double mousePosX;
    private double mousePosY;
    private StackPane imageRegion;
    private ImageView imageView;

    private Image image;

    public ImageExplorer() {
        imageRegion = new StackPane();
        imageView = new ImageView();
    }


    public StackPane initialize(Stage mainStage) {

        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setCursor(Cursor.OPEN_HAND);

        imageRegion.getChildren().add(imageView);

        imageRegion.setOnMousePressed(e -> {
            mousePosX = e.getSceneX();
            mousePosY = e.getSceneY();

            if (e.isSecondaryButtonDown()) {
                Point2D pointOnRegion = null;
                try {
                    pointOnRegion = imageRegion.getLocalToSceneTransform().inverseTransform(e.getSceneX(), e.getSceneY());
                } catch (NonInvertibleTransformException e1) {
                    e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                imageRegion.getChildren().add(new Rectangle(pointOnRegion.getX() - 5, pointOnRegion.getY() - 5, 10, 10));
            }
        });

        imageRegion.setOnScroll(e -> {
            Point2D pointOnImage = null;
            try {
                pointOnImage = imageRegion.getLocalToSceneTransform().inverseTransform(e.getSceneX(), e.getSceneY());
            } catch (NonInvertibleTransformException e1) {
                e1.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            zoom(getZoomFactor(e), pointOnImage);
        });

        imageView.setOnMouseDragged(e -> {
            imageView.setCursor(Cursor.CLOSED_HAND);

            double mouseDeltaX = (e.getSceneX() - mousePosX);
            double mouseDeltaY = (e.getSceneY() - mousePosY);
            mousePosX = e.getSceneX();
            mousePosY = e.getSceneY();

            imageRegion.setTranslateX(imageRegion.getTranslateX() + mouseDeltaX);
            imageRegion.setTranslateY(imageRegion.getTranslateY() + mouseDeltaY);
        });

        imageView.setOnMouseReleased(e -> {
            imageView.setCursor(Cursor.OPEN_HAND);
        });

        mainStage.getScene().getStylesheets().add(this.getClass().getResource("/css/imageExplorer/style.css").toExternalForm());

        return this.imageRegion;
    }

    public void zoom(double zoomFactor, Point2D pointOnImage) {
        double currentX = pointOnImage.getX();
        double currentY = pointOnImage.getY();

        double currentDistanceFromCenterX = currentX - imageRegion.getBoundsInLocal().getWidth() / 2;
        double currentDistanceFromCenterY = currentY - imageRegion.getBoundsInLocal().getHeight() / 2;

        double addScaleX = currentDistanceFromCenterX * zoomFactor;
        double addScaleY = currentDistanceFromCenterY * zoomFactor;

        double translationX = addScaleX - currentDistanceFromCenterX;
        double translationY = addScaleY - currentDistanceFromCenterY;

        imageRegion.setTranslateX(imageRegion.getTranslateX() - translationX * imageRegion.getScaleX());
        imageRegion.setTranslateY(imageRegion.getTranslateY() - translationY * imageRegion.getScaleY());

        imageRegion.setScaleX(imageRegion.getScaleX() * zoomFactor);
        imageRegion.setScaleY(imageRegion.getScaleY() * zoomFactor);
    }

    public void setImage(Image image) {
        this.image = image;
        this.imageView.setImage(image);
    }

    public Image getImage() {
        return this.image;
    }

    public void reset() {
        try {
            Pane outer = (Pane)this.imageRegion.getParent();

            StackPane.setMargin(imageRegion, new Insets(outer.getHeight() * 0.5,
                    0, 0, outer.getWidth() * 0.5));
            imageView.setFitWidth(outer.getWidth());
            imageView.setFitHeight(outer.getHeight());
            imageRegion.setPrefSize(outer.getWidth(),outer.getHeight());
        } catch (Exception e) {
            System.out.println(e);
        }
        imageRegion.setTranslateX(0);
        imageRegion.setTranslateY(0);
        imageRegion.setScaleX(1);
        imageRegion.setScaleY(1);

    }

    private double getZoomFactor(ScrollEvent e) {
        double scrolldistance = e.getDeltaY();
        if(scrolldistance < 0) {
            return  1 - scrolldistance*-0.003;
        } else {
            return  1 + scrolldistance*0.003;
        }
    }

}