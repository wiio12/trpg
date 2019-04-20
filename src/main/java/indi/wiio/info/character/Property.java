package indi.wiio.info.character;

import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import indi.wiio.utils.ReadObjectsHelper;
import indi.wiio.utils.WriteObjectsHelper;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.NavigableMap;
import java.util.TreeMap;

public class Property  implements Serializable {
    private transient IntegerProperty STR = new SimpleIntegerProperty();
    private transient IntegerProperty DEX = new SimpleIntegerProperty();
    private transient IntegerProperty POW = new SimpleIntegerProperty();
    private transient IntegerProperty CON = new SimpleIntegerProperty();
    private transient IntegerProperty APP = new SimpleIntegerProperty();
    private transient IntegerProperty EDU = new SimpleIntegerProperty();
    private transient IntegerProperty SIZ = new SimpleIntegerProperty();
    private transient IntegerProperty INT_IDEA = new SimpleIntegerProperty();
    private transient IntegerProperty MOV = new SimpleIntegerProperty();
    private transient IntegerProperty ADJ = new SimpleIntegerProperty();

    private transient IntegerProperty SUMWithLuck = new SimpleIntegerProperty();
    private transient IntegerProperty SUMWithoutLuck = new SimpleIntegerProperty();


    public Property(){
        STR.set(0);
        DEX.set(0);
        POW.set(0);
        CON.set(0);
        APP.set(0);
        EDU.set(0);
        SIZ.set(0);
        INT_IDEA.set(0);
        MOV.set(0);
        ADJ.set(0);
    }

    public void setupRelation(){
        SUMWithoutLuck.bind(
                Bindings.add(Bindings.add(Bindings.add(Bindings.add(Bindings.add(
                        Bindings.add(Bindings.add(STR, DEX),POW),CON),APP),EDU),SIZ),INT_IDEA));
        SUMWithLuck.bind(Bindings.add(SUMWithoutLuck, Resources.getResources().getPlayerStatus().luckProperty()));

        STR.addListener(((observableValue, number, t1) -> handleMOV()));
        DEX.addListener(((observableValue, number, t1) -> handleMOV()));
        SIZ.addListener(((observableValue, number, t1) -> handleMOV()));

    }


    private void writeObject(ObjectOutputStream s) throws IOException {
        WriteObjectsHelper.writeAllProp(s,STR, DEX,POW,CON,APP,EDU,SIZ,INT_IDEA,MOV,ADJ, SUMWithLuck, SUMWithoutLuck);
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        initInstance();
        ReadObjectsHelper.readAllProp(s, STR, DEX,POW,CON,APP,EDU,SIZ,INT_IDEA,MOV,ADJ, SUMWithLuck, SUMWithoutLuck);
    }

    private void initInstance() {
        STR = new SimpleIntegerProperty();
        DEX = new SimpleIntegerProperty();
        POW = new SimpleIntegerProperty();
        CON = new SimpleIntegerProperty();
        APP = new SimpleIntegerProperty();
        EDU = new SimpleIntegerProperty();
        SIZ = new SimpleIntegerProperty();
        INT_IDEA = new SimpleIntegerProperty();
        MOV = new SimpleIntegerProperty();
        ADJ = new SimpleIntegerProperty();

        SUMWithLuck = new SimpleIntegerProperty();
        SUMWithoutLuck = new SimpleIntegerProperty();
    }

    public void handleMOV() {
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
        int mins = map.floorEntry(Resources.getResources().getPlayerInfo().getAge()).getValue();
        MOV.set(max - mins);
        ADJ.set(max - 8);
    }

    public int getSTR() {
        return STR.get();
    }

    public IntegerProperty STRProperty() {
        return STR;
    }

    public void setSTR(int STR) {
        this.STR.set(STR);
    }

    public int getDEX() {
        return DEX.get();
    }

    public IntegerProperty DEXProperty() {
        return DEX;
    }

    public void setDEX(int DEX) {
        this.DEX.set(DEX);
    }

    public int getPOW() {
        return POW.get();
    }

    public IntegerProperty POWProperty() {
        return POW;
    }

    public void setPOW(int POW) {
        this.POW.set(POW);
    }

    public int getCON() {
        return CON.get();
    }

    public IntegerProperty CONProperty() {
        return CON;
    }

    public void setCON(int CON) {
        this.CON.set(CON);
    }

    public int getAPP() {
        return APP.get();
    }

    public IntegerProperty APPProperty() {
        return APP;
    }

    public void setAPP(int APP) {
        this.APP.set(APP);
    }

    public int getEDU() {
        return EDU.get();
    }

    public IntegerProperty EDUProperty() {
        return EDU;
    }

    public void setEDU(int EDU) {
        this.EDU.set(EDU);
    }

    public int getSIZ() {
        return SIZ.get();
    }

    public IntegerProperty SIZProperty() {
        return SIZ;
    }

    public void setSIZ(int SIZ) {
        this.SIZ.set(SIZ);
    }

    public int getINT_IDEA() {
        return INT_IDEA.get();
    }

    public IntegerProperty INT_IDEAProperty() {
        return INT_IDEA;
    }

    public void setINT_IDEA(int INT_IDEA) {
        this.INT_IDEA.set(INT_IDEA);
    }

    public int getMOV() {
        return MOV.get();
    }

    public IntegerProperty MOVProperty() {
        return MOV;
    }

    public void setMOV(int MOV) {
        this.MOV.set(MOV);
    }

    public int getADJ() {
        return ADJ.get();
    }

    public IntegerProperty ADJProperty() {
        return ADJ;
    }

    public void setADJ(int ADJ) {
        this.ADJ.set(ADJ);
    }

    public int getSUMWithLuck() {
        return SUMWithLuck.get();
    }

    public IntegerProperty SUMWithLuckProperty() {
        return SUMWithLuck;
    }

    public void setSUMWithLuck(int SUMWithLuck) {
        this.SUMWithLuck.set(SUMWithLuck);
    }

    public int getSUMWithoutLuck() {
        return SUMWithoutLuck.get();
    }

    public IntegerProperty SUMWithoutLuckProperty() {
        return SUMWithoutLuck;
    }

    public void setSUMWithoutLuck(int SUMWithoutLuck) {
        this.SUMWithoutLuck.set(SUMWithoutLuck);
    }
}
