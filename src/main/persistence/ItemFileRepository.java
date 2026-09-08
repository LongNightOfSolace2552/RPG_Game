package main.persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import main.domain.items.Accessory;
import main.domain.items.Item;
import main.domain.items.Weapon;
import main.exceptions.DataLoadException;

// Loads items from text files.
public class ItemFileRepository {
    private static final String ITEMS_PATH = "data/items.txt";
    private static final String FIELD_SEPARATOR = "\\|";

    private final FileReaderUtil fileReaderUtil;

    public ItemFileRepository(FileReaderUtil fileReaderUtil) {
        this.fileReaderUtil = fileReaderUtil;
    }

    // items.txt format:
    // id|TYPE|name|strengthBonus|magicBonus|agilityBonus|abilityName|abilityChance|abilityBonusStrength
    // TYPE is WEAPON or ACCESSORY. abilityName "-" means the item has no ability.
    public Map<String, Item> loadItemCatalog() throws DataLoadException {
        Map<String, Item> catalog = new HashMap<>();
        for (String line : readDataLines()) {
            String[] fields = line.split(FIELD_SEPARATOR, 6);
            if (fields.length != 6) {
                throw new DataLoadException("Malformed line in " + ITEMS_PATH + ": " + line);
            }

            String id = fields[0].trim();
            String type = fields[1].trim().toUpperCase();
            String name = fields[2].trim();
            int strengthBonus = parseInt(fields[3], id);
            int magicBonus = parseInt(fields[4], id);
            int agilityBonus = parseInt(fields[5], id);

            Item item;
            switch (type) {
                case "WEAPON":
                    item = new Weapon(id, name, strengthBonus, magicBonus, agilityBonus);
                    break;
                case "ACCESSORY":
                    item = new Accessory(id, name, strengthBonus, magicBonus, agilityBonus);
                    break;
                default:
                    throw new DataLoadException("Unknown item type '" + type + "' for item " + id);
            }

            catalog.put(id, item);
        }
        return catalog;
    }

    private int parseInt(String field, String itemId) throws DataLoadException {
        try {
            return Integer.parseInt(field.trim());
        } catch (NumberFormatException e) {
            throw new DataLoadException("Item " + itemId + " has a non-numeric stat bonus: " + field, e);
        }
    }

    private List<String> readDataLines() throws DataLoadException {
        List<String> result = new ArrayList<>();
        for (String line : fileReaderUtil.readLines(ITEMS_PATH)) {
            String trimmed = line.trim();
            if (trimmed.isEmpty() || trimmed.startsWith("#")) {
                continue;
            }
            result.add(trimmed);
        }
        return result;
    }
}
