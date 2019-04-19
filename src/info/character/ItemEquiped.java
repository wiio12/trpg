package info.character;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import utils.ReadObjectsHelper;
import utils.WriteObjectsHelper;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class ItemEquiped implements Serializable {
    private transient StringProperty itemDescription;

    private ItemEquiped() {
        itemDescription = new SimpleStringProperty();
    }

    public ItemEquiped(String itemDescription) {
        this();
        this.setItemDescription(itemDescription);
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        WriteObjectsHelper.writeAllProp(s,itemDescription);
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        initInstance();
        ReadObjectsHelper.readAllProp(s, itemDescription);
    }

    private void initInstance() {
        itemDescription = new SimpleStringProperty();
    }

    public String getItemDescription() {
        return itemDescription.get();
    }
    public StringProperty itemDescriptionProperty() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription.set(itemDescription);
    }


}
