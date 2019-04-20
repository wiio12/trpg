package indi.wiio.info.character;

import java.io.Serializable;

public enum SkillType implements Serializable {
    Accountancy("会计学",5),
    Law("法律",5),
    Anthropology("人类学",1),
    Library("图书馆使用",20),
    Pricing("估价",5),
    Hearing("聆听",20),
    Locksmith("锁匠",1),
    Archeology("考古学",1),
    Skill1("技艺：",5),
    Skill2("技艺：",5),
    Skill3("技艺：",5),
    MechanicalMaintenance("机械维修",10),
    Medicine("医学",1),
    NaturalHistory("博物学",10),
    Seduction("魅惑",15),
    Pilot("领航",10),
    Climbing("攀爬",20),
    Mystery("神秘学",5),
    Computer("计算机使用Ω",5),
    OptHeavyMach("操作重型机械",1),
    Credit("信用评级",0),
    Persuasion("说服",10),
    Cthulhu("克苏鲁神话",0),
    Steer("驾驶",1),
    Dressing("乔装",5),
    Psychoanalysis("精神分析",1),
    Dodge("闪避",0),
    Psychology("心理学",10),
    DriveCar("汽车驾驶",20),
    Riding("骑术",5),
    ElectricalMaintenance("电器维修",10),
    Science1("科学：",1),
    Science2("科学：",1),
    Science3("科学：",1),
    Electrical("电子学Ω",1),
    Conversation("话术",5),
    Fighting1("格斗：",0),
    Fighting2("格斗：",0),
    Fighting3("格斗：",0),
    GoodHand("妙手",10),
    Investigation("侦查",25),
    Sneaking("潜行",20),
    Shooting1("射击：", 0),
    Shooting2("射击：",0),
    Shooting3("射击：",0),
    Survival1("生存：",10),
    Survival2("生存：",10),
    Swimming("游泳",20),
    FirstAid("急救",30),
    Throwing("投掷",20),
    History("历史",5),
    Tracking("追踪",10),
    Intimidate("恐吓",15),
    Rare("罕见",0),
    Jump("跳跃",20),
    Knowledge("学问",1),
    Language1("语言：",1),
    Language2("语言：",1),
    Language3("语言：",1),
    MotherTongue("母语：",0);




    private Skill skill;

    SkillType(String name, int basic ){
        skill = new Skill(name, basic);
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

}
