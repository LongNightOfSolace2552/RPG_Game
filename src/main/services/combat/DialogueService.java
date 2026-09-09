package main.services.combat;

import java.util.List;
import main.domain.combat.DialogueTemplate.StatState;

// Interface for selecting a dialogue template based on the stat-ratio difference between player and enemy, and forming the resulting sentence.
public interface DialogueService {
    List<String> buildBattleLines(StatState statState, String playerName, String enemyName);
}
