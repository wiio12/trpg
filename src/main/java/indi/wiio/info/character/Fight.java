package indi.wiio.info.character;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.util.Pair;
import indi.wiio.utils.ReadObjectsHelper;
import indi.wiio.utils.WriteObjectsHelper;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.NavigableMap;
import java.util.TreeMap;

public class Fight implements Serializable {
    private transient StringProperty damage = new SimpleStringProperty();
    private transient StringProperty build = new SimpleStringProperty();
    private transient IntegerProperty dodge = new SimpleIntegerProperty();
    private transient IntegerProperty armor = new SimpleIntegerProperty();

    public Fight(){
        this("","",0,0);
    }

    public void setupRelation(){
        IntegerProperty STR = Resources.getResources().getProperty().STRProperty();
        IntegerProperty SIZ = Resources.getResources().getProperty().SIZProperty();
        STR.addListener((observableValue, number, t1) -> handleDamage());
        SIZ.addListener((observableValue, number, t1) -> handleDamage());

        Skill skDodge = Resources.getResources().getSkill("闪避");
        if(skDodge != null){
            skDodge.totalProperty().addListener((observableValue, number, t1) -> dodge.set(skDodge.getTotal()));
        }
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        WriteObjectsHelper.writeAllProp(s,damage, build, dodge, armor);
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        initInstance();
        ReadObjectsHelper.readAllProp(s, damage, build, dodge, armor);
    }

    private void initInstance() {
        damage = new SimpleStringProperty();
        build = new SimpleStringProperty();
        dodge = new SimpleIntegerProperty();
        armor = new SimpleIntegerProperty();

    }


    private void handleDamage() {
        int sum = Resources.getResources().getProperty().getSTR() + Resources.getResources().getProperty().getSIZ();

        NavigableMap<Integer, Pair<String, Integer>> map = new TreeMap<>();
        map.put(0, new Pair<>("0",0));
        map.put(2, new Pair<>("-2", -2));
        map.put(65, new Pair<>("-1",-1));
        map.put(85,new Pair<>("0",0));
        map.put(125, new Pair<>("+1D4", 1));
        map.put(165, new Pair<>("+1D6", 2));
        map.put(205, new Pair<>("+2D6", 3));
        for(int i = 285; i<=2125; i += 80){
            int add = (i - 205)/80 + 2;
            String str = "+" + add + "D6";
            map.put(i, new Pair<>(str, add + 1));
        }

        Pair<String, Integer> result = map.floorEntry(sum).getValue();
        damage.set(result.getKey());
        build.set(result.getValue()+"");


    }

    public Fight(String damage, String build, int dodge, int armor) {
        this.damage.set(damage);
        this.build.set(build);
        this.dodge.set(dodge);
        this.armor.set(armor);

    }

    public String getDamage() {
        return damage.get();
    }

    public StringProperty damageProperty() {
        return damage;
    }

    public void setDamage(String damage) {
        this.damage.set(damage);
    }

    public String getBuild() {
        return build.get();
    }

    public StringProperty buildProperty() {
        return build;
    }

    public void setBuild(String build) {
        this.build.set(build);
    }

    public int getDodge() {
        return dodge.get();
    }

    public IntegerProperty dodgeProperty() {
        return dodge;
    }

    public void setDodge(int dodge) {
        this.dodge.set(dodge);
    }

    public int getArmor() {
        return armor.get();
    }

    public IntegerProperty armorProperty() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor.set(armor);
    }
}
