package persistence.db;

import com.google.gson.*;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Class that reads the database configuration from a JSON file.
 *
 * @author Group 6
 * @version 1.0
 */
public class DBConfiguration {

    /**
     * Method that reads the database configuration from a JSON file.
     *
     * @return JsonObject with the database configuration.
     * @throws IOException if there was an error reading the file.
     */
    private JsonObject readConfig() throws IOException {

        StringBuilder stringBuilder = new StringBuilder();
        File file = new File("data/config.json");
        Scanner scanner = new Scanner(file);

        while (scanner.hasNext()) {
            stringBuilder.append(scanner.nextLine());
        }
        scanner.close();
        String data = stringBuilder.toString();

        return JsonParser.parseString(data).getAsJsonObject();
    }

    /**
     * Method that returns the database address.
     *
     * @return String with the database address.
     * @throws IOException if there was an error reading the file.
     */
     protected String getAddress() throws IOException {
        return readConfig().get("database_address").getAsString();
     }

    /**
     * Method that returns the database port.
     *
     * @return int with the database port.
     * @throws IOException if there was an error reading the file.
     */
    protected int getPort() throws IOException {
        return Integer.parseInt(readConfig().get("database_port").getAsString());
    }

    /**
     * Method that returns the database name.
     *
     * @return String with the database name.
     * @throws IOException if there was an error reading the file.
     */
    protected String getName() throws IOException {
        return readConfig().get("database_name").getAsString();
    }

    /**
     * Method that returns the database user.
     *
     * @return String with the database user.
     * @throws IOException if there was an error reading the file.
     */
    protected String getUser() throws IOException {
        return readConfig().get("database_username").getAsString();
    }

    /**
     * Method that returns the database password.
     *
     * @return String with the database password.
     * @throws IOException if there was an error reading the file.
     */
    protected String getPassword() throws IOException {
        return readConfig().get("database_password").getAsString();
    }
}