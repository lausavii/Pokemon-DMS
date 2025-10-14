import java.io.IOException;
import java.util.List;

/**
 *
 * Key Responsibilities:
 * - Add Pokémon (with validation)
 * - Remove Pokémon (with confirmation)
 * - Update Pokémon (with validation)
 * - Search Pokémon by name
 * - Upload Pokémon from a TXT file
 *

 */
public class PokedexController {


    private final PokedexService service = new PokedexService(); // Service layer instance


    /**
     * Adds a new Pokémon after validating.
     */
    public String addPokemon(Pokemon pokemon) {
        if (!service.validatePokemon(pokemon)) {
            return "Error: Invalid Pokémon data or Dex number already exists.";
        }
        service.addPokemon(pokemon);
        return "Pokémon has been caught!";
    }

    /**
     * Removes a Pokémon by PokeDex number after confirmation.
     * USes PokeDex number to remove Pokemon data
     * User confirmation (true if yes)
     * returns  Success or error message
     */
    public String removePokemon(int dex, boolean confirm) {
        Pokemon existing = service.findByDex(dex);
        if (existing == null) return "Error: Pokémon not found.";
        if (!confirm) return "Release canceled.";
        service.removePokemon(dex);
        return "Pokémon released.";
    }

    // ----------------- Update Pokémon -----------------
    /**
     * Updates a Pokémon after validation.
     *  returns a Success or error message
     */
    public String updatePokemon(Pokemon pokemon) {
        if (!service.validatePokemonForUpdate(pokemon)) {
            return "Error: Invalid update data or Pokémon not found.";
        }
        service.updatePokemon(pokemon);
        return "Pokémon updated successfully.";
    }


    /**
     * Searches Pokémon by name.
     * List of matching Pokémon
     */
    public List<Pokemon> searchPokemon(String name) {
        return service.searchPokemon(name);
    }


    /**
     * Uploads Pokémon data from a TXT file.
     * Success or error message
     */
    public String uploadFile(String filePath) {
        try {
            int added = service.uploadFromFile(filePath);
            if (added < 0) return "Error: File does not exist or cannot be read.";
            return "File uploaded successfully! " + added + " Pokémon added.";
        } catch (IOException e) {
            return "Error reading file: " + e.getMessage();
        }
    }
}
