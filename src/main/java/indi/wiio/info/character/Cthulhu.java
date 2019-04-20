package indi.wiio.info.character;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import indi.wiio.utils.ReadObjectsHelper;
import indi.wiio.utils.WriteObjectsHelper;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Cthulhu implements Cloneable, Serializable {
    private transient StringProperty item;
    private transient StringProperty god;

    private Cthulhu() {
        item = new SimpleStringProperty();
        god = new SimpleStringProperty();
    }

    public Cthulhu(String item, String god) {
        this();
        this.setItem(item);
        this.setGod(god);
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        WriteObjectsHelper.writeAllProp(s,item, god);
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        initInstance();
        ReadObjectsHelper.readAllProp(s, item, god);
    }

    private void initInstance() {
        item = new SimpleStringProperty();
        god = new SimpleStringProperty();
    }

    public String getItem() {
        return item.get();
    }

    public StringProperty itemProperty() {
        return item;
    }

    public void setItem(String item) {
        this.item.set(item);
    }

    public String getGod() {
        return god.get();
    }

    public StringProperty godProperty() {
        return god;
    }

    public void setGod(String god) {
        this.god.set(god);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
