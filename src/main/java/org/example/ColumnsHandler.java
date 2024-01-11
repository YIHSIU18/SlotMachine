package org.example;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class ColumnsHandler {
    private static ArrayList<ArrayList<String>> json = new ArrayList<>();

    /**
     * Constructor for ColumnsHandler class.
     * Initializes the object by reading and parsing a JSON file containing column data.
     * @throws JsonSyntaxException if there is an issue with JSON syntax during parsing.
     */
    public ColumnsHandler() throws JsonSyntaxException
    {
        // Create a Gson object for JSON parsing
        Gson data = new Gson();
        // Define the path to the JSON file within the JAR
        String filePath = "/columns.json";
        try (InputStream inputStream = ColumnsHandler.class.getResourceAsStream(filePath)) {
           if(inputStream != null) // Check if the input stream is not null
           {
               Scanner sc = new Scanner(inputStream).useDelimiter("\\A");
               String contenuFichier = sc.hasNext() ? sc.next() : "";
               // Parse the JSON content into an ArrayList using Gson
               json = data.fromJson(contenuFichier,ArrayList.class);

           }else
           {
               System.out.println(" Le fiicher n'a pas trouvé dans le JAR");
           }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets a random symbol from the loaded data.
     *
     * @return A randomly selected symbol.
     */
    public String getRandomSymbol()
    {
        if (json != null && !json.isEmpty()) {
            // Select a random column
            int randomColumnIndex = new Random().nextInt(json.size());

            // Select a random symbol from the chosen column
            List<String> columnSymbols = json.get(randomColumnIndex);
            int randomSymbolIndex = new Random().nextInt(columnSymbols.size());

            return columnSymbols.get(randomSymbolIndex);
        } else {
            // Handle the case when the JSON data is not loaded properly
            System.out.println("Error: JSON data not loaded.");
            return "";
        }
    }

    /*public ArrayList<ArrayList<String>> getColumns()
    {
        return json;
    }*/






}
