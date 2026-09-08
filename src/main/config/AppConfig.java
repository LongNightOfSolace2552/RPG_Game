package main.config;

import java.util.List;
import java.util.Map;
import main.core.Game;
import main.core.GameController;
import main.core.SaveManager;
import main.cui.CommandParser;
import main.cui.ConsoleRenderer;
import main.cui.MenuRenderer;
import main.domain.items.Item;
import main.domain.world.Node;
import main.exceptions.DataLoadException;
import main.persistence.FileReaderUtil;
import main.persistence.FileWriterUtil;
import main.persistence.ItemFileRepository;
import main.persistence.NodeFileRepository;
import main.persistence.PlayerFileRepository;
import main.services.travel.TravelService;
import main.services.travel.TravelServiceImpl;

// Dependency wiring for services and repositories.
public class AppConfig {
    /*
    builds every service and its dependencies in one place.
    Classes in the upper inheritance tree only depend on the
    abstractions and collaborator needed
    */
    public Game buildGame() throws DataLoadException {
        FileReaderUtil fileReaderUtil = new FileReaderUtil();
        FileWriterUtil fileWriterUtil = new FileWriterUtil();

        ItemFileRepository itemFileRepository = new ItemFileRepository(fileReaderUtil);
        Map<String, Item> itemCatalog = itemFileRepository.loadItemCatalog();

        PlayerFileRepository playerFileRepository =
                new PlayerFileRepository(fileReaderUtil, fileWriterUtil, itemCatalog);
        SaveManager saveManager = new SaveManager(playerFileRepository);

        NodeFileRepository nodeFileRepository = new NodeFileRepository(fileReaderUtil);
        List<Node> nodes = nodeFileRepository.loadNodes();
        TravelService travelService = new TravelServiceImpl(nodes);

        ConsoleRenderer consoleRenderer = new ConsoleRenderer();
        MenuRenderer menuRenderer = new MenuRenderer();
        CommandParser commandParser = new CommandParser();

        GameController gameController = new GameController(consoleRenderer, commandParser, saveManager, travelService);

        return new Game(consoleRenderer, menuRenderer, commandParser, gameController, saveManager, itemCatalog);
    }
}
