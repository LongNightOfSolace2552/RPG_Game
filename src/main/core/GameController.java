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
    
    
}
