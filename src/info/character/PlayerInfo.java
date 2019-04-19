package info.character;

import info.Self;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import utils.ReadObjectsHelper;
import utils.WriteObjectsHelper;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.NavigableMap;
import java.util.TreeMap;

public class PlayerInfo  implements Serializable {
    private transient StringProperty name = new SimpleStringProperty();
    private transient StringProperty playerName = new SimpleStringProperty();
    private transient StringProperty years = new SimpleStringProperty();
    private transient StringProperty profession = new SimpleStringProperty();
    private transient IntegerProperty age = new SimpleIntegerProperty();
    private transient StringProperty address = new SimpleStringProperty();
    private transient StringProperty hometown = new SimpleStringProperty();
    private transient StringProperty gender = new SimpleStringProperty();

    public PlayerInfo(){
        name.set("");
        playerName.set("");
        years.set("");
        profession.set("");
        age.set(0);
        address.set("");
        hometown.set("");
        gender.set("");
    }

    public void setupRelation(){
        age.addListener((observableValue, number, t1) -> handleMOV());
        playerName.bind(Self.getSelf().nameProperty());
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        WriteObjectsHelper.writeAllProp(s,name, playerName, years, profession, age, address, address,hometown,gender);
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        initInstance();
        ReadObjectsHelper.readAllProp(s,name, playerName, years, profession, age, address, address,hometown,gender);
    }

    private void initInstance() {
        name = new SimpleStringProperty();
        playerName = new SimpleStringProperty();
        years = new SimpleStringProperty();
        profession = new SimpleStringProperty();
        age = new SimpleIntegerProperty();
        address = new SimpleStringProperty();
        hometown = new SimpleStringProperty();
        gender = new SimpleStringProperty();
    }

    private void handleMOV() {
        System.out.println("2333");
        IntegerProperty STR = Resources.getResources().getProperty().STRProperty();
        IntegerProperty DEX = Resources.getResources().getProperty().DEXProperty();
        IntegerProperty SIZ = Resources.getResources().getProperty().SIZProperty();
        IntegerProperty MOV = Resources.getResources().getProperty().MOVProperty();
        IntegerProperty ADJ = Resources.getResources().getProperty().ADJProperty();

        int a,b,c,d,max;
        a = b = c = d = max = 0;
        if(STR.get() == SIZ.get() || DEX.get() == SIZ.get()) {a = 8; max = a;}
        if(STR.get() < SIZ.get() && DEX.get() < SIZ.get()) {b = 7; max = (max<b)? b:max;}
        if(STR.get() > SIZ.get() && DEX.get() > SIZ.get()) { c = 9;max = (max<c)? c:max;}
        if( (STR.get() == DEX.get() && DEX.get() == SIZ.get()) || STR.get()>SIZ.get() || DEX.get()>SIZ.get())
        {d = 8;max = (max<d)? d:max;}

        NavigableMap<Integer, Integer> map = new TreeMap<>();
        map.put(0,0);
        map.put(40,1);
        map.put(50,2);
        map.put(60,3);
        map.put(70,4);
        map.put(80,5);
        int mins = map.floorEntry(age.get()).getValue();
        MOV.set(max - mins);
        ADJ.set(max - 8);
        System.out.println(max- mins);
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

    public String getPlayerName() {
        return playerName.get();
    }

    public StringProperty playerNameProperty() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName.set(playerName);
    }

    public String getYears() {
        return years.get();
    }

    public StringProperty yearsProperty() {
        return years;
    }

    public void setYears(String years) {
        this.years.set(years);
    }

    public String getProfession() {
        return profession.get();
    }

    public StringProperty professionProperty() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession.set(profession);
    }

    public int getAge() {
        return age.get();
    }

    public IntegerProperty ageProperty() {
        return age;
    }

    public void setAge(int age) {
        this.age.set(age);
    }

    public String getAddress() {
        return address.get();
    }

    public StringProperty addressProperty() {
        return address;
    }

    public void setAddress(String address) {
        this.address.set(address);
    }

    public String getHometown() {
        return hometown.get();
    }

    public StringProperty hometownProperty() {
        return hometown;
    }

    public void setHometown(String hometown) {
        this.hometown.set(hometown);
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
}
