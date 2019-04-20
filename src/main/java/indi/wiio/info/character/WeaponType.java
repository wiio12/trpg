package indi.wiio.info.character;

import java.io.Serializable;

public enum WeaponType implements Serializable {
    BowAndArrow(new Weapon("", "弓箭","弓术",0,"1D6+半DB",
            "30米","X","1","1","97")),
    KnuckleDusters(new Weapon("", "黄铜指虎","斗殴",0,"1D3+1+DB",
            "接触","X","1","-","-")),
    LongWhip(new Weapon("", "长鞭","鞭子",0,"1D3+半DB",
            "3米","X","1","-","-")),
    Torch(new Weapon("", "燃烧的火把","斗殴",0,"1D6+燃烧",
            "接触","X","1","-","-"));

    private Weapon weapon;

    WeaponType(Weapon weapon) {
        this.weapon = weapon;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }
}
