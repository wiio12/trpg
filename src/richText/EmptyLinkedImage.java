package richText;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class EmptyLinkedImage implements LinkedImage {

    private static Image empIcon = new Image("richText/resources/brokenImage.png");

    @Override
    public boolean isReal() {
        return false;
    }

    @Override
    public String getImagePath() {
        return "";
    }

    @Override
    public Node createNode() {
        ImageView result = new ImageView(empIcon);
        System.out.println(new File("richText/resources/brokenImage.png").exists());
        return result;
    }
}
