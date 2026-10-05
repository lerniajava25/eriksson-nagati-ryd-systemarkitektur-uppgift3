package systemarkitektur.uppgift3.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.NotFoundException;
import systemarkitektur.uppgift3.dto.PetDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.LongStream;

/**
 * The type Pet repository.
 */
@ApplicationScoped
public class PetRepository {
    private final ConcurrentHashMap<Long, PetDTO> pets = new ConcurrentHashMap<>();
    private final AtomicLong id = new AtomicLong(1);

    /**
     * Instantiates a new Pet repository.
     * (with some test data)
     */
    @Inject
    public PetRepository() {
        // Loading some test data
        createPet("Bella", "Dog");
        createPet("Max", "Cat");
        createPet("Charlie", "Dog");
        createPet("Lucy", "Cat");
    }

    /**
     * Create a pet from name, species, hungerLevel, happiness.
     *
     * @param name        the name
     * @param species     the species
     * @param hungerLevel the hunger level
     * @param happiness   the happiness level
     * @return the pet dto
     */
    public PetDTO createPet(String name, String species, int hungerLevel, int happiness) {
        long newId = id.getAndIncrement();
        PetDTO newPetDTO = new @Valid PetDTO(name, species, hungerLevel, happiness);
        pets.put(newId, newPetDTO);
        return newPetDTO;
    }

    /**
     * Create a pet from name and species.
     *
     * @param name    the name
     * @param species the species
     * @return the pet
     */
    public PetDTO createPet(String name, String species) {
        return createPet(name, species, 0, 100);
    }

    /**
     * Create a pet from a pet dto.
     *
     * @param petdto the pet
     * @return the pet as PetDTO
     */
    public PetDTO createPet(@Valid PetDTO petdto) {
        return createPet(petdto.name(), petdto.species(), petdto.hungerLevel(), petdto.happiness());
    }

    /**
     * Gets all pets.
     *
     * @return all pets in the repository
     */
    public List<PetDTO> getAllPets() {
        return new ArrayList<>(pets.values());
    }

    /**
     * Gets a sequence of pets.
     *
     * @param id             the id, where the sequence starts
     * @param sequenceLength the sequence length
     * @return the sequence of pets
     */
    public List<PetDTO> getSequenceOfPets(long id, int sequenceLength) {
        return LongStream.range(id, id + sequenceLength)
                .mapToObj(pets::get)
                .filter(Objects::nonNull)
                .toList();
    }

    /**
     * Gets pet by id.
     *
     * @param id the id
     * @return the pet by id
     */
    public PetDTO getPetById(long id) {
        PetDTO pet = pets.get(id);
        if (pet == null) {
        throw new NotFoundException("Pet with id " + id + " does not exist");
        }
        return pet;
    }


    /**
     * Delete pet by id.
     *
     * @param id the id
     */
    public void deletePetById(long id) {
        pets.remove(id);
    }

    /**
     * Feed pet by id.
     *
     * @param id the id
     */
    public void feedPetById(long id) {
        PetDTO updatedPet = pets.computeIfPresent(id, (key, pet) -> {
            int hungerLevel = Math.clamp(pet.hungerLevel() - 10L, 0, 100);
            return new @Valid PetDTO(pet.name(), pet.species(), hungerLevel, pet.happiness());
        });
        if (updatedPet == null) {
            throw new NotFoundException("Pet with id " + id + " does not exist");
        }
    }

    /**
     * Play with pet by id.
     *
     * @param id the id
     */
    public void playWithPetById(long id) {
        PetDTO updatedPet = pets.computeIfPresent(id, (key, pet) -> {
            int happiness = Math.clamp(pet.happiness() + 10L, 0, 100);
            return new @Valid PetDTO(pet.name(), pet.species(), pet.hungerLevel(), happiness);
        });
        if (updatedPet == null) {
            throw new NotFoundException("Pet with id " + id + " does not exist");
        }
    }

    /**
     * Increase a pet's hunger level.
     *
     * @param id the id
     */
    public void increaseHungerLevel(long id) {
        PetDTO updatedPet = pets.computeIfPresent(id, (key, pet) -> {
            int hungerLevel = Math.clamp(pet.hungerLevel() + 10L, 0, 100);
            return new PetDTO(pet.name(), pet.species(), hungerLevel, pet.happiness());
        });
        if (updatedPet == null) {
            throw new NotFoundException("Pet with id " + id + " does not exist");
        }
    }

    /**
     * Decrease a pet's happiness level.
     *
     * @param id the id
     */
    public void decreaseHappiness(long id) {
        PetDTO updatedPet = pets.computeIfPresent(id, (key, pet) -> {
            int happinessLevel = Math.clamp(pet.happiness() - 10L, 0, 100);
            return new PetDTO(pet.name(), pet.species(), pet.hungerLevel(), happinessLevel);
        });
        if (updatedPet == null) {
            throw new NotFoundException("Pet with id " + id + " does not exist");
        }
    }
}
