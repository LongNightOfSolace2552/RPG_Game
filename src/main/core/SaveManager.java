package main.core;

/**
 *
 * @author wxyon
 */

import main.domain.player.Player;
import main.exceptions.SaveDataException;
import main.persistence.PlayerFileRepository;

/* 
Handles save/load via File I/O.
Wraps PlayerFileRepository with the project's fixed save path so the rest
of the game never deals with file paths directly
*/
public class SaveManager {
    /* 
    dynamic location of save file define by final, every time when a player
    enters the game they will be prompted with entering a name. It acts as
    a search for the player files, if not recognised then new player file created,
    otherwise access that file.
    */
    private static final String SAVE_DIRECTORY = "data/player_saves/";
    private static final String SAVE_EXTENSION = ".txt";
    
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
    (not directly just checking the repository).
    */
    public boolean hasSave(String profileName) {
        return playerFileRepository.saveExists(pathFor(profileName));
    }
    
    /*
    loads player object from the save file 
    (but does not parse, read or convert text to Player)
    */
    public Player loadPlayer(String profileName) throws SaveDataException {
        return playerFileRepository.load(pathFor(profileName));
    }
    
    /*
    calls the save() method 
    (just calling it, it does not write, open or serialize the Player)
    */
    public void savePlayer(Player player, String profileName) throws SaveDataException {
        playerFileRepository.save(player, pathFor(profileName));
    }
    
    //the path it is going to be save towards (the name of player is the file)
    private String pathFor(String profileName) {
        return SAVE_DIRECTORY + clean_up(profileName) + SAVE_EXTENSION;
    }
    
    /*cleans up the String of inputted text so for
    example: there are no capital letters*/
    private String clean_up(String profileName) {
        /*
        " ^ " this means to match anything that is not one of the characters 
        stated.
        
        accepts: a-zA-Z means any letter, 0-9 is any digit, " _ " is underscore and
        " - " is hyphen
        */
        return profileName.trim().replaceAll("[^a-zA-Z0-9_-]", "_");
    }
}
