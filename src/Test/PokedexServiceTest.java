import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PokedexServiceTest {

    private PokedexService service;
    private PokemonRepository repository;

    @BeforeEach
    void setUp() {
        repository = new PokemonRepository();
        service = new PokedexService();
    }

    @Test
    void testAddPokemon() {
        Pokemon squirtle = new Pokemon(7, "Squirtle", "Kanto", "Water", "", "A small turtle Pokémon.", true);
        service.addPokemon(squirtle);
        assertEquals(squirtle, repository.findById(7), "Squirtle should be saved in repository.");
    }

    @Test
    void testUpdatePokemon() {
        Pokemon eevee = new Pokemon(133, "Eevee", "Kanto", "Normal", "", "A flexible Pokémon that evolves in many ways.", true);
        repository.save(eevee);
        Pokemon evolved = new Pokemon(133, "Eevee", "Kanto", "Fairy", "", "Type changed for testing.", true);
        service.updatePokemon(evolved);
        assertEquals("Fairy", repository.findById(133).getType1(), "Type should be updated.");
    }

    @Test
    void testRemovePokemon() {
        Pokemon snorlax = new Pokemon(143, "Snorlax", "Kanto", "Normal", "", "A large, sleepy Pokémon.", false);
        repository.save(snorlax);
        boolean result = service.removePokemon(143).startsWith("Pokémon");
        assertTrue(result, "Should return true if Pokémon was successfully removed.");
        assertNull(repository.findById(143), "Snorlax should be removed from the repository.");
    }

    @Test
    void testFindPokemonByName() {
        Pokemon psyduck = new Pokemon(54, "Psyduck", "Kanto", "Water", "", "Always has a headache.", true);
        repository.save(psyduck);

        // Use searchPokemon() instead of findPokemonByName()
        Pokemon found = service.searchPokemon("Psyduck").get(0);

        assertNotNull(found, "Should find Psyduck.");
        assertEquals(54, found.getDexNumber(), "Dex number should match Psyduck.");
    }
}