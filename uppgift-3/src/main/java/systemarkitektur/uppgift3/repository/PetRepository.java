package systemarkitektur.uppgift3.repository;

import jakarta.inject.Inject;
import systemarkitektur.uppgift3.model.Pet;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * The type Pet repository.
 */

public class PetRepository {
    private final ConcurrentHashMap<Long, Pet> pets = new ConcurrentHashMap<>();
    private final AtomicLong id = new AtomicLong(0);

    /**
     * Instantiates a new Pet repository.
     * (with some test data)
     */
    @Inject
    public PetRepository() {
        // Loading some test data
        savePet("Bella", "Dog");
        savePet("Max", "Cat");
        savePet("Charlie", "Dog");
        savePet("Lucy", "Cat");
    }

    /**
     * Create pet pet.
     *
     * @param name    the name
     * @param species the species
     * @return the pet
     */
    public Pet savePet(String name, String species) {
        long newId = id.getAndIncrement();
        Pet newPet = new Pet(name, species);
        pets.put(newId, newPet);
        return newPet;
    }

    /**
     * Gets all pets.
     *
     * @return all pets in the repository
     */
    public List<Pet> getAllPets() {
        return new ArrayList<>(pets.values());
    }

    /**
     * Gets pet by id.
     *
     * @param id the id
     * @return a pet by id
     */
    public Pet getPetById(long id) {
        return pets.get(id);
    }

    /**
     * Delete pet by id boolean.
     *
     * @param id the id
     * @return boolean
     * (true if the pet was deleted,
     * false otherwise)
     */
    public boolean deletePetById(long id) {
        int currentSize = pets.size();
        pets.remove(id);
        return currentSize != pets.size();
    }

    /**
     * Feed pet by id boolean.
     *
     * @param id the id
     * @return boolean
     * (true if the pet was fed,
     * false otherwise)
     */
    public boolean feedPetById(long id) {
        Pet pet = pets.get(id);
        if (pet != null) {
            pet.setHungerLevel(pet.getHungerLevel() - 10);
            return true;
        }
        return false;
    }

    /**
     * Play with pet by id boolean.
     *
     * @param id the id
     * @return boolean
     * (true if the pet was played
     * with successfully, false otherwise)
     */
    public boolean playWithPetById(long id) {
        Pet pet = pets.get(id);
        if (pet != null) {
            pet.setHappiness(pet.getHappiness() + 10);
            return true;
        }
        return false;
    }
}
