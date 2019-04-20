package indi.wiio.info;

import indi.wiio.info.character.Resources;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import indi.wiio.network.client.ClientMain;
import indi.wiio.utils.Univ;

import javax.imageio.ImageIO;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Others implements Serializable {
    private static String filepath = "./user/card/others.ser";
    private static String fileMyPath = "./user/card/myself.ser";
    private static Map<String, Others> othersMap = new HashMap<>();

    public static Map<String, Others> getOthersMap() {
        return othersMap;
    }

    public static void setOthersMap(Map<String, Others> othersMap) {
        Others.othersMap = othersMap;
    }


    private Resources resources;
    private transient Image image;
    private String imageType;


    public static void writeToFile(String name){
        try {
            File file = new File(filepath);
            if(!file.exists())file.createNewFile();
            FileOutputStream fileOut = new FileOutputStream(filepath);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(othersMap.get(name));
            objectOut.close();
            System.out.println("The others was succesfully written to a file");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static boolean writeMySelfToFile(){
        try {
            Others others = new Others();
            if(ClientMain.isPlayer()){
                if(Resources.getResources() == null){
                    return false;
                }
                others = new Others();
                others.setResources(Resources.getResources());
                others.setImage(new Image(
                        new File(Resources.getResources().getHeader().getProfilePhotoPath()).toURI().toString()));
                others.setImageType(Univ.getFileType(Resources.getResources().getHeader().getProfilePhotoPath()));


            }else if(ClientMain.isKeeper()){
                Resources resources = new Resources();
                resources.getPlayerInfo().setName(Self.getSelf().getName());
                resources.getPlayerInfo().setPlayerName(Self.getSelf().getName());
                others = new Others();
                others.setResources(resources);
                others.setImage(Self.getSelf().getProfilePhoto());
                others.setImageType(Univ.getFileType(Self.getSelf().getProfilePhotoFile().toString()));
            }else {
                return false;
            }

            File file = new File(filepath);
            if(!file.exists())file.createNewFile();
            FileOutputStream fileOut = new FileOutputStream(fileMyPath);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(others);
            objectOut.close();
            System.out.println("The <Myself> was succesfully written to a file");
            return true;


        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return true;
    }

    public static void readFromFile(String name, String filepath){
        try {
            FileInputStream fileIn = new FileInputStream(filepath);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            Others others = (Others) objectIn.readObject();
            othersMap.put(name, others);
            objectIn.close();
            System.out.println("The others <"+ name + "> was succesfully read from a file");

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        s.writeBoolean(image != null);
        if (image != null) {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), imageType, s);
        }
    }

    @SuppressWarnings("Duplicates")
    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        if (s.readBoolean()) {
            image = new Image(s);
        } else {
            image = null;
        }
    }





    public Resources getResources() {
        return resources;
    }

    public void setResources(Resources resources) {
        this.resources = resources;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public static String getFilepath() {
        return filepath;
    }

    public static void setFilepath(String filepath) {
        Others.filepath = filepath;
    }

    public String getImageType() {
        return imageType;
    }

    public void setImageType(String imageType) {
        this.imageType = imageType;
    }

    public static String getFileMyPath() {
        return fileMyPath;
    }

    public static void setFileMyPath(String fileMyPath) {
        Others.fileMyPath = fileMyPath;
    }
}
