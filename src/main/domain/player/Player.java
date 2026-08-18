package main.domain.player;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import main.domain.items.Item;

// Player model, including core stats (Strength, Magic, Agility), equipped items, and current node location.
public class Player {
    private String name;
    private Stats stats;
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
    
    
}
