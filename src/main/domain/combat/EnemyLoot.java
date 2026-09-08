package main.domain.combat;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author kyawt
 */
public class EnemyLoot {
    private final double statDropChance;
    private final int minStatDrop;
    private final int maxStatDrop;
    private final double itemDropChance;
    private final String itemID;
    
    public EnemyLoot(double statDropChance,int minStatDrop,int maxStatDrop,double itemDropChance,String itemID){
        this.statDropChance=statDropChance;
        this.maxStatDrop=maxStatDrop;
        this.minStatDrop=minStatDrop;
        this.itemDropChance=itemDropChance;
        this.itemID=itemID;
    }
    
    public double getStatDropChance(){
        return statDropChance;
    }
    
    public int getMaxStatDrop(){
        return maxStatDrop;
    }
    
    public int getMinStatDrop(){
        return minStatDrop;
    }
    
    public double getItemDropChance(){
        return itemDropChance;
    }
    
    public String getItemID(){
        return itemID;
    }
    
    public boolean dropItem(){
        return itemID!=null;
    }
}
