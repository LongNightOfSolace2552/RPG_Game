package main.domain.combat;

import main.domain.player.Stats;

// A node's boss enemy. A Boss is an Enemy with stronger stats/thresholds and
// boss-specific behavior (e.g. reward or dialogue overrides), so it can be
// used anywhere an Enemy is expected.
public class Boss extends Enemy {
    public Boss(String id, String name, Stats stats, String dropItemId, double dropChance, int statPointReward) {
        super(id, name, stats, dropItemId, dropChance, statPointReward);
    }
}
 