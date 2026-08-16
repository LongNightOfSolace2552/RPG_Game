package main.config;

import main.core.Game;
import main.core.GameController;
import main.core.SaveManager;
import main.cui.CommandParser;
import main.cui.ConsoleRenderer;
import main.cui.MenuRenderer;
import main.persistence.FileReaderUtil;
import main.persistence.FileWriterUtil;
import main.persistence.PlayerFileRepository;

// Dependency wiring for services and repositories.
public class AppConfig {
    /*
    builds every service and its dependencies in one place.
    Classes in the upper inheritance tree only depend on the
    abstractions and collaborator needed
    */
    public Game buildGame() {
        FileReaderUtil fileReaderUtil = new FileReaderUtil();
        FileWriterUtil fileWriterUtil = new FileWriterUtil();
        PlayerFileRepository playerFileRepository = new PlayerFileRepository(fileReaderUtil, fileWriterUtil);
        SaveManager saveManager = new SaveManager(playerFileRepository);

        ConsoleRenderer consoleRenderer = new ConsoleRenderer();
        MenuRenderer menuRenderer = new MenuRenderer();
        CommandParser commandParser = new CommandParser();

        GameController gameController = new GameController(consoleRenderer, saveManager);

        return new Game(consoleRenderer, menuRenderer, commandParser, gameController, saveManager);
    }
}
