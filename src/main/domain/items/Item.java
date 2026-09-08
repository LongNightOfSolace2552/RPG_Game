package main.domain.items;

// Abstract base for anything a player can hold or equip (weapons, rings,
// amulets, etc.). Concrete item types provide their own stat-bonus behavior.
public abstract class Item {
    private final String id;
    private final String name;
    private final int strengthBonus;
    private final int magicBonus;
    private final int agilityBonus;

    protected Item(String id, String name, int strengthBonus, int magicBonus, int agilityBonus) {
        this.id = id;
        this.name = name;
        this.strengthBonus = strengthBonus;
        this.magicBonus = magicBonus;
        this.agilityBonus = agilityBonus;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getStrengthBonus() {
        return strengthBonus;
    }

    public int getMagicBonus() {
        return magicBonus;
    }

    public int getAgilityBonus() {
        return agilityBonus;
    }

    // Each concrete item type reports its own kind (e.g. "Weapon", "Accessory").
    public abstract String getItemType();

    @Override
    public String toString() {
        String base = "[" + getItemType() + "] " + name
                + " (+" + strengthBonus + " STR, +" + magicBonus + " MAG, +" + agilityBonus + " AGI)";
        return base;
    }
}
