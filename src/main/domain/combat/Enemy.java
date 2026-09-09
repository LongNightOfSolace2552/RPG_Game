package main.domain.combat;

// Base enemy model with its own Strength/Magic/Agility thresholds and stat
// immunities. Defines the behavior shared by regular enemies and bosses;
// subclasses may override parts of it (e.g. Boss).
public class Enemy {
    private final String id;
    private final String name;
    private final int strengthThreshold;
    private final int magicThreshold;
    
    public Enemy(String id, String name, int strengthThreshold, int magicThreshold){
        this.id=id;
        this.name=name;
        this.strengthThreshold=strengthThreshold;
        this.magicThreshold=magicThreshold;
    }
    
    public String getId(){
        return id;
    }
    
    public String getName(){
        return name;
    }
    
    public int getStrengthThreshold(){
        return strengthThreshold;
    }
    
    public int getMagicThreshold(){
        return magicThreshold;
    }
    
    @Override
    public String toString(){
        return name + " (STR " + strengthThreshold + ", MAG " + magicThreshold + ")";
    }
}
