package main.core;

import main.cui.ConsoleRenderer;
import main.domain.player.Player;
import main.exceptions.SaveDataException;

// Orchestrates services and CUI rendering in response to player actions.
public class GameController {
    private final ConsoleRenderer consoleRenderer;
    private final SaveManager saveManager;
    
    //constructor
    public GameController(ConsoleRenderer consoleRenderer, SaveManager saveManager) {
        this.consoleRenderer = consoleRenderer;
        this.saveManager = saveManager;
    }
    
    // Returns true if the game loop should stop after this action.
    public boolean handle(int choice, Player player) {
        switch (choice) {
            case 1:
                consoleRenderer.printMessage("Travel is not implemented yet.");
                return false;
            case 2:
                consoleRenderer.printMessage("Node is not implemented yet.");
                return false;
            case 3:
                consoleRenderer.printMessage("Dungeon is not implemented yet.");
                return false;
            case 4:
                consoleRenderer.printStats(player);
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
