import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PokedexService {

    private final PokemonRepository repository = new PokemonRepository();

    // ----------------- Upload from TXT File -----------------
    /**
     * Reads a TXT file and adds valid Pokémon to the repository.
     * Returns the number of Pokémon successfully added.
     * Returns -1 if file cannot be read.
     */
    public int uploadFromFile(String filePath) throws IOException {
        List<Pokemon> addedPokemons = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            int count = 0;

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue; // skip empty lines

                String[] data = line.split(",");

                // Validate correct number of fields (7 expected)
                if (data.length != 7) continue;

                try {
                    int dex = Integer.parseInt(data[0].trim());
                    String name = data[1].trim();
                    String region = data[2].trim();
                    String type1 = data[3].trim();
                    String type2 = data[4].trim();
                    String description = data[5].trim();
                    boolean canEvolve = Boolean.parseBoolean(data[6].trim());

                    Pokemon p = new Pokemon(dex, name, region, type1, type2, description, canEvolve);

                    // Validate and avoid duplicates
                    if (validatePokemon(p) && repository.findById(dex) == null) {
                        repository.save(p);
                        addedPokemons.add(p);
                        count++;
                    }
                } catch (Exception e) {
                    // Skip invalid lines (non-integer Dex, invalid boolean, etc.)
                    continue;
                }
            }

            return count;

        } catch (IOException e) {
            return -1; // File does not exist or cannot be read
        }
    }


    /**
     * Checks validity for adding a Pokémon:
     * - All fields filled
     * - Dex number unique
     */
    public boolean validatePokemon(Pokemon pokemon) {
        if (pokemon.getDexNumber() <= 0 || pokemon.getName().isEmpty() ||
                pokemon.getRegion().isEmpty() || pokemon.getType1().isEmpty() ||
                pokemon.getDescription().isEmpty()) {
            return false;
        }
        // Dex must be unique
        return repository.findById(pokemon.getDexNumber()) == null;
    }

    /**
     * Validation for updates (pokemon must exist)
     */
    public boolean validatePokemonForUpdate(Pokemon pokemon) {
        if (pokemon.getDexNumber() <= 0 || pokemon.getName().isEmpty() ||
                pokemon.getRegion().isEmpty() || pokemon.getType1().isEmpty() ||
                pokemon.getDescription().isEmpty()) {
            return false;
        }
        return repository.findById(pokemon.getDexNumber()) != null;
    }


    public Pokemon addPokemon(Pokemon pokemon) {
        return repository.save(pokemon);
    }

    public boolean removePokemon(int dex) {
        return repository.deleteById(dex);
    }

    public Pokemon updatePokemon(Pokemon pokemon) {
        return repository.update(pokemon);
    }

    public List<Pokemon> searchPokemon(String name) {
        return repository.findByName(name);
    }

    public Pokemon findByDex(int dex) {
        return repository.findById(dex);
    }
}
