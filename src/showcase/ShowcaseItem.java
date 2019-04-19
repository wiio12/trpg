package showcase;

import utils.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;

import java.io.*;

public class ShowcaseItem implements Serializable {
    private transient StringProperty name;
    private transient Image image;
    private String imageType;
    private transient File imageFile;

    private static String path = "user/card/showcase.ser";

    public ShowcaseItem() {
        this.name = new SimpleStringProperty();
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public Image getImage() {
        return image;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public void setImage(Image image) {
        //if(image ==  null) return;
        //this.imageFile = new File(image.getUrl().replace("file:",""));
        this.image = image;
    }

    public void writeMapToFile(){
        try {
            File file = new File(path);
            FileOutputStream fileOut = new FileOutputStream(path);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(this);
            objectOut.close();
            System.out.println("img was successfully written to a file");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void readMapFromFile(){
        try {
            FileInputStream fileIn = new FileInputStream(path);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            ShowcaseItem tmp = (ShowcaseItem) objectIn.readObject();
            this.setName(tmp.getName());
            this.setImage(tmp.getImage());
            objectIn.close();
            System.out.println("img was successfully read from a file");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        WriteObjectsHelper.writeAllProp(s, name);

    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        name = new SimpleStringProperty();
        ReadObjectsHelper.readAllProp(s,name);
        if(imageFile != null)
            image = new Image(imageFile.toURI().toString());
    }
}
