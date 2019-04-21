package indi.wiio.info.character;

import indi.wiio.info.Self;
import javafx.beans.Observable;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import indi.wiio.utils.ReadObjectsHelper;
import indi.wiio.utils.WriteObjectsHelper;

import java.io.*;
import java.util.*;

public class Resources implements Serializable {

    private static Map<String, Resources> resourcesMap = new HashMap<>();
    private static final String filepath="./user/card/cards.ser";
    private static int resourceCount = 0;
    private static String mainResource;
    private static ArrayList<CharacterResourcesChangeListener> characterResourcesChangeListeners = new ArrayList<>();

    public static void addCharacterResourcesChangeListener(CharacterResourcesChangeListener listener){
        characterResourcesChangeListeners.add(listener);
    }

    public static Resources getResources(String name){
        if(Self.getSelf().getName().equalsIgnoreCase(name)){
            if(!resourcesMap.containsKey(name)){
                Resources resources = new Resources();
                resourcesMap.put(Self.getSelf().getName(), resources);

                resources.setupRelation();
            }
        }
        return resourcesMap.getOrDefault(name, null);
    }

    public static void removeResources(String key){
        resourcesMap.remove(key);
        resourceCount--;
    }

    private static void addKeeper(){

    }

    public static String addResources(){
        String resName = Self.getSelf().getName() + resourceCount;
        resourceCount ++;
        Resources resources = new Resources();
        resourcesMap.put(resName, resources);
        String tmp = mainResource;
        mainResource = resName;
        resources.setupRelation();
        mainResource = tmp;
        return resName;
    }

    public static String getMainResource() {
        return mainResource;
    }

    public static void setMainResource(String mainResource) {
        Resources.mainResource = mainResource;
    }

    private void setupRelation(){
        this.property.setupRelation();
        this.playerStatus.setupRelation();
        this.playerInfo.setupRelation();
        this.fight.setupRelation();
        this.asset.setupRelation();
        this.header.setupRelation();
    }

    public static void setupAllRelation(){
        Resources.getResources().property.setupRelation();
        Resources.getResources().playerStatus.setupRelation();
        Resources.getResources().playerInfo.setupRelation();
        Resources.getResources().fight.setupRelation();
        Resources.getResources().asset.setupRelation();
        Resources.getResources().header.setupRelation();
    }

    public static void writeMapToFile(){
        try {
            File file = new File(filepath);
            if(!file.exists())file.createNewFile();
            FileOutputStream fileOut = new FileOutputStream(filepath);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(resourcesMap);
            objectOut.close();
            System.out.println("The <Cards>  was successfully written to a file");

            for(CharacterResourcesChangeListener c : characterResourcesChangeListeners){
                c.onCharacterResourcesChange();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void readMapFromFile(){
        try {

            FileInputStream fileIn = new FileInputStream(filepath);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            resourcesMap = (Map<String, Resources>) objectIn.readObject();
            objectIn.close();
            resourceCount = resourcesMap.size();
            System.out.println("The <Cards>  was successfully read from a file");

        } catch (Exception ex) {
            System.err.println("The <Cards> read failed");
        }
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        s.defaultWriteObject();
        s.writeObject(new ArrayList<>(companions));
        s.writeObject(new ArrayList<>(cthulhus));
        s.writeObject(new ArrayList<>(experiences));
        s.writeObject(new ArrayList<>(itemEquipeds));
        s.writeObject(new ArrayList<>(weapons));

    }

    @SuppressWarnings("Duplicates")
    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        s.defaultReadObject();
        List<Companion> list1 = (List<Companion>) s.readObject();
        companions = FXCollections.observableArrayList(list1);

        List<Cthulhu> list2 = (List<Cthulhu>) s.readObject();
        cthulhus = FXCollections.observableArrayList(list2);

        List<Experience> list3 = (List<Experience>) s.readObject();
        experiences = FXCollections.observableArrayList(list3);

        List<ItemEquiped> list4 = (List<ItemEquiped>) s.readObject();
        itemEquipeds = FXCollections.observableArrayList(list4);

        List<Weapon> list = (List<Weapon>) s.readObject();
        weapons = FXCollections.observableArrayList(list);


    }


    public static Resources getResources(){
        return getResources(mainResource);
    }


    private Asset asset = new Asset();
    //private transient ArrayList<SkillType> skillTypes = new ArrayList<>(Arrays.asList(SkillType.values()));
    private ArrayList<Skill> skills = new ArrayList<>();
    private Background background = new Background();
    private transient ObservableList<Companion> companions = FXCollections.observableArrayList();
    private transient ObservableList<Cthulhu> cthulhus = FXCollections.observableArrayList();
    private transient ObservableList<Experience> experiences = FXCollections.observableArrayList();
    private Fight fight = new Fight();
    private Header header = new Header();
    private transient ObservableList<ItemEquiped> itemEquipeds = FXCollections.observableArrayList();
    private PlayerInfo playerInfo = new PlayerInfo();
    private Property property = new Property();
    private PlayerStatus playerStatus = new PlayerStatus();
    private transient ObservableList<Weapon> weapons = FXCollections.observableArrayList();

    public Resources(){
        ArrayList<SkillType> skillTypes = new ArrayList<>(Arrays.asList(SkillType.values()));
        for(SkillType s: skillTypes){
            skills.add(s.getSkill());
        }
    }

    public Skill getSkill(String name){
        for(Skill sk : skills){
            if(sk.getName().equalsIgnoreCase(name)){
                return sk;
            }
        }
        return null;
    }


    public ObservableList<Cthulhu> getCthulhus() {
        return cthulhus;
    }

    public ObservableList<Companion> getCompanions() {
        return companions;
    }




    public Background getBackground() {
        return background;
    }

    public Asset getAsset() {
        return asset;
    }

    public ObservableList<Experience> getExperiences() {
        return experiences;
    }

    public Fight getFight() {
        return fight;
    }

    public Header getHeader() {
        return header;
    }

    public ObservableList<ItemEquiped> getItemEquipeds() {
        return itemEquipeds;
    }

    public PlayerInfo getPlayerInfo() {
        return playerInfo;
    }

    public Property getProperty() {
        return property;
    }

    public PlayerStatus getPlayerStatus() {
        return playerStatus;
    }

    public ObservableList<Weapon> getWeapons() {
        return weapons;
    }

    public static Map<String, Resources> getResourcesMap() {
        return resourcesMap;
    }

    public ArrayList<Skill> getSkills() {
        return skills;
    }
}
