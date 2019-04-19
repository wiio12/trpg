package info.character;

import javafx.beans.binding.Bindings;
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

public class PlayerStatus  implements Serializable {
    private transient IntegerProperty hitPoint = new SimpleIntegerProperty();
    private transient IntegerProperty HPLimit = new SimpleIntegerProperty();
    private transient IntegerProperty sanity = new SimpleIntegerProperty();
    private transient IntegerProperty sanLimit = new SimpleIntegerProperty();
    private transient IntegerProperty luck = new SimpleIntegerProperty();
    private transient IntegerProperty magicPoint = new SimpleIntegerProperty();
    private transient IntegerProperty MPLimit = new SimpleIntegerProperty();
    private transient StringProperty physicalStatus = new SimpleStringProperty();
    private transient StringProperty mentalStatus = new SimpleStringProperty();


    public void setupRelation(){
        HPLimit.bind(Bindings.add(Resources.getResources().getProperty().CONProperty(),
                Resources.getResources().getProperty().SIZProperty()).divide(10));
        MPLimit.bind(Resources.getResources().getProperty().POWProperty().divide(5));

    }


    private void writeObject(ObjectOutputStream s) throws IOException {
        WriteObjectsHelper.writeAllProp(s,hitPoint, HPLimit, sanity, sanLimit, luck, magicPoint, MPLimit,
                physicalStatus, mentalStatus);
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        initInstance();
        ReadObjectsHelper.readAllProp(s, hitPoint, HPLimit, sanity, sanLimit, luck, magicPoint, MPLimit,
                physicalStatus, mentalStatus);
    }

    private void initInstance() {
        hitPoint = new SimpleIntegerProperty();
        HPLimit = new SimpleIntegerProperty();
        sanity = new SimpleIntegerProperty();
        sanLimit = new SimpleIntegerProperty();
        luck = new SimpleIntegerProperty();
        magicPoint = new SimpleIntegerProperty();
        MPLimit = new SimpleIntegerProperty();
        physicalStatus = new SimpleStringProperty();
        mentalStatus = new SimpleStringProperty();
    }

    public int getHitPoint() {
        return hitPoint.get();
    }

    public IntegerProperty hitPointProperty() {
        return hitPoint;
    }

    public void setHitPoint(int hitPoint) {
        this.hitPoint.set(hitPoint);
    }

    public int getSanity() {
        return sanity.get();
    }

    public IntegerProperty sanityProperty() {
        return sanity;
    }

    public void setSanity(int sanity) {
        this.sanity.set(sanity);
    }

    public int getLuck() {
        return luck.get();
    }

    public IntegerProperty luckProperty() {
        return luck;
    }

    public void setLuck(int luck) {
        this.luck.set(luck);
    }

    public int getMagicPoint() {
        return magicPoint.get();
    }

    public IntegerProperty magicPointProperty() {
        return magicPoint;
    }

    public void setMagicPoint(int magicPoint) {
        this.magicPoint.set(magicPoint);
    }

    public String getPhysicalStatus() {
        return physicalStatus.get();
    }

    public StringProperty physicalStatusProperty() {
        return physicalStatus;
    }

    public void setPhysicalStatus(String physicalStatus) {
        this.physicalStatus.set(physicalStatus);
    }

    public String getMentalStatus() {
        return mentalStatus.get();
    }

    public StringProperty mentalStatusProperty() {
        return mentalStatus;
    }

    public void setMentalStatus(String mentalStatus) {
        this.mentalStatus.set(mentalStatus);
    }

    public int getHPLimit() {
        return HPLimit.get();
    }

    public IntegerProperty HPLimitProperty() {
        return HPLimit;
    }

    public void setHPLimit(int HPLimit) {
        this.HPLimit.set(HPLimit);
    }

    public int getSanLimit() {
        return sanLimit.get();
    }

    public IntegerProperty sanLimitProperty() {
        return sanLimit;
    }

    public void setSanLimit(int sanLimit) {
        this.sanLimit.set(sanLimit);
    }

    public int getMPLimit() {
        return MPLimit.get();
    }

    public IntegerProperty MPLimitProperty() {
        return MPLimit;
    }

    public void setMPLimit(int MPLimit) {
        this.MPLimit.set(MPLimit);
    }
}
