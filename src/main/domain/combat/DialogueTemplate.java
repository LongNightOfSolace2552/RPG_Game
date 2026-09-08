package main.domain.combat;

// A single dialogue template: a keyword (e.g. dodge, heavy_attack), the stat category it relates to (Strength, Magic, Agility), the stat-ratio range it applies to, and the sentence pattern with <player>/<enemy> placeholders.
public class DialogueTemplate {
    
    public enum StatState{
        AHEAD,BEHIND,WAY_AHEAD,AGILITY_CLUTCH
    } 
    
    private final StatState statstates;
    private final String template;
    
    public DialogueTemplate(StatState statstate,String template){
        this.statstates=statstate;
        this.template=template;
    }
    
    public StatState getStatState(){
        return statstates;
    }
    
    public String getTemplate(){
        return template;
    }
    
    public String format(String playerName,String enemyName){
        return template.replace("<player>", playerName).replace("<enemy>", enemyName);
    }
}
