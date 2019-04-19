package info.character;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import utils.ReadObjectsHelper;
import utils.WriteObjectsHelper;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Background implements Serializable {
    private transient StringProperty personalDescription = new SimpleStringProperty();
    private transient StringProperty beliefs = new SimpleStringProperty();
    private transient StringProperty importantPerson = new SimpleStringProperty();
    private transient StringProperty importantPlace = new SimpleStringProperty();
    private transient StringProperty importantThings = new SimpleStringProperty();
    private transient StringProperty speciality = new SimpleStringProperty();
    private transient StringProperty wound = new SimpleStringProperty();
    private transient StringProperty phobia = new SimpleStringProperty();

    public Background(){
        this.personalDescription.set("");
        this.beliefs.set("");
        this.importantPerson.set("");
        this.importantPlace.set("");
        this.importantThings.set("");
        this.speciality.set("");
        this.wound.set("");
        this.phobia.set("");
    }

    public Background(String personalDescription, String beliefs, String importantPerson,
                      String importantPlace, String importantThings, String speciality,
                      String wound, String phobia) {
        this.personalDescription.set(personalDescription);
        this.beliefs.set(beliefs);
        this.importantPerson.set(importantPerson);
        this.importantPlace.set(importantPlace);
        this.importantThings.set(importantThings);
        this.speciality.set(speciality);
        this.wound.set(wound);
        this.phobia.set(phobia);
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        WriteObjectsHelper.writeAllProp(out,personalDescription,beliefs,importantPerson,importantPlace,importantThings,
                speciality, wound,phobia);

    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        initInstance();
        ReadObjectsHelper.readAllProp(in, personalDescription,beliefs,importantPerson,importantPlace,importantThings,
                speciality, wound,phobia);
    }

    private void initInstance() {
        personalDescription = new SimpleStringProperty();
        beliefs = new SimpleStringProperty();
        importantPerson = new SimpleStringProperty();
        importantPlace = new SimpleStringProperty();
        importantThings = new SimpleStringProperty();
        speciality = new SimpleStringProperty();
        wound = new SimpleStringProperty();
        phobia = new SimpleStringProperty();
    }

    public String getPersonalDescription() {
        return personalDescription.get();
    }

    public StringProperty personalDescriptionProperty() {
        return personalDescription;
    }

    public void setPersonalDescription(String personalDescription) {
        this.personalDescription.set(personalDescription);
    }

    public String getBeliefs() {
        return beliefs.get();
    }

    public StringProperty beliefsProperty() {
        return beliefs;
    }

    public void setBeliefs(String beliefs) {
        this.beliefs.set(beliefs);
    }

    public String getImportantPerson() {
        return importantPerson.get();
    }

    public StringProperty importantPersonProperty() {
        return importantPerson;
    }

    public void setImportantPerson(String importantPerson) {
        this.importantPerson.set(importantPerson);
    }

    public String getImportantPlace() {
        return importantPlace.get();
    }

    public StringProperty importantPlaceProperty() {
        return importantPlace;
    }

    public void setImportantPlace(String importantPlace) {
        this.importantPlace.set(importantPlace);
    }

    public String getImportantThings() {
        return importantThings.get();
    }

    public StringProperty importantThingsProperty() {
        return importantThings;
    }

    public void setImportantThings(String importantThings) {
        this.importantThings.set(importantThings);
    }

    public String getSpeciality() {
        return speciality.get();
    }

    public StringProperty specialityProperty() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality.set(speciality);
    }

    public String getWound() {
        return wound.get();
    }

    public StringProperty woundProperty() {
        return wound;
    }

    public void setWound(String wound) {
        this.wound.set(wound);
    }

    public String getPhobia() {
        return phobia.get();
    }

    public StringProperty phobiaProperty() {
        return phobia;
    }

    public void setPhobia(String phobia) {
        this.phobia.set(phobia);
    }
}
