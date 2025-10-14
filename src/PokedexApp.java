import java.util.List;
import java.util.Scanner;

/**
 * Main application for the Pokémon database.
 * Automatically loads a TXT file at startup and displays all Pokémon.
 */
public class PokedexApp {

    private final Scanner scanner = new Scanner(System.in);
    private final PokedexController controller = new PokedexController();

    // Path to your TXT file
    private final String defaultFilePath = "pokemon_data.txt"; // put your TXT file here

    public static void main(String[] args) {
        System.out.println(new PokedexApp().start());
    }

    // Main menu loop
    public String start() {
        StringBuilder output = new StringBuilder("Welcome to the Pokémon Database!\n");

        // Automatically load TXT file at startup
        output.append(loadDefaultFile()).append("\n");

        boolean running = true;
        while (running) {
            output.append("\n=== Pokémon Database Menu ===");
            output.append("\n1. Add Pokémon");
            output.append("\n2. Remove Pokémon");
            output.append("\n3. Update Pokémon");
            output.append("\n4. Search Pokémon");
            output.append("\n5. Upload from TXT file");
            output.append("\n6. Exit");
            output.append("\nSelect an option: ");

            System.out.print(output);
            output.setLength(0); // reset for next loop

            String choice = scanner.nextLine().trim();
            String result;

            switch (choice) {
                case "1" -> result = addPokemonFlow();
                case "2" -> result = removePokemonFlow();
                case "3" -> result = updatePokemonFlow();
                case "4" -> result = searchPokemonFlow();
                case "5" -> result = uploadFileFlow();
                case "6" -> {
                    running = false;
                    result = "Exiting Pokédex. Goodbye!";
                }
                default -> result = "Invalid option. Please try again.";
            }

            System.out.println(result);
        }

        return "Application terminated.";
    }

    // ------------------ Load Default File ------------------
    public String loadDefaultFile() {
        String msg = controller.uploadFile(defaultFilePath);

        // Display all Pokémon after loading
        List<Pokemon> all = controller.searchPokemon(""); // empty string returns all
        if (all.isEmpty()) return msg + "\nNo Pokémon loaded.";

        StringBuilder sb = new StringBuilder(msg + "\n=== All Pokémon ===\n");
        for (Pokemon p : all) {
            sb.append(p).append("\n");
        }
        return sb.toString();
    }

    // ------------------ Flow Methods ------------------

    public String addPokemonFlow() {
        try {
            System.out.print("Dex Number: ");
            int dex = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Name: ");
            String name = scanner.nextLine().trim();

            System.out.print("Region: ");
            String region = scanner.nextLine().trim();

            System.out.print("Type 1: ");
            String type1 = scanner.nextLine().trim();

            System.out.print("Type 2 (optional): ");
            String type2 = scanner.nextLine().trim();

            System.out.print("Description: ");
            String desc = scanner.nextLine().trim();

            System.out.print("Can Evolve? (true/false): ");
            boolean canEvolve = Boolean.parseBoolean(scanner.nextLine().trim());

            Pokemon p = new Pokemon(dex, name, region, type1, type2, desc, canEvolve);
            return controller.addPokemon(p);

        } catch (Exception e) {
            return "Error: Invalid input. Pokémon not added.";
        }
    }

    public String removePokemonFlow() {
        try {
            System.out.print("Enter Dex Number to release: ");
            int dex = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Are you sure you want to release this Pokémon? (yes/no): ");
            boolean confirm = scanner.nextLine().trim().equalsIgnoreCase("yes");

            return controller.removePokemon(dex, confirm);

        } catch (Exception e) {
            return "Error: Invalid input or Pokémon not found.";
        }
    }

    public String updatePokemonFlow() {
        try {
            System.out.print("Dex Number to update: ");
            int dex = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("New Name: ");
            String name = scanner.nextLine().trim();

            System.out.print("New Region: ");
            String region = scanner.nextLine().trim();

            System.out.print("New Type 1: ");
            String type1 = scanner.nextLine().trim();

            System.out.print("New Type 2 (optional): ");
            String type2 = scanner.nextLine().trim();

            System.out.print("New Description: ");
            String desc = scanner.nextLine().trim();

            System.out.print("Can Evolve? (true/false): ");
            boolean canEvolve = Boolean.parseBoolean(scanner.nextLine().trim());

            Pokemon p = new Pokemon(dex, name, region, type1, type2, desc, canEvolve);
            return controller.updatePokemon(p);

        } catch (Exception e) {
            return "Error: Invalid input or Pokémon not found.";
        }
    }

    public String searchPokemonFlow() {
        System.out.print("Enter Pokémon name to search: ");
        String name = scanner.nextLine().trim();
        List<Pokemon> results = controller.searchPokemon(name);

        if (results.isEmpty()) {
            return "No Pokémon found with that name.";
        }

        StringBuilder sb = new StringBuilder();
        for (Pokemon p : results) {
            sb.append(p).append("\n");
        }
        return sb.toString();
    }

    public String uploadFileFlow() {
        System.out.print("Enter path to TXT file: ");
        String filePath = scanner.nextLine().trim();
        String msg = controller.uploadFile(filePath);

        // Display all Pokémon after uploading
        List<Pokemon> all = controller.searchPokemon("");
        if (all.isEmpty()) return msg + "\nNo Pokémon loaded.";

        StringBuilder sb = new StringBuilder(msg + "\n=== All Pokémon ===\n");
        for (Pokemon p : all) {
            sb.append(p).append("\n");
        }
        return sb.toString();
    }
}
