package main.services.combat;

import main.domain.combat.BattleResult;
import main.domain.combat.Enemy;
import main.domain.player.Player;

// Interface for battle resolution: threshold-based win chance, dodge chance,
// weapon ability activation rolls, and Power Level calculation. Produces the
// ordered list of BattleActions that CombatSequencer plays out.
public interface CombatService {
    BattleResult fight(Player player, Enemy enemy);
}
