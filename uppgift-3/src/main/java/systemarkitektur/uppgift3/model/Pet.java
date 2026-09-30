package systemarkitektur.uppgift3.model;

public class Pet {
    private final String name;
    private final String species;
    private int hungerLevel;
    private int happiness;

    public Pet(String name, String species) {
        this.name = name;
        this.species = species;
        this.hungerLevel = 0;
        this.happiness = 100;
    }

    public int getHungerLevel() {
        return hungerLevel;
    }

    public void setHungerLevel(int hungerLevel) {
        this.hungerLevel = hungerLevel;
    }

    public int getHappiness() {
        return happiness;
    }

    public void setHappiness(int happiness) {
        this.happiness = happiness;
    }

    public String getName() {
        return name;
    }

    public String getSpecies() {
        return species;
    }
}
