package info.character;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import utils.ReadObjectsHelper;
import utils.WriteObjectsHelper;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Companion implements Serializable {
    private transient StringProperty name;
    private transient StringProperty description;




    private Companion() {
        name = new SimpleStringProperty();
        description = new SimpleStringProperty();
    }

    public Companion(String name, String description) {
        this();
        this.setName(name);
        this.setDescription(description);
    }


    private void writeObject(ObjectOutputStream s) throws IOException {
        WriteObjectsHelper.writeAllProp(s,name, description);
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        initInstance();
        ReadObjectsHelper.readAllProp(s,name, description);
    }

    private void initInstance() {
        name = new SimpleStringProperty();
        description = new SimpleStringProperty();
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

    public String getDescription() {
        return description.get();
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String description) {
        this.description.set(description);
    }




}
