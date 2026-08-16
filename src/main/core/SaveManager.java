package main.core;

import main.domain.player.Player;
import main.exceptions.SaveDataException;
import main.persistence.PlayerFileRepository;

/* 
Handles save/load via File I/O.
Wraps PlayerFileRepository with the project's fixed save path so the rest
of the game never deals with file paths directly
*/
public class SaveManager {
    //fixed location of save file define by final
    private static final String SAVE_PATH = "data/player_save.txt";
    
    /*
    a field declaration with a reference to an object of type 
    PlayerFileRepository. 
    Context: Dependency injection and Single responsibility principle
    */
    private final PlayerFileRepository playerFileRepository;
    
    public SaveManager(PlayerFileRepository playerFileRepository) {
        this.playerFileRepository = playerFileRepository;
    }
    
    /*
    checks if the save file exists at the given path 
    (not directly just checking the repository)
    */
    public boolean hasSave() {
        return playerFileRepository.saveExists(SAVE_PATH);
    }
    
    /*
    loads player object from the save file 
    (but does not parse, read or convert text to Player)
    */
    public Player LoadPlayer() throws SaveDataException {
        return playerFileRepository.load(SAVE_PATH);
    }
    
    /*
    calls the save() method 
    (just calling it, it does not write, open or serialize the Player)
    */
    public void savePlayer(Player player) throws SaveDataException {
        playerFileRepository.save(player, SAVE_PATH);
    }
}
