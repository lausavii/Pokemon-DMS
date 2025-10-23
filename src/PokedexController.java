import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PokedexController {

    private final List<Pokemon> pokemons = new ArrayList<>();

    // CRUD Operations

    public String addPokemon(Pokemon p) {
        if (pokemons.contains(p)) return "Error: Pokémon with that Dex number already exists.";
        pokemons.add(p);
        return "Pokémon added successfully!";
    }

    public String removePokemon(int dexNumber, boolean confirm) {
        if (!confirm) return "Operation canceled.";
        Pokemon toRemove = null;
        for (Pokemon p : pokemons) {
            if (p.getDexNumber() == dexNumber) {
                toRemove = p;
                break;
            }
        }
        if (toRemove == null) return "Error: Pokémon not found.";
        pokemons.remove(toRemove);
        return "Pokémon removed successfully!";
    }

    public String updatePokemon(Pokemon updated) {
        for (int i = 0; i < pokemons.size(); i++) {
            if (pokemons.get(i).getDexNumber() == updated.getDexNumber()) {
                pokemons.set(i, updated);
                return "Pokémon updated successfully!";
            }
        }
        return "Error: Pokémon not found.";
    }

    public List<Pokemon> searchPokemon(String name) {
        List<Pokemon> results = new ArrayList<>();
        if (name == null || name.isBlank()) {
            results.addAll(pokemons); // return all
            return results;
        }
        for (Pokemon p : pokemons) {
            if (p.getName().toLowerCase().contains(name.toLowerCase())) {
                results.add(p);
            }
        }
        return results;
    }



    public String uploadFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists() || !file.canRead()) return "Error: File does not exist or cannot be read.";

        int count = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 7) continue; // skip invalid lines
                try {
                    int dex = Integer.parseInt(parts[0].trim());
                    String name = parts[1].trim();
                    String region = parts[2].trim();
                    String type1 = parts[3].trim();
                    String type2 = parts[4].trim();
                    String desc = parts[5].trim();
                    boolean evolve = Boolean.parseBoolean(parts[6].trim());

                    Pokemon p = new Pokemon(dex, name, region, type1, type2, desc, evolve);
                    if (!pokemons.contains(p)) {
                        pokemons.add(p);
                        count++;
                    }
                } catch (Exception e) {
                    // skip invalid line
                }
            }
        } catch (IOException e) {
            return "Error: Unable to read file.";
        }

        return count > 0 ? "Successfully loaded " + count + " Pokémon." : "No valid Pokémon found in file.";
    }

    public String saveToFile(String filePath) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
            for (Pokemon p : pokemons) {
                bw.newLine();
            }
        } catch (IOException e) {
            return "Error: Unable to save file.";
        }
        return "Pokémon saved to file successfully!";
    }
}
