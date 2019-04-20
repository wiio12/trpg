package indi.wiio.info.character;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import indi.wiio.utils.ReadObjectsHelper;
import indi.wiio.utils.WriteObjectsHelper;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Weapon implements Cloneable, Serializable {
    private transient StringProperty weaponName;
    private transient StringProperty weaponType;
    private transient StringProperty weaponSkill;
    private transient IntegerProperty successRate1;
    private transient IntegerProperty successRate2;
    private transient IntegerProperty successRate3;
    private transient StringProperty damage;
    private transient StringProperty basicRange;
    private transient StringProperty penetrate;
    private transient StringProperty times;
    private transient StringProperty loadAmount;
    private transient StringProperty functionality;

    private Weapon(){
        weaponName = new SimpleStringProperty();
        weaponType = new SimpleStringProperty();
        weaponSkill = new SimpleStringProperty();
        successRate1 = new SimpleIntegerProperty();
        successRate2 = new SimpleIntegerProperty();
        successRate3 = new SimpleIntegerProperty();
        damage = new SimpleStringProperty();
        basicRange = new SimpleStringProperty();
        penetrate = new SimpleStringProperty();
        times = new SimpleStringProperty();
        loadAmount = new SimpleStringProperty();
        functionality = new SimpleStringProperty();

        successRate2.bind(successRate1.divide(2));
        successRate3.bind(successRate1.divide(5));

    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        WriteObjectsHelper.writeAllProp(s,weaponName, weaponSkill, weaponType, successRate1, damage,
                basicRange, penetrate, times, loadAmount, functionality);
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        initInstance();
        ReadObjectsHelper.readAllProp(s, weaponName, weaponSkill, weaponType, successRate1, damage,
                basicRange, penetrate, times, loadAmount, functionality);
    }

    private void initInstance() {
        weaponName = new SimpleStringProperty();
        weaponType = new SimpleStringProperty();
        weaponSkill = new SimpleStringProperty();
        successRate1 = new SimpleIntegerProperty();
        successRate2 = new SimpleIntegerProperty();
        successRate3 = new SimpleIntegerProperty();
        damage = new SimpleStringProperty();
        basicRange = new SimpleStringProperty();
        penetrate = new SimpleStringProperty();
        times = new SimpleStringProperty();
        loadAmount = new SimpleStringProperty();
        functionality = new SimpleStringProperty();

        successRate2.bind(successRate1.divide(2));
        successRate3.bind(successRate1.divide(5));
    }

    public Weapon(String weaponName, String weaponType, String weaponSkill, int successRate, String damage,
                  String basicRange, String penetrate, String times, String loadAmount, String functionality) {
        this();
        this.weaponName.set(weaponName);
        this.weaponType.set(weaponType);
        this.weaponSkill.set(weaponSkill);
        this.successRate1.set(successRate);
        this.damage.set(damage);
        this.basicRange.set(basicRange);
        this.penetrate.set(penetrate);
        this.times.set(times);
        this.loadAmount.set(loadAmount);
        this.functionality.set(functionality);
    }

    public String getWeaponName() {
        return weaponName.get();
    }

    public StringProperty weaponNameProperty() {
        return weaponName;
    }

    public void setWeaponName(String weaponName) {
        this.weaponName.set(weaponName);
    }

    public String getWeaponType() {
        return weaponType.get();
    }

    public StringProperty weaponTypeProperty() {
        return weaponType;
    }

    public void setWeaponType(String weaponType) {
        this.weaponType.set(weaponType);
    }

    public String getWeaponSkill() {
        return weaponSkill.get();
    }

    public StringProperty weaponSkillProperty() {
        return weaponSkill;
    }

    public void setWeaponSkill(String weaponSkill) {
        this.weaponSkill.set(weaponSkill);
    }

    public int getSuccessRate1() {
        return successRate1.get();
    }

    public IntegerProperty successRate1Property() {
        return successRate1;
    }

    public void setSuccessRate1(int successRate1) {
        this.successRate1.set(successRate1);
    }

    public int getSuccessRate2() {
        return successRate2.get();
    }

    public IntegerProperty successRate2Property() {
        return successRate2;
    }

    public void setSuccessRate2(int successRate2) {
        this.successRate2.set(successRate2);
    }

    public int getSuccessRate3() {
        return successRate3.get();
    }

    public IntegerProperty successRate3Property() {
        return successRate3;
    }

    public void setSuccessRate3(int successRate3) {
        this.successRate3.set(successRate3);
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

    public String getBasicRange() {
        return basicRange.get();
    }

    public StringProperty basicRangeProperty() {
        return basicRange;
    }

    public void setBasicRange(String basicRange) {
        this.basicRange.set(basicRange);
    }

    public String getPenetrate() {
        return penetrate.get();
    }

    public StringProperty penetrateProperty() {
        return penetrate;
    }

    public void setPenetrate(String penetrate) {
        this.penetrate.set(penetrate);
    }

    public String getTimes() {
        return times.get();
    }

    public StringProperty timesProperty() {
        return times;
    }

    public void setTimes(String times) {
        this.times.set(times);
    }

    public String getLoadAmount() {
        return loadAmount.get();
    }

    public StringProperty loadAmountProperty() {
        return loadAmount;
    }

    public void setLoadAmount(String loadAmount) {
        this.loadAmount.set(loadAmount);
    }

    public String getFunctionality() {
        return functionality.get();
    }

    public StringProperty functionalityProperty() {
        return functionality;
    }

    public void setFunctionality(String functionality) {
        this.functionality.set(functionality);
    }


    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
