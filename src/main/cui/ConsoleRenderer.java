package main.cui;

import main.domain.items.Item;
import main.domain.player.Player;
import main.domain.player.Stats;

// Prints core screens to the console: player name, current node location, dungeon difficulty, and the main menu.
public class ConsoleRenderer {
    public void printHeader(Player player) {
        System.out.println("=======================================");
        System.out.println("Player: " + player.getName());
        System.out.println("Current Node: " + player.getCurrentNodeName());
        System.out.println("Dungeon Difficulty: N/A");
        System.out.println("=======================================");
    }

    public void printStats(Player player) {
        Stats stats = player.getStats();
        System.out.println("--- Stats ---");
        System.out.println(stats);

        System.out.println("--- Inventory ---");
        if (player.getInventory().isEmpty()) {
            System.out.println("(empty)");
        } else {
            for (Item item : player.getInventory()) {
                System.out.println("- " + item);
            }
        }
    }

    public void printMessage(String message) {
        System.out.println(message);
    }

    public void printError(String message) {
        System.out.println("[Error] " + message);
    }
}
