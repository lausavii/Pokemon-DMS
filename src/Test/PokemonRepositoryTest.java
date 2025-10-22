import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PokemonRepositoryTest {

    private PokemonRepository repository;

    // --- Runs before each test to reset the repository ---
    @BeforeEach
    void setUp() {
        repository = new PokemonRepository();
    }

    // Test saving a new Pokémon
    @Test
    void testSave() {
        // Create a Pokémon with all 7 arguments
        Pokemon pikachu = new Pokemon(
                25,                 // Dex Number
                "Pikachu",          // Name
                "Kanto",            // Region
                "Electric",         // Type 1
                "",                 // Type 2 (optional)
                "Mouse Pokémon",    // Description
                true                // Can Evolve
        );

        Pokemon saved = repository.save(pikachu);
        assertEquals(pikachu, saved, "Saved Pokémon should match the input Pokémon");

        List<Pokemon> all = repository.findByName("");
        assertTrue(all.contains(pikachu), "Repository should contain the saved Pokémon");
    }

    // Test updating an existing Pokémon
    @Test
    void testUpdate() {
        Pokemon charmander = new Pokemon(4, "Charmander", "Kanto", "Fire", "", "Lizard Pokémon", true);
        repository.save(charmander);

        // Update description
        charmander.setDescription("Tiny flame Pokémon");
        Pokemon updated = repository.update(charmander);

        assertEquals("Tiny flame Pokémon", updated.getDescription(), "Description should be updated");
    }

    // Test deleting a Pokémon
    @Test
    void testDeleteById() {
        Pokemon bulbasaur = new Pokemon(1, "Bulbasaur", "Kanto", "Grass", "Poison", "Seed Pokémon", true);
        repository.save(bulbasaur);

        boolean removed = repository.deleteById(1);
        assertTrue(removed, "Bulbasaur should be removed");
        assertNull(repository.findById(1), "Repository should no longer contain Bulbasaur");
    }

    // Test finding Pokémon by Dex Number
    @Test
    void testFindById() {
        Pokemon squirtle = new Pokemon(7, "Squirtle", "Kanto", "Water", "", "Tiny Turtle Pokémon", true);
        repository.save(squirtle);

        Pokemon found = repository.findById(7);
        assertEquals(squirtle, found, "Should find Squirtle by Dex Number");
    }

    // Test finding Pokémon by Name
    @Test
    void testFindByName() {
        Pokemon meowth = new Pokemon(52, "Meowth", "Kanto", "Normal", "", "Scratch Cat Pokémon", true);
        repository.save(meowth);

        List<Pokemon> result = repository.findByName("Meowth");
        assertEquals(1, result.size(), "Should find 1 Pokémo" +
                "n with name Meowth");
        assertEquals(meowth, result.get(0), "Found Pokémon should be Meowth");

        // Test empty string returns all Pokémon
        List<Pokemon> all = repository.findByName("");
        assertTrue(all.contains(meowth), "All Pokémon search should include Meowth");
    }
}
