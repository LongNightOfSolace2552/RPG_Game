package main.domain.player;
import java.util.ArrayList;
import java.util.List;
import main.domain.items.Item;

// Player model, including core stats (Strength, Magic, Agility), equipped items, and current node location.
public class Player {
    private String name;
    private Stats stats;
    private String currentNodeName;
    private final List<Item> inventory = new ArrayList<>();
    
    //constructor
    public Player(String name, Stats stats, String currentNodeName) {
        this.name = name;
        this.stats = stats;
        this.currentNodeName = currentNodeName;
    }
    
    //get method
    public String getName() {
        return name;
    }
    
    public Stats getStats() {
        return stats;
    }
    
    public String getCurrentNodeName() {
        return currentNodeName;
    }
    
    //set method
    public void setName(String name) {
        this.name = name;
    }
    
    public void setCurrentNodeName() {
        this.currentNodeName = currentNodeName;
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
