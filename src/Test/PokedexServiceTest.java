import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PokedexServiceTest {

    private PokedexService service;

    @BeforeEach
    void setUp() {
        service = new PokedexService(); // uses internal repository
    }

    @Test
    void testAddPokemon() {
        Pokemon squirtle = new Pokemon(7, "Squirtle", "Kanto", "Water", "", "A small turtle Pokémon.", true);
        service.addPokemon(squirtle);
        assertEquals(squirtle, service.findByDex(7), "Squirtle should be saved in repository.");
    }

    @Test
    void testUpdatePokemon() {
        Pokemon eevee = new Pokemon(133, "Eevee", "Kanto", "Normal", "", "Flexible Pokémon.", true);
        service.addPokemon(eevee);
        Pokemon evolved = new Pokemon(133, "Eevee", "Kanto", "Fairy", "", "Type changed.", true);
        String result = service.updatePokemon(evolved);
        assertTrue(result.contains("updated successfully"));
        assertEquals("Fairy", service.findByDex(133).getType1());
    }

    @Test
    void testRemovePokemon() {
        Pokemon snorlax = new Pokemon(143, "Snorlax", "Kanto", "Normal", "", "A large, sleepy Pokémon.", false);
        service.addPokemon(snorlax);
        String result = service.removePokemon(143);
        assertTrue(result.contains("removed successfully"));
        assertNull(service.findByDex(143));
    }

    @Test
    void testFindPokemonByName() {
        Pokemon psyduck = new Pokemon(54, "Psyduck", "Kanto", "Water", "", "Always has a headache.", true);
        service.addPokemon(psyduck);

        Pokemon found = service.searchPokemon("Psyduck").get(0);
        assertNotNull(found);
        assertEquals(54, found.getDexNumber());
    }
}
