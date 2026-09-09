package main.services.combat;

// Interface for battle resolution: threshold-based win chance, dodge chance,
// weapon ability activation rolls, and Power Level calculation. Produces the
// ordered list of BattleActions that CombatSequencer plays out.

import main.domain.combat.CombatResult;
import main.domain.combat.Enemy;
import main.domain.player.Player;

public interface CombatService {
    CombatResult resolveFight(Player player, Enemy enemy);
}
