/**
 * Pokemon class represents a Pokemon and all of its characteristics
 * Stores all information about a Pokémon.
 * .
 */
public class Pokemon {

    //
    private int dexNumber;        // Unique Pokédex number
    private String name;          // Pokémon name
    private String region;        // Pokémon region
    private String type1;         // Primary type
    private String type2;         // Secondary type (optional)
    private String description;   // Description of the Pokémon
    private boolean canEvolve;    // Indicates if Pokémon can evolve


    /**
     * Constructor method that displays a Pokemon with all required fields.
     */
    public Pokemon(int dexNumber, String name, String region, String type1, String type2,
                   String description, boolean canEvolve) {
        this.dexNumber = dexNumber;
        this.name = name;
        this.region = region;
        this.type1 = type1;
        this.type2 = type2;
        this.description = description;
        this.canEvolve = canEvolve;
    }

    // Getters
    public int getDexNumber() { return dexNumber; }
    public String getName() { return name; }
    public String getRegion() { return region; }
    public String getType1() { return type1; }
    public String getType2() { return type2; }
    public String getDescription() { return description; }
    public boolean isCanEvolve() { return canEvolve; }


    // Setters return the object itself for method chaining
    public Pokemon setDexNumber(int dexNumber) { this.dexNumber = dexNumber; return this; }
    public Pokemon setName(String name) { this.name = name; return this; }
    public Pokemon setRegion(String region) { this.region = region; return this; }
    public Pokemon setType1(String type1) { this.type1 = type1; return this; }
    public Pokemon setType2(String type2) { this.type2 = type2; return this; }
    public Pokemon setDescription(String description) { this.description = description; return this; }
    public Pokemon setCanEvolve(boolean canEvolve) { this.canEvolve = canEvolve; return this; }


    /**
     * Returns a formatted string with all Pokémon details.
     */
    @Override
    public String toString() {
        return "Dex #" + dexNumber + " | " + name + " (" + region + ") [" + type1 +
                (type2 != null && !type2.isEmpty() ? ", " + type2 : "") + "]" +
                " Evolves: " + (canEvolve ? "Yes" : "No") +
                "\nDescription: " + description;
    }
}
