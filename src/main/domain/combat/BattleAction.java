package main.domain.combat;

// One resolved step of a battle sequence: the acting side, the target, the matched dialogue keyword, and the final formed sentence to display.
public class BattleAction {
    private final String line;
    
    public BattleAction(String line){
        this.line=line;
    }
    
    public String getLine(){
        return line;
    }
}
