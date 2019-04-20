package indi.wiio.info.character;

import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import indi.wiio.utils.ReadObjectsHelper;
import indi.wiio.utils.WriteObjectsHelper;

import javax.naming.Binding;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Skill  implements Serializable {
    private transient StringProperty mark = new SimpleStringProperty();
    private transient StringProperty name = new SimpleStringProperty();
    private transient IntegerProperty profession = new SimpleIntegerProperty();

    private transient IntegerProperty basic = new SimpleIntegerProperty();
    private transient IntegerProperty grow = new SimpleIntegerProperty();
    private transient IntegerProperty professionPoint = new SimpleIntegerProperty();
    private transient IntegerProperty interestPoint = new SimpleIntegerProperty();
    private transient IntegerProperty total = new SimpleIntegerProperty();
    private transient IntegerProperty total2 = new SimpleIntegerProperty();
    private transient IntegerProperty total3 = new SimpleIntegerProperty();

    public Skill(String name, int basic){
        this.name.set(name);
        this.basic.set(basic);
        grow.set(0);
        professionPoint.set(0);
        interestPoint.set(0);
        total.bind(Bindings.add(Bindings.add(Bindings.add(this.basic, grow),professionPoint),interestPoint));
        total2.bind(total.divide(2));
        total3.bind(total.divide(5));
    }


    private void writeObject(ObjectOutputStream s) throws IOException {
        WriteObjectsHelper.writeAllProp(s,mark, name,profession,basic, grow, professionPoint, interestPoint,total);
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        initInstance();
        ReadObjectsHelper.readAllProp(s, mark, name,profession,basic, grow, professionPoint, interestPoint,total);

        total.bind(Bindings.add(Bindings.add(Bindings.add(this.basic, grow),professionPoint),interestPoint));
        total2.bind(total.divide(2));
        total3.bind(total.divide(5));
    }

    private void initInstance() {
        mark = new SimpleStringProperty();
        name = new SimpleStringProperty();
        profession = new SimpleIntegerProperty();

        basic = new SimpleIntegerProperty();
        grow = new SimpleIntegerProperty();
        professionPoint = new SimpleIntegerProperty();
        interestPoint = new SimpleIntegerProperty();
        total = new SimpleIntegerProperty();
        total2 = new SimpleIntegerProperty();
        total3 = new SimpleIntegerProperty();


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

    public int getProfession() {
        return profession.get();
    }

    public IntegerProperty professionProperty() {
        return profession;
    }

    public void setProfession(int profession) {
        this.profession.set(profession);
    }

    public int getBasic() {
        return basic.get();
    }

    public IntegerProperty basicProperty() {
        return basic;
    }

    public void setBasic(int basic) {
        this.basic.set(basic);
    }

    public int getGrow() {
        return grow.get();
    }

    public IntegerProperty growProperty() {
        return grow;
    }

    public void setGrow(int grow) {
        this.grow.set(grow);
    }

    public int getProfessionPoint() {
        return professionPoint.get();
    }

    public IntegerProperty professionPointProperty() {
        return professionPoint;
    }

    public void setProfessionPoint(int professionPoint) {
        this.professionPoint.set(professionPoint);
    }

    public int getInterestPoint() {
        return interestPoint.get();
    }

    public IntegerProperty interestPointProperty() {
        return interestPoint;
    }

    public void setInterestPoint(int interestPoint) {
        this.interestPoint.set(interestPoint);
    }

    public int getTotal() {
        return total.get();
    }

    public IntegerProperty totalProperty() {
        return total;
    }

    public void setTotal(int total) {
        this.total.set(total);
    }

    public String getMark() {
        return mark.get();
    }

    public StringProperty markProperty() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark.set(mark);
    }

    public int getTotal2() {
        return total2.get();
    }

    public IntegerProperty total2Property() {
        return total2;
    }

    public void setTotal2(int total2) {
        this.total2.set(total2);
    }

    public int getTotal3() {
        return total3.get();
    }

    public IntegerProperty total3Property() {
        return total3;
    }

    public void setTotal3(int total3) {
        this.total3.set(total3);
    }
}
