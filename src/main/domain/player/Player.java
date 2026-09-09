package main.domain.player;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import main.domain.items.Item;

// Player model, including core stats (Strength, Magic, Agility), equipped items, and current node location.
public class Player {
    private String name;
    private final Stats stats;
    private String currentNodeId;
    private final List<Item> inventory = new ArrayList<>();
    private Item equippedItem;
    private final Set<String> defeatedBossNodeIds = new HashSet<>();
    
    //constructor
    public Player(String name, Stats stats, String currentNodeId) {
        this.name = name;
        this.stats = stats;
        this.currentNodeId = currentNodeId;
    }
    
    //get method
    public String getName() {
        return name;
    }
    
    public Stats getStats() {
        return stats;
    }
    
    public String getCurrentNodeId() {
        return currentNodeId;
    }
    
    public Item getEquippedItem() {
        return equippedItem;
    }
    
    //set method
    public void setName(String name) {
        this.name = name;
    }
    
    public void setCurrentNodeId(String currentNodeId) {
        this.currentNodeId = currentNodeId;
    }
    
    /*
    returns a copy of players inventory, so players can't bypass
    addItem and removeItem to mutate the player's inventory directly
    */
    public List<Item> getInventory() {
        return new ArrayList<>(inventory);
    }
    
    public void addItem(Item item) {
        inventory.add(item);
    }
    
    public boolean removeItem(Item item) {
        return inventory.remove(item);
    }
    
    public boolean hasEquippedItem() {
        return equippedItem != null;
    }
    
    /*
    equips an item from the inventory. Any previously equipped item is
    returned to the inventory first, so only one item is ever equipped
    at a time regardless of type (Weapon or Accessory).
    */
    public boolean equip(Item item) {
        if (!inventory.remove(item)) {
            return false;
        }
        if (equippedItem != null) {
            inventory.add(equippedItem);
        }
        equippedItem = item;
        return true;
    }
    
    //returns the equipped item to the inventory and clears the slot.
    public void unequip() {
        if (equippedItem == null) {
            return;
        }
        inventory.add(equippedItem);
        equippedItem = null;
    }

    /*
    base stats plus the equipped item's bonuses, if any. Returns a new
    Stats instance - never mutates the player's base stats.
    */
    public Stats getEffectiveStats() {
        if (equippedItem == null) {
            return new Stats(stats.getStrength(), stats.getMagic(), stats.getAgility());
        }
        return new Stats(
                stats.getStrength() + equippedItem.getStrengthBonus(),
                stats.getMagic() + equippedItem.getMagicBonus(),
                stats.getAgility() + equippedItem.getAgilityBonus());
    }

    public void markBossDefeated(String nodeId) {
        defeatedBossNodeIds.add(nodeId);
    }

    public boolean hasDefeatedBoss(String nodeId) {
        return defeatedBossNodeIds.contains(nodeId);
    }

    //returns a copy so callers can't mutate defeat progress directly.
    public Set<String> getDefeatedBossNodeIds() {
        return new HashSet<>(defeatedBossNodeIds);
    }
}
