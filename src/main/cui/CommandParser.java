package main.cui;

import java.util.Scanner;
import main.exceptions.InvalidCommandException;

// Parses player input, including menu selections and the dungeon exit command ('x').
public class CommandParser {
    private static final int MIN_MENU_CHOICE = 1;
    private static final int MAX_MENU_CHOICE = 5;

    private final Scanner scanner = new Scanner(System.in);

    public int readMenuChoice() throws InvalidCommandException {
        return readChoiceInRange(MIN_MENU_CHOICE, MAX_MENU_CHOICE);
    }

    // Reads a single integer choice, validated to be within [min, max].
    // Used for the main menu and for sub-menus (node selection, item
    // selection) that have their own valid ranges.
    public int readChoiceInRange(int min, int max) throws InvalidCommandException {
        String input = scanner.nextLine().trim();

        int choice;
        try {
            choice = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new InvalidCommandException("'" + input + "' is not a valid option.");
        }

        if (choice < min || choice > max) {
            throw new InvalidCommandException("Choose a number between " + min + " and " + max + ".");
        }

        return choice;
    }

    public String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
}
