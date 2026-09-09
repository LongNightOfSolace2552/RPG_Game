package main.domain.combat;

import java.util.List;
import java.util.Map;
import main.util.Randomizer;

// The single infinite dungeon; draws its active enemy pool from the player's current node until the player exits.
public class Dungeon {
    
    private final Randomizer random;
    private final Map<String,List<Enemy>> enemyPool;
    private final String nodeId;
    
    public Dungeon(Randomizer random,Map<String,List<Enemy>> enemyPool, String nodeId){
        this.random=random;
        this.enemyPool=enemyPool;
        this.nodeId=nodeId;
    }
    
    public String getNodeId(){
        return nodeId;
    }
    
    public Enemy nextEnemy(){
        int index=random.nextInt(3);
        List<Enemy> enemies=enemyPool.get(nodeId);
        return enemies.get(index);
    }
}
