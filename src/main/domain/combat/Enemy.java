package main.domain.combat;

/*import Stats from player since enemies uses the same Stats*/
import main.domain.player.Stats;

/* 
base enemy model with its own Strength/Magic/Agility thresholds and stat
immunities. Defines the behavior shared by regular enemies and bosses;
subclasses may override parts of it (e.g. Boss).
*/
public class Enemy {

    private final String id;
    private final String name;
    private final Stats stats;
    private final String dropItemId; // nullable - no drop if null
    private final double dropChance; // 0.0 - 1.0, only meaningful if dropItemId is set
    private final int statPointReward;

    public Enemy(String id, String name, Stats stats, String dropItemId, double dropChance, int statPointReward) {
        this.id = id;
        this.name = name;
        this.stats = stats;
        this.dropItemId = dropItemId;
        this.dropChance = dropChance;
        this.statPointReward = statPointReward;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Stats getStats() {
        return stats;
    }

    public boolean hasDropItem() {
        return dropItemId != null;
    }

    public String getDropItemId() {
        return dropItemId;
    }

    public double getDropChance() {
        return dropChance;
    }

    public int getStatPointReward() {
        return statPointReward;
    }

    // Delegates to Stats' shared calculation, so an enemy's Power Level is
    // computed the same way as a player's.
    public int getPowerLevel() {
        return stats.getPowerLevel();
    }
}
