package main.domain.items;

// Weapon item; a concrete Item that can carry a WeaponAbility.
public class Weapon extends Item {
    public Weapon(String id, String name, int strengthBonus, int magicBonus, int agilityBonus) {
        super(id, name, strengthBonus, magicBonus, agilityBonus);
    }

    @Override
    public String getItemType() {
        return "Weapon";
    }
}
