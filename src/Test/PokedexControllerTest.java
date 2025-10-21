import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PokedexControllerTest {

    private PokedexController controller;

    // Initialize a fresh controller before each test
    @BeforeEach
    void setUp() {
        controller = new PokedexController();
    }

    // --- Test adding Pokémon ---
    @Test
    void testAddPokemon() {
        Pokemon p = new Pokemon(1, "Pikachu", "Kanto", "Electric", "", "Electric mouse Pokémon", true);
        String result = controller.addPokemon(p);
        assertEquals("Pokémon added successfully!", result);

        // Adding the same DexNumber again should fail
        String duplicate = controller.addPokemon(p);
        assertEquals("Error: Pokémon with that Dex number already exists.", duplicate);
    }

    // --- Test removing Pokémon ---
    @Test
    void testRemovePokemon() {
        Pokemon p = new Pokemon(2, "Charmander", "Kanto", "Fire", "", "Fire lizard Pokémon", true);
        controller.addPokemon(p);

        // Attempt to remove with confirmation
        String removed = controller.removePokemon(2, true);
        assertEquals("Pokémon removed successfully!", removed);

        // Attempt to remove non-existent Pokémon
        String notFound = controller.removePokemon(999, true);
        assertEquals("Error: Pokémon not found.", notFound);

        // Attempt to remove but cancel operation
        controller.addPokemon(p);
        String canceled = controller.removePokemon(2, false);
        assertEquals("Operation canceled.", canceled);
    }

    // --- Test updating Pokémon ---
    @Test
    void testUpdatePokemon() {
        Pokemon p = new Pokemon(3, "Bulbasaur", "Kanto", "Grass", "Poison", "Seed Pokémon", true);
        controller.addPokemon(p);

        // Update description and canEvolve
        Pokemon updated = new Pokemon(3, "Bulbasaur", "Kanto", "Grass", "Poison", "Updated seed Pokémon", false);
        String result = controller.updatePokemon(updated);
        assertEquals("Pokémon updated successfully!", result);

        // Attempt to update non-existent Pokémon
        Pokemon notExist = new Pokemon(999, "MissingNo", "Unknown", "???", "", "Glitch Pokémon", false);
        String error = controller.updatePokemon(notExist);
        assertEquals("Error: Pokémon not found.", error);
    }

    // --- Test searching Pokémon ---
    @Test
    void testSearchPokemon() {
        Pokemon p1 = new Pokemon(4, "Squirtle", "Kanto", "Water", "", "Tiny turtle Pokémon", true);
        Pokemon p2 = new Pokemon(5, "Blastoise", "Kanto", "Water", "", "Shellfish Pokémon", false);
        controller.addPokemon(p1);
        controller.addPokemon(p2);

        List<Pokemon> results = controller.searchPokemon("squirtle");
        assertEquals(1, results.size());
        assertEquals("Squirtle", results.get(0).getName());

        // Search with empty string should return all Pokémon
        List<Pokemon> all = controller.searchPokemon("");
        assertEquals(2, all.size());
    }

    // --- Test uploading file ---
    @Test
    void testUploadFile() {
        // Here, just simulate a bad file path
        String badPath = controller.uploadFile("nonexistent.txt");
        assertEquals("Error: File does not exist or cannot be read.", badPath);

    }
}
