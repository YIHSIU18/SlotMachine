package org.example;

import com.fasterxml.jackson.databind.util.JSONPObject;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Machine {
    private String[][] matrix;
    private ColumnsHandler columnsHandler;
    private int coins;
    private int gamesPlayed;
    private int gamesWon;
    private int coinsSpent;

    public Machine(ColumnsHandler columnsHandler, int initialCoins) {
        this.columnsHandler = columnsHandler;
        this.matrix = new String[3][3];
        this.coins = initialCoins;
        this.gamesPlayed = 0;
        this.gamesWon = 0;
        this.coinsSpent = 0;
    }

    public void play() {
        Scanner scanner = new Scanner(System.in);


        System.out.println("°º¤ø,¸¸,ø¤º°`°º¤ø,¸,ø¤°º¤ø,¸¸,ø¤º°`");
        System.out.println("Bienvenue au Casino de Céladopole !");
        System.out.println("°º¤ø,¸¸,ø¤º°`°º¤ø,¸,ø¤°º¤ø,¸¸,ø¤º°`");
        System.out.println("                                   ");

        if (coins == 0) {
            System.out.println("You have no coins.");
            return; // Exit the function
        }

        while (coins > 0) {


            refreshMatrix();
            displayMatrix();


            System.out.println("Jetons possédés : " + coins);
            int tokens = promptForTokens(scanner);
            int winnings = checkWin(tokens);


            coins -= tokens;
            coins += winnings;
            gamesPlayed++;
            coinsSpent += tokens;

            System.out.println("Gains : " + winnings);
        }

        displayStatistics();
    }


    private int promptForTokens(Scanner scanner) {
        while (true) {
            System.out.print("Combien de jetons ? (1, 2 ou 3) : ");
            String input = scanner.nextLine();

            if (StringUtils.isNumeric(input)) {
                int tokens = Integer.parseInt(input);
                if (tokens >= 1 && tokens <= 3) {
                    return tokens;
                } else {
                    System.out.println("Veuillez saisir un nombre entre 1 et 3 inclus.");
                }
            } else {
                System.out.println("Veuillez saisir un nombre valide entre 1 et 3 inclus.");
            }
        }
    }
    public void refreshMatrix() {
        for (int col = 0; col < 3; col++) {
            String columnSymbol = columnsHandler.getRandomSymbol();
            for (int row = 0; row < 3; row++) {
                matrix[row][col] = columnSymbol;
            }
        }
    }

    public void displayMatrix() {
        System.out.println("\n-----------------------------------");
        for (int row = 0; row < 3; row++) {
            System.out.print(" | ");
            for (int col = 0; col < 3; col++) {
                matrix[row][col] = columnsHandler.getRandomSymbol();
                System.out.print(" (" + matrix[row][col] + ")  | ");
            }
            System.out.println();
        }
        System.out.println("-----------------------------------");
    }


    private int checkWin(int tokens) {
        int winnings = 0;

        // Vérifier les lignes horizontales
        for (int row = 0; row < 3; row++) {
            if (matrix[row][0].equals(matrix[row][1]) && matrix[row][1].equals(matrix[row][2])) {
                winnings += tokens; // Gain en fonction des règles du jeu
            }
        }

        // Vérifier les colonnes verticales
        for (int col = 0; col < 3; col++)
        {
            if (matrix[0][col].equals(matrix[1][col]) && matrix[1][col].equals(matrix[2][col])) {
                winnings += tokens; // Gain en fonction des règles du jeu
            }
        }

        // Vérifier les diagonales
        if (matrix[0][0].equals(matrix[1][1]) && matrix[1][1].equals(matrix[2][2])) {
            winnings += tokens; // Gain en fonction des règles du jeu
        }

        if (matrix[0][2].equals(matrix[1][1]) && matrix[1][1].equals(matrix[2][0])) {
            winnings += tokens; // Gain en fonction des règles du jeu
        }

        // Check for specific symbol wins and add corresponding payouts
        for (int row = 0; row < 3; row++) {
            int countSeven = 0; // Counter for "7" occurrences in a row
            for (int col = 0; col < 3; col++) {
                String symbol = matrix[row][col];
                if (symbol.equals("7")) {
                    countSeven++;
                }
            }
            if (countSeven == 3) {
                winnings += 300; // Award 300 jetons for three "7" in a row
            }
        }

        gamesWon += (winnings > 0) ? 1 : 0; // Incrémenter le nombre de parties gagnées si le joueur a gagné

        return winnings;
    }


    public void displayStatistics()
    {
        System.out.println("Statistiques du jeu :");
        System.out.println("Nombre de parties jouées : " + gamesPlayed);
        System.out.println("Nombre de parties gagnées : " + gamesWon);
        System.out.println("Nombre de jetons dépensés : " + coinsSpent);

        // Save statistics to a JSON file
        saveStatisticsToJson();
    }

    private void saveStatisticsToJson()
    {
        JSONObject result = new JSONObject();
        result.put("coins : ", coins);
        result.put("gamesPlayed : ", gamesPlayed);
        result.put("gamesWon : ",gamesWon);
        result.put("coinsSpent : ",coinsSpent);

        try (FileWriter fileWriter = new FileWriter("statistics.json"))
        {
        fileWriter.write(result.toString());
        System.out.println("Statistics saved to statistics.json");
        } catch (IOException e)
        {
        System.err.println("Error saving statistics to JSON file: " + e.getMessage());
        }

    }


}




