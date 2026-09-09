/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.domain.combat;

/**
 *
 * @author kyawt
 */
public class CombatResult {
 
    private final boolean won;
    private final String statLostName;
    private final int statLostAmount;
    
    public CombatResult(boolean won, String statLostName, int statLostAmount) {
        this.won = won;
        this.statLostName = statLostName;
        this.statLostAmount = statLostAmount;
    }

    public boolean isWon() {
        return won;
    }

    public boolean hasStatLoss() {
        return statLostName != null;
    }

    public String getStatLostName() {
        return statLostName;
    }

    public int getStatLostAmount() {
        return statLostAmount;
    }  
    
}
