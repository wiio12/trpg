package indi.wiio.info;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.image.Image;
import indi.wiio.utils.ReadObjectsHelper;
import indi.wiio.utils.WriteObjectsHelper;

import java.io.*;


public class Self implements Serializable {
    private static Self self = new Self();
    private static String path = "user/card/self.ser";

    public static Self getSelf() {
        return self;
    }

    private transient StringProperty name = new SimpleStringProperty();

    private File profilePhotoFile = new File(getClass().getResource("/image/profilePhoto.jpg").toString());
    private transient Image profilePhoto = new Image(profilePhotoFile.toURI().toString());

    public static void writeMapToFile(){
        try {
            File file = new File(path);
            if(!file.exists())file.createNewFile();
            FileOutputStream fileOut = new FileOutputStream(path);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(self);
            objectOut.close();
            System.out.println("Self  was succesfully written to a file");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void readMapFromFile(){
        try {

            FileInputStream fileIn = new FileInputStream(path);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            self = (Self) objectIn.readObject();
            objectIn.close();
            System.out.println("Self  was successfully read from a file");

        } catch (Exception ex) {
            System.err.println("self.ser read failed");
        }
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        WriteObjectsHelper.writeAllProp(s, name);

    }


    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        name = new SimpleStringProperty();
        ReadObjectsHelper.readAllProp(s,name);
        profilePhoto = new Image(profilePhotoFile.toURI().toString());

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

    public File getProfilePhotoFile() {
        return profilePhotoFile;
    }

    public void setProfilePhotoFile(File profilePhotoFile) {
        this.profilePhotoFile = profilePhotoFile;
    }

    public Image getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(Image profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    //    public static StringProperty nameProperty() {
//        return name;
//    }
//
//    public static String getName() {
//        return name.get();
//    }
//
//    public static void setName(String name) {
//        Self.name.set(name);
//    }
//
//    public static Image getProfilePhoto() {
//        return profilePhoto;
//    }
//
//    public static void setProfilePhoto(Image profilePhoto) {
//        Self.profilePhoto = profilePhoto;
//    }
//
//    public static File getProfilePhotoFile() {
//        return profilePhotoFile;
//    }
//
//    public static void setProfilePhotoFile(File profilePhotoFile) {
//        Self.profilePhotoFile = profilePhotoFile;
//    }
}
