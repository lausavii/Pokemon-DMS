import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PokedexServiceTest {

    private PokedexService service;

    @BeforeEach
    void setUp() {
        // This method runs before each test and initializes a new PokedexService instance
        service = new PokedexService(); // uses internal repository
    }

    @Test
    void testAddPokemon() {
        // Tests adding a Pokémon to the Pokedex
        Pokemon squirtle = new Pokemon(7, "Squirtle", "Kanto", "Water", "", "A small turtle Pokémon.", true);
        service.addPokemon(squirtle);
        // Verifies that the Pokémon was successfully added and can be found by its Dex number
        assertEquals(squirtle, service.findByDex(7), "Squirtle should be saved in repository.");
    }

    @Test
    void testUpdatePokemon() {
        // Tests updating an existing Pokémon’s data (for example, changing its type)
        Pokemon eevee = new Pokemon(133, "Eevee", "Kanto", "Normal", "", "Flexible Pokémon.", true);
        service.addPokemon(eevee);
        Pokemon evolved = new Pokemon(133, "Eevee", "Kanto", "Fairy", "", "Type changed.", true);
        // Updates Eevee’s data and checks the result message
        String result = service.updatePokemon(evolved);
        assertTrue(result.contains("updated successfully"));
        // Confirms the Pokémon type was actually updated
        assertEquals("Fairy", service.findByDex(133).getType1());
    }

    @Test
    void testRemovePokemon() {
        // Tests removing a Pokémon from the Pokedex by Dex number
        Pokemon snorlax = new Pokemon(143, "Snorlax", "Kanto", "Normal", "", "A large, sleepy Pokémon.", false);
        service.addPokemon(snorlax);
        String result = service.removePokemon(143);
        // Checks if the removal message is correct
        assertTrue(result.contains("removed successfully"));
        // Verifies that the Pokémon no longer exists in the repository
        assertNull(service.findByDex(143));
    }

    @Test
    void testFindPokemonByName() {
        // Tests searching for a Pokémon by its name
        Pokemon psyduck = new Pokemon(54, "Psyduck", "Kanto", "Water", "", "Always has a headache.", true);
        service.addPokemon(psyduck);
        // Searches for "Psyduck" and ensures the result matches the expected Pokémon
        Pokemon found = service.searchPokemon("Psyduck").get(0);
        assertNotNull(found);
        assertEquals(54, found.getDexNumber());
    }
}

