import java.util.*;
import java.io.IOException;

public class PokedexApp {

    private final Scanner scanner = new Scanner(System.in);
    private final PokedexService service = new PokedexService();

    // Entry point
    public static void main(String[] args) {
        PokedexApp app = new PokedexApp();
        System.out.println(app.start());
    }

    // Main menu loop
    public String start() {
        System.out.println("\nWelcome to the LalaDex System!");
        boolean running = true;

        while (running) {
            System.out.println("\n *Pokémon Database Menu*");
            System.out.println("1. Add Pokémon");
            System.out.println("2. Remove Pokémon");
            System.out.println("3. Update Pokémon");
            System.out.println("4. Display Pokémon");
            System.out.println("5. PokeSync (Upload txt file)");
            System.out.println("6. Who's That Pokémon? (Custom Action)");
            System.out.println("7. Exit");
            System.out.print("Select an option: ");

            String choice = scanner.nextLine().trim();
            String result;

            switch (choice) {
                case "1" -> result = addPokemonFlow();
                case "2" -> result = removePokemonFlow();
                case "3" -> result = updatePokemonFlow();
                case "4" -> result = displayPokemonFlow();
                case "5" -> result = uploadFileFlow();
                case "6" -> result = whosThatPokemonFlow();
                case "7" -> {
                    running = false;
                    result = "Exiting Pokédex. Goodbye!";
                }
                default -> result = "Invalid option. Please try again.";
            }

            System.out.println(result);
        }

        return "Application terminated.";
    }

    // --- Flow Methods ---

    public String addPokemonFlow() {
        try {
            System.out.print("PokeDex Number: ");
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

            // Validate before adding
            if (!service.validatePokemon(p)) {
                return "Error: Invalid or duplicate Pokémon. Check PokeNumber and required fields.";
            }

            return service.addPokemon(p);
        } catch (NumberFormatException e) {
            return "Error: Dex Number must be an integer.";
        } catch (Exception e) {
            return "Error: Invalid input. Pokémon not added.";
        }
    }

    public String removePokemonFlow() {
        try {
            System.out.print("Enter Dex Number to release: ");
            int dex = Integer.parseInt(scanner.nextLine().trim());

            Pokemon existing = service.findByDex(dex);
            if (existing == null) return "Error: Pokémon with PokeDex #" + dex + " not found.";

            System.out.print("Are you sure you want to release this Pokémon? (yes/no): ");
            boolean confirm = scanner.nextLine().trim().equalsIgnoreCase("yes");

            if (!confirm) return "Operation canceled.";
            return service.removePokemon(dex);
        } catch (NumberFormatException e) {
            return "Error: PokeDex Number must be an integer.";
        } catch (Exception e) {
            return "Error: Invalid input.";
        }
    }

    public String updatePokemonFlow() {
        try {
            System.out.print("Enter Dex Number of Pokémon to update: ");
            int dex = Integer.parseInt(scanner.nextLine().trim());

            Pokemon existing = service.findByDex(dex);
            if (existing == null) return "Error: Pokémon with Dex #" + dex + " not found.";

            boolean updating = true;
            while (updating) {
                System.out.println("\nSelect field to update:");
                System.out.println("1. Name");
                System.out.println("2. Region");
                System.out.println("3. Type 1");
                System.out.println("4. Type 2");
                System.out.println("5. Description");
                System.out.println("6. Can Evolve");
                System.out.println("7. Done");
                System.out.print("Choice: ");

                String choice = scanner.nextLine().trim();

                switch (choice) {
                    case "1" -> {
                        System.out.print("New Name: ");
                        existing.setName(scanner.nextLine().trim());
                    }
                    case "2" -> {
                        System.out.print("New Region: ");
                        existing.setRegion(scanner.nextLine().trim());
                    }
                    case "3" -> {
                        System.out.print("New Type 1: ");
                        existing.setType1(scanner.nextLine().trim());
                    }
                    case "4" -> {
                        System.out.print("New Type 2 (optional): ");
                        existing.setType2(scanner.nextLine().trim());
                    }
                    case "5" -> {
                        System.out.print("New Description: ");
                        existing.setDescription(scanner.nextLine().trim());
                    }
                    case "6" -> {
                        System.out.print("Can Evolve? (true/false): ");
                        existing.setCanEvolve(Boolean.parseBoolean(scanner.nextLine().trim()));
                    }
                    case "7" -> updating = false;
                    default -> System.out.println("Invalid choice. Try again.");
                }
            }

            return service.updatePokemon(existing);

        } catch (Exception e) {
            return "Error: Invalid input. Pokémon not updated.";
        }
    }

    public String displayPokemonFlow() {
        List<Pokemon> pokemons = service.searchPokemon(""); // empty string returns all
        if (pokemons.isEmpty()) return "No Pokémon available to display.";

        StringBuilder sb = new StringBuilder("*All Pokémon* \n");
        for (Pokemon p : pokemons) sb.append(p).append("\n");
        return sb.toString();
    }

    public String uploadFileFlow() {
        System.out.print("Enter path to TXT file: ");
        String path = scanner.nextLine().trim();
        try {
            int count = service.uploadFromFile(path);
            if (count == -1) return "Error: File could not be read.";
            return count + " Pokémon successfully uploaded!";
        } catch (IOException e) {
            return "Error: Problem reading the file.";
        }
    }

    public String whosThatPokemonFlow() {
        List<Pokemon> all = service.searchPokemon(""); // get all Pokémon
        if (all.isEmpty()) {
            return "No Pokémon available in the database. Please upload data first.";
        }

        // Pick a random Pokémon
        Pokemon spotlight = all.get((int) (Math.random() * all.size()));

        // Count other Pokémon with the same Type 1 or Type 2
        long matchCount = all.stream()
                .filter(p -> p.getDexNumber() != spotlight.getDexNumber())
                .filter(p -> p.getType1().equalsIgnoreCase(spotlight.getType1())
                        || (spotlight.getType2() != null && !spotlight.getType2().isEmpty()
                        && !spotlight.getType2().equalsIgnoreCase("null")
                        && !spotlight.getType2().equalsIgnoreCase("N/A")
                        && (p.getType1().equalsIgnoreCase(spotlight.getType2())
                        || p.getType2().equalsIgnoreCase(spotlight.getType2()))))
                .count();

        // Build the message
        StringBuilder sb = new StringBuilder();
        sb.append("*It's ").append(spotlight.getName()).append("!*\n");
        sb.append(spotlight).append("\n");

        if (spotlight.getType2() == null || spotlight.getType2().isEmpty()
                || spotlight.getType2().equalsIgnoreCase("null")
                || spotlight.getType2().equalsIgnoreCase("N/A")) {
            sb.append("You have caught ").append(matchCount)
                    .append(" ").append(spotlight.getType1())
                    .append("-type Pokémon. Gotta Catch 'Em All!");
        } else {
            sb.append("You have caught ").append(matchCount)
                    .append(" ")
                    .append(spotlight.getType1()).append("/")
                    .append(spotlight.getType2())
                    .append(" type Pokemon. Gotta Catch 'Em All!");
        }

        return sb.toString();
    }


}
