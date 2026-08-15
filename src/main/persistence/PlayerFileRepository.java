package main.persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import main.domain.player.Player;
import main.domain.player.Stats;
import main.exceptions.DataLoadException;
import main.exceptions.SaveDataException;

// Loads and saves player data.
public class PlayerFileRepository {
    //create constants used internally by this class
    private static final String KEY_NAME = "name";
    private static final String KEY_STRENGTH = "strength";
    private static final String KEY_MAGIC = "magic";
    private static final String KEY_AGILITY = "agility";
    private static final String KEY_NODE = "currentNode";
    
    private final FileReaderUtil fileReaderUtil;
    private final FileWriterUtil fileWriterUtil;
    
    public PlayerFileRepository(FileReaderUtil fileReaderUtil, FileWriterUtil fileWriterUtil) {
        this.fileReaderUtil = fileReaderUtil;
        this.fileWriterUtil = fileWriterUtil;
    }
    
    public boolean saveExists(String path) {
        return fileReaderUtil.exists(path);
    }
    
    public Player load(String path) throws SaveDataException {
        if (!saveExists(path)) {
            throw new SaveDataException("No save file found at " + path);
        }
        
        List<String> lines;
        try {
            lines = fileReaderUtil.readLines(path);
        } catch(DataLoadException e) {
            throw new SaveDataException("Could not read save file: " + path, e);
        }
        
        Map<String, String> values = parseKeyValueLines(lines, path);
        
        try {
            String name = requireValue(values, KEY_NAME, path);
            int strength = Integer.parseInt(requireValue(values, KEY_STRENGTH, path));
            int magic = Integer.parseInt(requireValue(values, KEY_MAGIC, path));
            int agility = Integer.parseInt(requireValue(values, KEY_AGILITY, path));
            String currentNode = requireValue(values, KEY_NODE, path);

            Stats stats = new Stats(strength, magic, agility);
            return new Player(name, stats, currentNode);
        } catch (NumberFormatException e) {
            throw new SaveDataException("Save file has a non-numeric stat value: " + path, e);
        } catch (IllegalArgumentException e) {
            throw new SaveDataException("Save file has an invalid stat value: " + path, e);
        }
    }
    
    public void save(Player player, String path) throws SaveDataException {
        Stats stats = player.getStats();
        List<String> lines = new ArrayList<>();
        
        lines.add(KEY_NAME + "=" + player.getName());
        lines.add(KEY_STRENGTH + "=" + stats.getStrength());
        lines.add(KEY_MAGIC + "=" + stats.getMagic());
        lines.add(KEY_AGILITY + "=" + stats.getAgility());
        lines.add(KEY_NODE + "=" + player.getCurrentNodeName());
        
        fileWriterUtil.writeLines(path, lines);
    }
    
    //parseKeyValueLines method
    private Map<String, String> parseKeyValueLines(List<String> lines, String path) throws SaveDataException {
        Map<String, String> values = new HashMap<>();
        
        for(String line : lines) {
            String trimmed = line.trim();
            
            if(trimmed.isEmpty() || trimmed.startsWith("#")) {
                continue;
            }
            
            int separatorIndex = trimmed.indexOf("=");
            if (separatorIndex < 0) {
                throw new SaveDataException("Malformed line save file " + path + ": " + line);
            }
            
            String key = trimmed.substring(0, separatorIndex).trim();
            String value = trimmed.substring(separatorIndex + 1).trim();
            values.put(key, value);
        }
        
        return values;
    }
    
    //requireValue method
    private String requireValue(Map<String, String> values, String key, String path) throws SaveDataException {
        String value = values.get(key);
        if (value == null || value.isEmpty()) {
            throw new SaveDataException("Save file " + path + " is missing required field: " + key);
        }
        return value;
    }
}
