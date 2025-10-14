import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PokedexService {

    private final PokemonRepository repository = new PokemonRepository();

    // ------------------ Add a Pokémon ------------------
    public String addPokemon(Pokemon pokemon) {
        if (!validatePokemon(pokemon)) {
            return "Error: Invalid or duplicate Pokémon. Ensure all required fields are filled correctly and DexNumber is unique.";
        }
        repository.save(pokemon);
        return "Pokémon " + pokemon.getName() + " added successfully!";
    }

    // ------------------ Remove a Pokémon ------------------
    public String removePokemon(int dex) {
        Pokemon existing = repository.findById(dex);
        if (existing == null) return "Error: Pokémon with Dex #" + dex + " not found.";
        repository.deleteById(dex);
        return "Pokémon " + existing.getName() + " removed successfully!";
    }

    // ------------------ Update a Pokémon ------------------
    public String updatePokemon(Pokemon pokemon) {
        if (!validatePokemonForUpdate(pokemon)) {
            return "Error: Pokémon with Dex #" + pokemon.getDexNumber() + " does not exist or has invalid fields.";
        }
        repository.update(pokemon);
        return "Pokémon " + pokemon.getName() + " updated successfully!";
    }

    // ------------------ Display / Search Pokémon ------------------
    public List<Pokemon> searchPokemon(String name) {
        return repository.findByName(name);
    }

    public Pokemon findByDex(int dex) {
        return repository.findById(dex);
    }

    // ------------------ Upload from TXT ------------------
    public int uploadFromFile(String filePath) throws IOException {
        List<Pokemon> addedPokemons = new ArrayList<>();
        int lineNumber = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                lineNumber++;
                if (line.trim().isEmpty()) continue;

                String[] data = line.split(",");
                if (data.length < 6 || data.length > 7) {
                    System.out.println("Line " + lineNumber + ": Incorrect number of fields. Skipped.");
                    continue;
                }

                try {
                    int dex = Integer.parseInt(data[0].trim());
                    String name = data[1].trim();
                    String region = data[2].trim();
                    String type1 = data[3].trim();
                    String type2 = "";
                    String desc;
                    boolean canEvolve;

                    if (data.length == 7) {
                        type2 = data[4].trim();
                        desc = data[5].trim();
                        canEvolve = Boolean.parseBoolean(data[6].trim());
                    } else {
                        desc = data[4].trim();
                        canEvolve = Boolean.parseBoolean(data[5].trim());
                    }

                    Pokemon p = new Pokemon(dex, name, region, type1, type2, desc, canEvolve);

                    if (!validatePokemon(p)) {
                        System.out.println("Line " + lineNumber + ": Invalid or duplicate Pokémon. Skipped.");
                        continue;
                    }

                    repository.save(p);
                    addedPokemons.add(p);

                } catch (NumberFormatException e) {
                    System.out.println("Line " + lineNumber + ": Dex Number or CanEvolve field invalid. Skipped.");
                } catch (Exception e) {
                    System.out.println("Line " + lineNumber + ": Unknown error. Skipped.");
                }
            }
        } catch (IOException e) {
            return -1; // File could not be read
        }

        return addedPokemons.size();
    }

    // ------------------ Validation Methods ------------------
    public boolean validatePokemon(Pokemon pokemon) {
        if (pokemon.getDexNumber() <= 0) return false;
        if (pokemon.getName().isEmpty()) return false;
        if (pokemon.getRegion().isEmpty()) return false;
        if (pokemon.getType1().isEmpty()) return false;
        if (pokemon.getDescription().isEmpty()) return false;

        // Check for duplicate DexNumber
        return repository.findById(pokemon.getDexNumber()) == null;
    }

    public boolean validatePokemonForUpdate(Pokemon pokemon) {
        if (pokemon.getDexNumber() <= 0) return false;
        if (pokemon.getName().isEmpty()) return false;
        if (pokemon.getRegion().isEmpty()) return false;
        if (pokemon.getType1().isEmpty()) return false;
        if (pokemon.getDescription().isEmpty()) return false;

        // Check that DexNumber exists
        return repository.findById(pokemon.getDexNumber()) != null;
    }
}
