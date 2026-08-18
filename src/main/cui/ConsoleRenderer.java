package main.cui;

import java.util.List;
import main.domain.items.Item;
import main.domain.player.Player;
import main.domain.player.Stats;
import main.domain.world.Node;

/*
prints core screens to the console: player name, current node location, 
dungeon difficulty, and the main menu.
*/
public class ConsoleRenderer {
    public void printHeader(Player player, Node currentNode) {
        System.out.println("=======================================");
        System.out.println("Player: " + player.getName());
        System.out.println("Current Node: " + currentNode.getName());
        System.out.println("Dungeon Difficulty: " + currentNode.getDifficulty());
        System.out.println("=======================================");
    }

    public void printStats(Player player) {
        Stats baseStats = player.getStats();
        Stats effectiveStats = player.getEffectiveStats();

        System.out.println("--- Stats ---");
        System.out.println("Base:      " + baseStats);
        System.out.println("Effective: " + effectiveStats);

        System.out.println("--- Equipped Item ---");
        System.out.println(player.hasEquippedItem() ? player.getEquippedItem() : "(none)");

        System.out.println("--- Inventory ---");
        List<Item> inventory = player.getInventory();
        if (inventory.isEmpty()) {
            System.out.println("(empty)");
        } else {
            for (Item item : inventory) {
                System.out.println("- " + item);
            }
        }
    }

    public void printInventoryMenu() {
        System.out.println();
        System.out.println("[1]: Equip an item");
        System.out.println("[2]: Unequip current item");
        System.out.println("[3]: Back");
        System.out.print("Choose an option: ");
    }

    public void printItemList(List<Item> items) {
        System.out.println("[0]: Cancel");
        for (int i = 0; i < items.size(); i++) {
            System.out.println("[" + (i + 1) + "]: " + items.get(i));
        }
        System.out.print("Choose an item: ");
    }

    public void printNodeList(List<Node> nodes, List<Boolean> locked) {
        System.out.println("[0]: Cancel");
        for (int i = 0; i < nodes.size(); i++) {
            Node node = nodes.get(i);
            String suffix = locked.get(i) ? " (LOCKED)" : "";
            System.out.println("[" + (i + 1) + "]: " + node.getName() + " (Difficulty " + node.getDifficulty() + ")" + suffix);
        }
        System.out.print("Choose a destination: ");
    }

    public void printNodeInfo(Node node, boolean bossDefeated) {
        System.out.println("--- " + node.getName() + " ---");
        System.out.println(node.getLocation().getDescription());
        System.out.println("Difficulty: " + node.getDifficulty());
        if (node.hasBoss()) {
            System.out.println(bossDefeated
                    ? "Boss status: Defeated"
                    : "Boss status: Undefeated - blocks the path onward");
        }
    }

    public void printMessage(String message) {
        System.out.println(message);
    }

    public void printError(String message) {
        System.out.println("[Error] " + message);
    }
}
