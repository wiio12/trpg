package indi.wiio.info.character;


import indi.wiio.utils.ReadObjectsHelper;
import indi.wiio.utils.WriteObjectsHelper;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.util.Pair;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.NavigableMap;
import java.util.TreeMap;

public class Asset implements Serializable {
    private transient IntegerProperty credit = new SimpleIntegerProperty();
    private transient StringProperty lifeStandard = new SimpleStringProperty();
    private transient StringProperty currency = new SimpleStringProperty();
    private transient StringProperty cash = new SimpleStringProperty();
    private transient StringProperty assetTotal = new SimpleStringProperty();
    private transient StringProperty consumptionLevel = new SimpleStringProperty();
    private transient StringProperty description = new SimpleStringProperty();

    public Asset(){
        this.credit.set(0);
        this.lifeStandard.set("");
        this.currency.set("");
        this.cash.set("");
        this.assetTotal.set("");
        this.consumptionLevel.set("");
        this.description.set("");

    }

    private void initInstance(){
         credit = new SimpleIntegerProperty();
         lifeStandard = new SimpleStringProperty();
         currency = new SimpleStringProperty();
         cash = new SimpleStringProperty();
         assetTotal = new SimpleStringProperty();
         consumptionLevel = new SimpleStringProperty();
         description = new SimpleStringProperty();
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        WriteObjectsHelper.writeAllProp(out, credit, lifeStandard, currency, cash, assetTotal,
                consumptionLevel, description);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException{
        initInstance();
        ReadObjectsHelper.readAllProp(in, credit, lifeStandard, currency, cash, assetTotal,
                consumptionLevel, description);
    }

    public void setupRelation(){
        StringProperty years = Resources.getResources().getPlayerInfo().yearsProperty();

        Skill skill = Resources.getResources().getSkill("信用评级");
        if(skill != null){
            skill.totalProperty().addListener((observableValue, number, t1) ->
                    credit.set(skill.getTotal()));
            skill.totalProperty().addListener((observableValue, number, t1) ->
                    handleLiveStandard(skill));


            skill.totalProperty().addListener((observableValue, number, t1) -> {
                handleCash(skill, years);
                handleAssetTotal(skill, years);
                handleConsumptionLevel(skill,years);
            });
            System.out.println(years.toString());
            years.addListener((observableValue, s, t1) -> {
                handleCash(skill, years);
                handleAssetTotal(skill, years);
                handleConsumptionLevel(skill,years);
            });
            currency.addListener((observableValue, s, t1) -> {
                handleCash(skill, years);
                handleAssetTotal(skill, years);
                handleConsumptionLevel(skill,years);
            });

        }




    }

    private void handleConsumptionLevel(Skill skill, StringProperty stringProperty){
        int c = skill.getTotal();
        String y = stringProperty.get();

        if(currency.get().equalsIgnoreCase("日元")){
            if(y.equalsIgnoreCase("1920s")){
                NavigableMap<Integer, Integer> map1 = new TreeMap<>();
                map1.put(0,0);
                map1.put(10,2);
                map1.put(50,25);
                map1.put(90,100);
                map1.put(99,500);
                if(c <=0) consumptionLevel.set("0.05");
                else if(c > 99) consumptionLevel.set("2000");
                else {
                    consumptionLevel.set(map1.floorEntry(c).getValue()+"");
                }
            } else {
                NavigableMap<Integer, String> map2 = new TreeMap<>();
                map2.put(0,"0");
                map2.put(10,"2000");
                map2.put(50,"25000");
                map2.put(90,"10万");
                map2.put(99,"50万");
                if(c <=0) consumptionLevel.set("500");
                else if(c > 99) consumptionLevel.set("200万");
                else {
                    consumptionLevel.set(map2.floorEntry(c).getValue());
                }
            }
        }

        if(currency.get().equalsIgnoreCase("人民币")){
            if(y.equalsIgnoreCase("1920s")){
                assetTotal.set("人民币");
            } else {
                NavigableMap<Integer, String> map2 = new TreeMap<>();
                map2.put(0,"0");
                map2.put(10,"100");
                map2.put(50,"1000");
                map2.put(90,"5000");
                map2.put(99,"2万");
                if(c <=0) consumptionLevel.set("50");
                else if(c > 99) consumptionLevel.set("10万");
                else {
                    consumptionLevel.set(map2.floorEntry(c).getValue());
                }
            }
        }

        if(currency.get().equalsIgnoreCase("美元")){
            if(y.equalsIgnoreCase("1920s")){
                NavigableMap<Integer, String> map1 = new TreeMap<>();
                map1.put(0,"0");
                map1.put(10,"2");
                map1.put(50,"10");
                map1.put(90,"50");
                map1.put(99,"250");
                if(c <=0) consumptionLevel.set("0.5");
                else if(c>99) consumptionLevel.set("5000");
                else {
                    consumptionLevel.set(map1.floorEntry(c).getValue());
                }
            } else {
                NavigableMap<Integer, Integer> map2 = new TreeMap<>();
                map2.put(0,0);
                map2.put(10,40);
                map2.put(50,200);
                map2.put(90,1000);
                map2.put(99,5000);
                if(c <=0) consumptionLevel.set("10");
                else if(c > 99) consumptionLevel.set("10万");
                else {
                    consumptionLevel.set(map2.floorEntry(c).getValue()+"");
                }
            }
        }
    }



    private void handleAssetTotal(Skill skill, StringProperty stringProperty){
        int c = skill.getTotal();
        String y = stringProperty.get();

        if(currency.get().equalsIgnoreCase("日元")){
            if(y.equalsIgnoreCase("1920s")){
                NavigableMap<Integer, Double> map1 = new TreeMap<>();
                map1.put(0,0.0);
                map1.put(10,20.0);
                map1.put(50,150.0);
                map1.put(90,0.1);
                map1.put(99,0.6);
                if(c <=0) assetTotal.set("没有");
                else if(c > 99) assetTotal.set("300万");
                else if(c <= 50){
                    assetTotal.set((int)(c * map1.floorEntry(c).getValue())+"");
                }else if(c >50 && c<=99){
                    assetTotal.set((int)(c * map1.floorEntry(c).getValue())+"万");
                }
            } else {
                NavigableMap<Integer, Integer> map2 = new TreeMap<>();
                map2.put(0,0);
                map2.put(10,2);
                map2.put(50,15);
                map2.put(90,100);
                map2.put(99,600);
                if(c <=0) assetTotal.set("1000");
                else if(c > 99) assetTotal.set("300亿");
                else {
                    assetTotal.set((c * map2.floorEntry(c).getValue())+"万");
                }
            }
        }

        if(currency.get().equalsIgnoreCase("人民币")){
            if(y.equalsIgnoreCase("1920s")){
                assetTotal.set("哪来的");
            } else {
                NavigableMap<Integer, Double> map2 = new TreeMap<>();
                map2.put(0,0.0);
                map2.put(10,0.1);
                map2.put(50,0.5);
                map2.put(90,4.0);
                map2.put(99,25.0);
                if(c <=0) assetTotal.set("没有");
                else if(c > 99) assetTotal.set("10亿");
                else {
                    assetTotal.set((c * map2.floorEntry(c).getValue())+"");
                }
            }
        }

        if(currency.get().equalsIgnoreCase("美元")){
            if(y.equalsIgnoreCase("1920s")){
                NavigableMap<Integer, Integer> map1 = new TreeMap<>();
                map1.put(0,0);
                map1.put(10,10);
                map1.put(50,50);
                map1.put(90,500);
                if(c <=0) assetTotal.set("没有");
                else if(c >90 && c<=99) assetTotal.set((c*0.2) + "万");
                else if(c > 99) assetTotal.set("500万");
                else {
                    assetTotal.set((c * map1.floorEntry(c).getValue())+"");
                }
            } else {
                NavigableMap<Integer, Integer> map2 = new TreeMap<>();
                map2.put(0,0);
                map2.put(10,200);
                map2.put(50,1000);
                map2.put(90,1);
                map2.put(99,4);
                if(c <=0) assetTotal.set("没有");
                else if(c > 50 && c < 99)
                    assetTotal.set((c * map2.floorEntry(c).getValue()) + "万");
                else if(c > 99) assetTotal.set("100万");
                else {
                    assetTotal.set((c * map2.floorEntry(c).getValue())+"");
                }
            }
        }
    }

    private void handleCash(Skill skill, StringProperty stringProperty){
        int c = skill.getTotal();
        String y = stringProperty.get();

        if(currency.get().equalsIgnoreCase("日元")){
            if(y.equalsIgnoreCase("1920s")){
                NavigableMap<Integer, Integer> map1 = new TreeMap<>();
                map1.put(0,0);
                map1.put(10,2);
                map1.put(50,4);
                map1.put(90,12);
                map1.put(99,75);
                if(c <=0) cash.set("1");
                else if(c > 99) cash.set("30000");
                else {
                    cash.set((c * map1.floorEntry(c).getValue())+"");
                }
            } else {
                NavigableMap<Integer, Double> map2 = new TreeMap<>();
                map2.put(0,0.0);
                map2.put(10,0.2);
                map2.put(50,0.4);
                map2.put(90,1.2);
                map2.put(99,7.5);
                if(c <=0) cash.set("1000");
                else if(c > 99) cash.set("3000万");
                else {
                    cash.set((int)(c * map2.floorEntry(c).getValue())+"万");
                }
            }
        }

        if(currency.get().equalsIgnoreCase("人民币")){
            if(y.equalsIgnoreCase("1920s")){
                cash.set("1920年");
            } else {
                NavigableMap<Integer, Integer> map2 = new TreeMap<>();
                map2.put(0,0);
                map2.put(10,100);
                map2.put(50,200);
                map2.put(90,500);
                if(c <=0) cash.set("100");
                else if(c >90 && c <= 99) cash.set((int)(c * 0.3) + "万");
                else if(c > 99) cash.set("100万");
                else {
                    cash.set((c * map2.floorEntry(c).getValue())+"");
                }
            }
        }

        if(currency.get().equalsIgnoreCase("美元")){
            if(y.equalsIgnoreCase("1920s")){
                NavigableMap<Integer, Integer> map1 = new TreeMap<>();
                map1.put(0,0);
                map1.put(10,1);
                map1.put(50,2);
                map1.put(90,5);
                map1.put(99,20);
                if(c <=0) cash.set("100");
                else if(c > 99) cash.set("50000");
                else {
                    cash.set((c * map1.floorEntry(c).getValue())+"");
                }
            } else {
                NavigableMap<Integer, Integer> map2 = new TreeMap<>();
                map2.put(0,0);
                map2.put(10,20);
                map2.put(50,40);
                map2.put(90,100);
                map2.put(99,400);
                if(c <=0) cash.set("10");
                else if(c > 99) cash.set("100万");
                else {
                    cash.set((c * map2.floorEntry(c).getValue())+"");
                }
            }
        }

    }

    private void handleLiveStandard(Skill skill){
        NavigableMap<Integer, String> map = new TreeMap<>();
        map.put(0,"身无分文");
        map.put(1,"贫穷");
        map.put(10,"标准");
        map.put(50,"小康");
        map.put(90,"富裕");
        map.put(99,"富豪");

        lifeStandard.set(map.floorEntry(skill.getTotal()).getValue());
    }

    public Asset(int credit, String lifeStandard, String currency, String cash,
                 String assetTotal, String consumptionLevel, String description) {
        this.credit.set(credit);
        this.lifeStandard.set(lifeStandard);
        this.currency.set(currency);
        this.cash.set(cash);
        this.assetTotal.set(assetTotal);
        this.consumptionLevel.set(consumptionLevel);
        this.description.set(description);
    }

    public int getCredit() {
        return credit.get();
    }

    public IntegerProperty creditProperty() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit.set(credit);
    }

    public String getLifeStandard() {
        return lifeStandard.get();
    }

    public StringProperty lifeStandardProperty() {
        return lifeStandard;
    }

    public void setLifeStandard(String lifeStandard) {
        this.lifeStandard.set(lifeStandard);
    }

    public String getCurrency() {
        return currency.get();
    }

    public StringProperty currencyProperty() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency.set(currency);
    }

    public String getCash() {
        return cash.get();
    }

    public StringProperty cashProperty() {
        return cash;
    }

    public void setCash(String cash) {
        this.cash.set(cash);
    }

    public String getAssetTotal() {
        return assetTotal.get();
    }

    public StringProperty assetTotalProperty() {
        return assetTotal;
    }

    public void setAssetTotal(String assetTotal) {
        this.assetTotal.set(assetTotal);
    }

    public String getConsumptionLevel() {
        return consumptionLevel.get();
    }

    public StringProperty consumptionLevelProperty() {
        return consumptionLevel;
    }

    public void setConsumptionLevel(String consumptionLevel) {
        this.consumptionLevel.set(consumptionLevel);
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
