package main.core;

import java.util.ArrayList;
import java.util.List;
import main.cui.CommandParser;
import main.cui.ConsoleRenderer;
import main.domain.items.Item;
import main.domain.player.Player;
import main.domain.world.Node;
import main.exceptions.InvalidCommandException;
import main.exceptions.ItemNotFoundException;
import main.exceptions.SaveDataException;
import main.services.travel.TravelService;

// Orchestrates services and CUI rendering in response to player actions.
public class GameController {
    private final ConsoleRenderer consoleRenderer;
    private final CommandParser commandParser;
    private final SaveManager saveManager;
    private final TravelService travelService;

    public GameController(ConsoleRenderer consoleRenderer, CommandParser commandParser,
                           SaveManager saveManager, TravelService travelService) {
        this.consoleRenderer = consoleRenderer;
        this.commandParser = commandParser;
        this.saveManager = saveManager;
        this.travelService = travelService;
    }

    // Renders the status header for the player's current node. Falls back
    // to a plain message if the player's saved node id no longer exists
    // in the node data (e.g. node data changed since the save was made).
    public void renderHeader(Player player) {
        try {
            Node currentNode = travelService.findNode(player.getCurrentNodeId());
            consoleRenderer.printHeader(player, currentNode);
        } catch (ItemNotFoundException e) {
            consoleRenderer.printError("Current node '" + player.getCurrentNodeId() + "' is unknown: " + e.getMessage());
        }
    }

    // Returns true if the game loop should stop after this action.
    public boolean handle(int choice, Player player) throws InvalidCommandException {
        switch (choice) {
            case 1:
                handleTravel(player);
                return false;
            case 2:
                handleNode(player);
                return false;
            case 3:
                handleDungeon(player);
                return false;
            case 4:
                handleInventory(player);
                return false;
            case 5:
                return saveAndQuit(player);
            default:
                // CommandParser already restricts choice to a valid range,
                // so this should be unreachable.
                consoleRenderer.printError("Unknown option: " + choice);
                return false;
        }
    }

    private void handleTravel(Player player) throws InvalidCommandException {
        List<Node> nodes = travelService.listNodes();
        if (nodes.isEmpty()) {
            consoleRenderer.printMessage("There is nowhere to travel to.");
            return;
        }

        List<Boolean> locked = new ArrayList<>();
        for (Node node : nodes) {
            locked.add(travelService.isLocked(node, player));
        }

        consoleRenderer.printNodeList(nodes, locked);
        int choice = commandParser.readChoiceInRange(0, nodes.size());
        if (choice == 0) {
            consoleRenderer.printMessage("Travel cancelled.");
            return;
        }

        Node destination = nodes.get(choice - 1);
        if (locked.get(choice - 1)) {
            consoleRenderer.printError(destination.getName() + " is locked. Defeat the previous node's boss first.");
            return;
        }
        
        //if player is already at node location
        if(destination.getId().equals(player.getCurrentNodeId())) {
            consoleRenderer.printMessage("You are already at " + destination.getName() + ".");
            return;
        }

        try {
            travelService.travelTo(player, destination.getId());
            consoleRenderer.printMessage("You travel to " + destination.getName() + ".");
        } catch (ItemNotFoundException e) {
            consoleRenderer.printError(e.getMessage());
        }
    }

    private void handleNode(Player player) {
        try {
            Node currentNode = travelService.findNode(player.getCurrentNodeId());
            boolean bossDefeated = player.hasDefeatedBoss(currentNode.getId());
            consoleRenderer.printNodeInfo(currentNode, bossDefeated);
        } catch (ItemNotFoundException e) {
            consoleRenderer.printError(e.getMessage());
        }
    }

    // Placeholder boss encounter: real enemy stats/combat aren't implemented
    // yet, but the lock/unlock flow itself needs to be testable now, so
    // this offers a simple attempt/retreat choice that marks the boss
    // defeated on success.
    private void handleDungeon(Player player) throws InvalidCommandException {
        Node currentNode;
        try {
            currentNode = travelService.findNode(player.getCurrentNodeId());
        } catch (ItemNotFoundException e) {
            consoleRenderer.printError(e.getMessage());
            return;
        }

        if (!currentNode.hasBoss()) {
            consoleRenderer.printMessage("This node has no boss. There is nothing to fight here yet.");
            return;
        }

        if (player.hasDefeatedBoss(currentNode.getId())) {
            consoleRenderer.printMessage("You have already defeated this node's boss.");
            return;
        }

        consoleRenderer.printMessage(
                "A boss blocks your path. (Enemy combat is not implemented yet - this is a placeholder encounter.)");
        consoleRenderer.printMessage("[1]: Attempt to defeat the boss   [2]: Retreat");
        int choice = commandParser.readChoiceInRange(1, 2);

        if (choice == 1) {
            player.markBossDefeated(currentNode.getId());
            consoleRenderer.printMessage("You defeated the boss! The path onward is now unlocked.");
        } else {
            consoleRenderer.printMessage("You retreat.");
        }
    }

    private void handleInventory(Player player) throws InvalidCommandException {
        boolean back = false;
        while (!back) {
            consoleRenderer.printStats(player);
            consoleRenderer.printInventoryMenu();
            int choice = commandParser.readChoiceInRange(1, 3);
            switch (choice) {
                case 1:
                    handleEquip(player);
                    break;
                case 2:
                    handleUnequip(player);
                    break;
                case 3:
                    back = true;
                    break;
                default:
                    // readChoiceInRange already restricts to 1-3.
                    break;
            }
        }
    }

    private void handleEquip(Player player) throws InvalidCommandException {
        List<Item> inventory = player.getInventory();
        if (inventory.isEmpty()) {
            consoleRenderer.printMessage("Your inventory is empty.");
            return;
        }

        consoleRenderer.printItemList(inventory);
        int choice = commandParser.readChoiceInRange(0, inventory.size());
        if (choice == 0) {
            return;
        }

        Item chosen = inventory.get(choice - 1);
        if (player.equip(chosen)) {
            consoleRenderer.printMessage("Equipped " + chosen.getName() + ".");
        } else {
            consoleRenderer.printError("Could not equip " + chosen.getName() + ".");
        }
    }

    private void handleUnequip(Player player) {
        if (!player.hasEquippedItem()) {
            consoleRenderer.printMessage("You have nothing equipped.");
            return;
        }
        String name = player.getEquippedItem().getName();
        player.unequip();
        consoleRenderer.printMessage("Unequipped " + name + ".");
    }

    private boolean saveAndQuit(Player player) {
        try {
            saveManager.savePlayer(player);
            consoleRenderer.printMessage("Progress saved. Goodbye, " + player.getName() + "!");
        } catch (SaveDataException e) {
            consoleRenderer.printError("Could not save your progress: " + e.getMessage());
        }
        return true;
    }
}
