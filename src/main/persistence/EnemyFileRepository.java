/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.persistence;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import main.domain.combat.Boss;
import main.domain.combat.Enemy;
import main.domain.combat.EnemyLoot;
import main.exceptions.DataLoadException;

/**
 *
 * @author kyawt
 */
public class EnemyFileRepository {
    private static final String ENEMIES_PATH="data/enemies.txt";
    private static final String FIELD_SEPARATOR="\\|";
    private static final String NO_VALUE_MARKER="-";
    private static final String MONSTER_TYPE="MONSTER";
    private static final String BOSS_TYPE="BOSS";
    
    private final FileReaderUtil fileReaderUtil;
    
    
    public EnemyFileRepository(FileReaderUtil fileReaderUtil){
        this.fileReaderUtil=fileReaderUtil;
    }
    
    // Read rows in enemies.txt and put rows that are MONSTER type in arraylists
    // before putting them into hashmap. Hashmap contains key, which is the nodeId, 
    // and the key is linked to the respective arraylist. 
    // New arraylist is created if the nodeId does not exist in the hashmap. 
    public Map<String, List<Enemy>> loadEnemyPools() throws DataLoadException {
        Map<String, List<Enemy>> enemyPools = new HashMap<>();
        for (String[] fields : readDataRows()) {
            if (!isType(fields, MONSTER_TYPE)) {
                continue;
            }
            String nodeId = fields[0].trim();
            enemyPools.computeIfAbsent(nodeId, key -> new ArrayList<>()).add(buildEnemy(fields));
        }
        return enemyPools;
    }

    // Hashmap bosses is created. The keys of the hashmap are nodeIds and the boss 
    // objects are created per key.
    public Map<String, Boss> loadBosses() throws DataLoadException {
        Map<String, Boss> bosses = new HashMap<>();
        for (String[] fields : readDataRows()) {
            if (!isType(fields, BOSS_TYPE)) {
                continue;
            }
            String nodeId = fields[0].trim();
            String id = fields[1].trim();
            String name = fields[3].trim();
            int strengthThreshold = parseInt(fields[4], id);
            int magicThreshold = parseInt(fields[5], id);
            bosses.put(nodeId, new Boss(id, name, strengthThreshold, magicThreshold));
        }
        return bosses;
    }

    // Hashmap lootTable is created. The lootTable has nodeId and id as the key 
    // and EnemyLoot object associated to it. Same slime enemy can be in different 
    // nodeId but have entirely different lootdrop chances.
    public Map<String, EnemyLoot> loadLootTable() throws DataLoadException {
        Map<String, EnemyLoot> lootTable = new HashMap<>();
        for (String[] fields : readDataRows()) {
            if (!isType(fields, MONSTER_TYPE)) {
                continue;
            }
            String nodeId = fields[0].trim();
            String id = fields[1].trim();
            lootTable.put(lootKey(nodeId, id), buildLoot(fields, id));
        }
        return lootTable;
    }

    public static String lootKey(String nodeId, String enemyId) {
        return nodeId + "|" + enemyId;
    }

    private boolean isType(String[] fields, String type) {
        return fields[2].trim().toUpperCase().equals(type);
    }

    // Builds an Enemy object 
    private Enemy buildEnemy(String[] fields) throws DataLoadException {
        String id = fields[1].trim();
        String name = fields[3].trim();
        int strengthThreshold = parseInt(fields[4], id);
        int magicThreshold = parseInt(fields[5], id);
        return new Enemy(id, name, strengthThreshold, magicThreshold);
    }

    // Looks at the loot field of the data row and builds an EnemyLoot object
    // The field is called itemDropField, and if the field is -, null is assigned
    // to the itemDropId. 
    private EnemyLoot buildLoot(String[] fields, String enemyId) throws DataLoadException {
        double statDropChance = parseChance(fields[6], enemyId);
        int statDropMin = parseInt(fields[7], enemyId);
        int statDropMax = parseInt(fields[8], enemyId);
        double itemDropChance = parseChance(fields[9], enemyId);
        String itemDropField = fields[10].trim();
        
        String itemDropId;
        if (itemDropField.equals(NO_VALUE_MARKER)){
            itemDropId=null;
        }
        else{
            itemDropId=itemDropField;
        }
        
        return new EnemyLoot(statDropChance, statDropMin, statDropMax, itemDropChance, itemDropId);
    }

    private double parseChance(String field, String enemyId) throws DataLoadException {
        try {
            return Double.parseDouble(field.trim());
        } catch (NumberFormatException e) {
            throw new DataLoadException("Enemy " + enemyId + " has a non-numeric drop chance: " + field, e);
        }
    }

    private int parseInt(String field, String enemyId) throws DataLoadException {
        try {
            return Integer.parseInt(field.trim());
        } catch (NumberFormatException e) {
            throw new DataLoadException("Enemy " + enemyId + " has a non-numeric stat: " + field, e);
        }
    }

    private List<String[]> readDataRows() throws DataLoadException {
        List<String[]> rows = new ArrayList<>();
        for (String line : fileReaderUtil.readLines(ENEMIES_PATH)) {
            String trimmed = line.trim();
            if (trimmed.isEmpty() || trimmed.startsWith("#")) {
                continue;
            }
            String[] fields = trimmed.split(FIELD_SEPARATOR, 11);
            if (fields.length != 11) {
                throw new DataLoadException("Malformed line in " + ENEMIES_PATH + ": " + trimmed);
            }
            rows.add(fields);
        }
        return rows;
    }
}

