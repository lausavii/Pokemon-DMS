public class Pokemon {
    private int dexNumber;
    private String name;
    private String region;
    private String type1;
    private String type2;
    private String description;
    private boolean canEvolve;

    public Pokemon(int dexNumber, String name, String region, String type1, String type2, String description, boolean canEvolve) {
        if (dexNumber <= 0) throw new IllegalArgumentException("Dex number must be positive.");
        if (name == null || name.isBlank()) throw new IllegalArgumentException("Name cannot be empty.");
        if (region == null || region.isBlank()) region = "Unknown";
        if (type1 == null || type1.isBlank()) type1 = "Unknown";

        this.dexNumber = dexNumber;
        this.name = name;
        this.region = region;
        this.type1 = type1;
        this.type2 = type2 != null ? type2 : "";
        this.description = description != null ? description : "";
        this.canEvolve = canEvolve;
    }

    // --- Getters & Setters ---
    public int getDexNumber() { return dexNumber; }
    public void setDexNumber(int dexNumber) { if(dexNumber>0) this.dexNumber = dexNumber; }
    public String getName() { return name; }
    public void setName(String name) { if(name!=null && !name.isBlank()) this.name=name; }
    public String getRegion() { return region; }
    public void setRegion(String region) { if(region!=null) this.region=region; }
    public String getType1() { return type1; }
    public void setType1(String type1) { if(type1!=null && !type1.isBlank()) this.type1=type1; }
    public String getType2() { return type2; }
    public void setType2(String type2) { this.type2 = type2 != null ? type2 : ""; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description != null ? description : ""; }
    public boolean isCanEvolve() { return canEvolve; }
    public void setCanEvolve(boolean canEvolve) { this.canEvolve = canEvolve; }

    @Override
    public String toString() {
        return "Dex #" + dexNumber + " | " + name + " (" + region + ") [" + type1 +
                (type2 != null && !type2.isEmpty() ? ", " + type2 : "") + "] Evolves: " +
                (canEvolve ? "Yes" : "No") + "\nDescription: " + description;
    }

    // For file storage
    public String toFileString() {
        return dexNumber + "," + name + "," + region + "," + type1 + "," + type2 + "," +
                description.replace(",", ";") + "," + canEvolve;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o==null || getClass() != o.getClass()) return false;
        Pokemon p = (Pokemon) o;
        return dexNumber == p.dexNumber;
    }

    @Override
    public int hashCode() { return Integer.hashCode(dexNumber); }
}
