package main;

import main.config.AppConfig;
import main.core.Game;

// Main entry point for the RPG game.
public class App {
    public static void main(String[] args) {
        AppConfig appConfig = new AppConfig();
        Game game = appConfig.buildGame();
        
        game.start();
    }
}
