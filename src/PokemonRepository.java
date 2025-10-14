import java.util.*;

/**.
 * Stores Pokémon in database using the Pokedex Number.
 * CRUD (Create, Read, Update, Delete) operations.
 */
public class PokemonRepository {


    private final Map<Integer, Pokemon> storage = new HashMap<>(); // Internal storage


    /**
     * Saves a Pokémon data in the database
     */
    public Pokemon save(Pokemon pokemon) {
        storage.put(pokemon.getDexNumber(), pokemon);
        return pokemon;
    }

    /**
     * Deletes a Pokémon by Dex number.
     * Use PokeDex number of Pokémon to delete
     */
    public boolean deleteById(int dex) {
        return storage.remove(dex) != null;
    }

    /**
     * Updates Pokemon data
     * Pokémon to update
     * Updates Pokémon if exists, null if not found
     */
    public Pokemon update(Pokemon pokemon) {
        if (storage.containsKey(pokemon.getDexNumber())) {
            storage.put(pokemon.getDexNumber(), pokemon);
            return pokemon;
        }
        return null;
    }

    /**
     * Finds a Pokémon by PokeDex number.
     * Pokémon if found, null otherwise
     */
    public Pokemon findById(int dex) {
        return storage.get(dex);
    }

    /**
     * Finds Pokémon by name (case-insensitive).
     *
     * List of matching Pokémon
     */
    public List<Pokemon> findByName(String name) {
        List<Pokemon> results = new ArrayList<>();
        for (Pokemon p : storage.values()) {
            if (p.getName().equalsIgnoreCase(name)) results.add(p);
        }
        return results;
    }

    /**
     * Returns all Pokémon saved in the database.
     * Shows a list of all Pokemon
     */
    public List<Pokemon> findAll() {
        return new ArrayList<>(storage.values());
    }
}
