package main.services.combat;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import main.domain.combat.DialogueTemplate;
import main.domain.combat.DialogueTemplate.StatState;


public class DialogueServiceImpl {
    private final Map<StatState, List<DialogueTemplate>> templatesByState;

    public DialogueServiceImpl(Map<StatState, List<DialogueTemplate>> templatesByState) {
        this.templatesByState = templatesByState;
    }

    // Returns arraylist of the full ready to output dialogues with the respective enemy and player name
    public List<String> buildBattleLines(StatState statState, String playerName, String enemyName) {
        List<DialogueTemplate> templates = templatesByState.get(statState);
        List<String> lines = new ArrayList<>();
        for (DialogueTemplate template : templates) {
            lines.add(template.format(playerName, enemyName));
        }
        return lines;
    }
}
