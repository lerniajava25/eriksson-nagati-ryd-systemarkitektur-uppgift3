package systemarkitektur.uppgift3.model;


/**
 * The type Pet.
 */
public class Pet {
    private final String name;
    private final String species;
    private int hungerLevel;
    private int happiness;

    /**
     * Instantiates a new Pet.
     *
     * @param name    the name
     * @param species the species
     */
    public Pet(String name, String species) {
        this.name = name;
        this.species = species;
        this.hungerLevel = 0;
        this.happiness = 100;
    }

    /**
     * Gets hunger level.
     *
     * @return the hunger level
     */
    public int getHungerLevel() {
        return hungerLevel;
    }

    /**
     * Sets hunger level.
     *
     * @param hungerLevel the hunger level
     */
    public void setHungerLevel(int hungerLevel) {
        this.hungerLevel = Math.max(0, Math.min(hungerLevel, 100));
    }

    /**
     * Gets happiness.
     *
     * @return the happiness
     */
    public int getHappiness() {
        return happiness;
    }

    /**
     * Sets happiness.
     *
     * @param happiness the happiness
     */
    public void setHappiness(int happiness) {
        this.happiness = Math.max(0, Math.min(happiness, 100));
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets species.
     *
     * @return the species
     */
    public String getSpecies() {
        return species;
    }
}
