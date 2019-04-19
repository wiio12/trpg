package info.character;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import utils.ReadObjectsHelper;
import utils.WriteObjectsHelper;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Experience implements Serializable {
    private transient StringProperty module;
    private transient StringProperty description;

    private Experience() {
        module = new SimpleStringProperty();
        description = new SimpleStringProperty();
    }

    public Experience(String module, String description) {
        this();
        this.setModule(module);
        this.setDescription(description);
    }

    private void writeObject(ObjectOutputStream s) throws IOException {
        WriteObjectsHelper.writeAllProp(s,module, description);
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        initInstance();
        ReadObjectsHelper.readAllProp(s, module, description);
    }

    private void initInstance() {
        module = new SimpleStringProperty();
        description = new SimpleStringProperty();
    }

    public String getModule() {
        return module.get();
    }

    public StringProperty moduleProperty() {
        return module;
    }

    public void setModule(String module) {
        this.module.set(module);
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
