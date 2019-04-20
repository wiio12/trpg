package indi.wiio.info.character;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.css.SimpleStyleableStringProperty;
import indi.wiio.utils.ReadObjectsHelper;
import indi.wiio.utils.WriteObjectsHelper;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Header implements Serializable {
    private transient StringProperty profilePhotoPath = new SimpleStringProperty();
    private transient StringProperty playerName = new SimpleStringProperty();
    private transient StringProperty gender = new SimpleStringProperty();
    private ArrayList<String> tagList = new ArrayList<>();
    private boolean isHeaderSet = false;

    private void writeObject(ObjectOutputStream s) throws IOException {
        WriteObjectsHelper.writeAllProp(s,profilePhotoPath, playerName, gender);
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        initInstance();
        s.defaultReadObject();
        ReadObjectsHelper.readAllProp(s,profilePhotoPath, playerName, gender);
    }

    private void initInstance() {
        profilePhotoPath = new SimpleStringProperty();
        playerName = new SimpleStringProperty();
        gender = new SimpleStringProperty();
    }

    public void setupRelation(){
        playerName.bind(Resources.getResources().getPlayerInfo().nameProperty());
        gender.bind(Resources.getResources().getPlayerInfo().genderProperty());
    }

    public String getProfilePhotoPath() {
        return profilePhotoPath.get();
    }

    public StringProperty profilePhotoPathProperty() {
        return profilePhotoPath;
    }

    public void setProfilePhotoPath(String profilePhotoPath) {
        this.profilePhotoPath.set(profilePhotoPath);
    }

    public String getPlayerName() {
        return playerName.get();
    }

    public StringProperty playerNameProperty() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName.set(playerName);
    }

    public String getGender() {
        return gender.get();
    }

    public StringProperty genderProperty() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender.set(gender);
    }

    public ArrayList<String> getTagList() {
        return tagList;
    }

    public void setTagList(ArrayList<String> tagList) {
        this.tagList = tagList;
    }

    public boolean isHeaderSet() {
        return isHeaderSet;
    }

    public void setHeaderSet(boolean headerSet) {
        isHeaderSet = headerSet;
    }
}
