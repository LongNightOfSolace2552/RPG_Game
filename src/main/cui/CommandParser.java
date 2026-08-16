package main.cui;

import java.util.Scanner;
import main.exceptions.InvalidCommandException;

// Parses player input, including menu selections and the dungeon exit command ('x').
public class CommandParser {
    private static final int MIN_MENU_CHOICE = 1;
    private static final int MAX_MENU_CHOICE = 5;

    private final Scanner scanner = new Scanner(System.in);

    public int readMenuChoice() throws InvalidCommandException {
        String input = scanner.nextLine().trim();

        int choice;
        try {
            choice = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new InvalidCommandException("'" + input + "' is not a valid option.");
        }

        if (choice < MIN_MENU_CHOICE || choice > MAX_MENU_CHOICE) {
            throw new InvalidCommandException(
                    "Choose a number between " + MIN_MENU_CHOICE + " and " + MAX_MENU_CHOICE + ".");
        }

        return choice;
    }

    public String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
}
