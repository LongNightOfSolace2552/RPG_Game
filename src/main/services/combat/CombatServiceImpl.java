package main.services.combat;

import main.domain.combat.CombatResult;
import main.domain.combat.Enemy;
import main.domain.player.Player;
import main.domain.player.Stats;
import main.util.Randomizer;

// Implementation of CombatService.
public class CombatServiceImpl {
    //private static final double BASE_WIN_CHANCE = 0.30;
    //private static final double WIN_CHANCE_PER_STAT_POINT = 0.01;

    private static final double MAGIC_LOSS_CHANCE = 0.45;
    private static final double STRENGTH_LOSS_CHANCE = 0.45;
    private static final double AGILITY_LOSS_CHANCE = 0.1;

    private static final int MIN_STAT_LOSS = 1;
    private static final int MAX_STAT_LOSS = 3;

    private final Randomizer random;
    
    public CombatServiceImpl(Randomizer random){
        this.random=random;
    }
    
    // Retrieves the total stats with the raw stats of the player+the stats of the items he is equipping.
    // There must be a motivation for the user to grind for the stat points to match or have more stat points
    // in each stat type: Strength and Magic, than the Boss to advance through the game.
    // 
    private double calculateWinChance(Player player, Enemy enemy) {
        Stats effectiveStats = player.getEffectiveStats();
        double totalWinChance;
        // Win chance calculated individually for each stat.
        // If user Strength and enemy Strength is the same, 30% win rate
        // If user Magic and enemy Magic is the same, 30% win rate
        // So total win rate would be 100%
        // Every Strength stat the user is lacking, -2% of the 30%
        // Every Magic stat the user is lacking, -2% of the 30%,
        // If user Strength is 10 and enemy is 15, win rate=30-(5*2)=20%
        // If user Magic is 30 and enemy is only 10, win rate=30+20=50%
        // Total win rate = 70%
        // If user Strength 100 and enemy is 10, win rate=30+90=120,
        // Since over 120, it is capped to win rate=50%
        // If user Magic 15 and enemhy is 10, win rate=30+5=35%
        // So total win rate=85%
        double strengthWinChance;
        int strengthDiff=effectiveStats.getStrength()-enemy.getStrengthThreshold();
        if (strengthDiff==0){
            strengthWinChance=0.3;
        }
        else if (strengthDiff<0){
            strengthWinChance=0.3+strengthDiff*0.02;
            if (strengthWinChance<0){
                strengthWinChance=0;
            }
        }
        else{
            strengthWinChance=0.3+strengthDiff*0.01;
            if (strengthWinChance>0.5){
                strengthWinChance=0.5;
            }
        }
        
        double magicWinChance;
        int magicDiff=effectiveStats.getMagic()-enemy.getMagicThreshold();
        if (magicDiff==0){
            magicWinChance=0.3;
        }
        else if (magicDiff<0){
            magicWinChance=0.3+magicDiff*0.02;
        }
        else{
            magicWinChance=0.3+magicDiff*0.01;
            if (magicWinChance<0){
                magicWinChance=0;
            }
        }
        totalWinChance=strengthWinChance+magicWinChance;
        return totalWinChance;
    }
    
    private CombatResult applyStatLoss(Player player) {
        double roll = random.nextDouble();
        int lossAmount = random.nextInt(MAX_STAT_LOSS - MIN_STAT_LOSS + 1) + MIN_STAT_LOSS;
        Stats stats = player.getStats();

        if (roll < MAGIC_LOSS_CHANCE) {
            int actualLoss = Math.min(lossAmount, stats.getMagic());
            stats.setMagic(stats.getMagic() - actualLoss);
            return new CombatResult(false, "Magic", actualLoss);
        }
        if (roll < MAGIC_LOSS_CHANCE + STRENGTH_LOSS_CHANCE) {
            int actualLoss = Math.min(lossAmount, stats.getStrength());
            stats.setStrength(stats.getStrength() - actualLoss);
            return new CombatResult(false, "Strength", actualLoss);
        }
        if (roll < MAGIC_LOSS_CHANCE + STRENGTH_LOSS_CHANCE + AGILITY_LOSS_CHANCE) {
            int actualLoss = Math.min(lossAmount, stats.getAgility());
            stats.setAgility(stats.getAgility() - actualLoss);
            return new CombatResult(false, "Agility", actualLoss);
        }
        return new CombatResult(false, null, 0);
    }
    
    public CombatResult resolveFight(Player player, Enemy enemy) {
        double winChance = calculateWinChance(player, enemy);
        boolean won = random.nextDouble() < winChance;

        if (won) {
            return new CombatResult(true, null, 0);
        }
        return applyStatLoss(player);
    }
}
