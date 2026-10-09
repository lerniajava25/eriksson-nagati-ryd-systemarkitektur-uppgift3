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
        // Loading some testdata
        this.pets.put(id.getAndIncrement(), new PetDTO("Bella", "Dog", 0, 100));
        this.pets.put(id.getAndIncrement(), new PetDTO("Max", "Cat", 0, 100));
        this.pets.put(id.getAndIncrement(), new PetDTO("Charlie", "Dog", 0, 100));
        this.pets.put(id.getAndIncrement(), new PetDTO("Lucy", "Cat", 0, 100));
    }

    /**
     * Save pet.
     *
     * @param petDTO the input pet dto
     * @return a pet dto
     */
    public PetDTO savePet(PetDTO petDTO) {
        long newId = id.getAndIncrement();
        pets.put(newId, petDTO);
        return petDTO;
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
        return pets.keySet().stream()
                .filter(key -> key >= id && key < id + sequenceLength)
                .map(pets::get)
                .filter(Objects::nonNull)
                .toList();
    }

    /**
     * Gets sorted pets.
     *
     * @param sortBy      the sort by
     * @param isAscending sort order is ascending
     * @return the sorted pets
     */
    public List<PetDTO> getSortedPets(String sortBy, boolean isAscending) {
        return switch (sortBy) {
            case "name" -> new ArrayList<>(pets.values().stream()
                    .sorted((o1, o2) -> isAscending ? o1.name().compareTo(o2.name()) : o2.name().compareTo(o1.name()))
                    .toList());
            case "species" -> new ArrayList<>(pets.values().stream()
                    .sorted((o1, o2) -> isAscending ? o1.species().compareTo(o2.species()) : o2.species().compareTo(o1.species()))
                    .toList());
            case "hunger" -> new ArrayList<>(pets.values().stream()
                    .sorted((o1, o2) -> isAscending ? o1.hungerLevel().compareTo(o2.hungerLevel()) : o2.hungerLevel().compareTo(o1.hungerLevel()))
                    .toList());
            case "happiness" -> new ArrayList<>(pets.values().stream()
                    .sorted((o1, o2) -> isAscending ? o1.happiness().compareTo(o2.happiness()) : o2.happiness().compareTo(o1.happiness()))
                    .toList());
            default -> new ArrayList<>(pets.values());
        };
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
    public PetDTO deletePetById(long id) {
        return pets.remove(id);
    }

    /**
     * Feed pet by id.
     *
     * @param id the id
     */
    public PetDTO feedPetById(long id) {
        PetDTO updatedPet = pets.computeIfPresent(id, PetRepository::feedPet);
        if (updatedPet == null) {
            throw new NotFoundException("Pet with id " + id + " does not exist");
        }
        return updatedPet;
    }

    /**
     * Play with pet by id.
     *
     * @param id the id
     */
    public PetDTO playWithPetById(long id) {
        PetDTO updatedPet = pets.computeIfPresent(id, PetRepository::playPet);
        if (updatedPet == null) {
            throw new NotFoundException("Pet with id " + id + " does not exist");
        }
        return updatedPet;
    }

    /**
     * Increase a pet's hunger level.
     *
     * @param id the id
     */
    public PetDTO increaseHungerLevel(long id) {
        PetDTO updatedPet = pets.computeIfPresent(id, PetRepository::starvePet);
        if (updatedPet == null) {
            throw new NotFoundException("Pet with id " + id + " does not exist");
        }
        return updatedPet;
    }

    /**
     * Decrease a pet's happiness level.
     *
     * @param id the id
     */
    public PetDTO decreaseHappiness(long id) {
        PetDTO updatedPet = pets.computeIfPresent(id, PetRepository::moreSadPet);
        if (updatedPet == null) {
            throw new NotFoundException("Pet with id " + id + " does not exist");
        }
        return updatedPet;
    }

    // Private methods below ##############################################
    private static PetDTO playPet(Long key, PetDTO pet) {
        int happiness = Math.clamp(pet.happiness() + 10L, 0, 100);
        return new @Valid PetDTO(pet.name(), pet.species(), pet.hungerLevel(), happiness);
    }

    private static PetDTO feedPet(Long key, PetDTO pet) {
        int hungerLevel = Math.clamp(pet.hungerLevel() - 10L, 0, 100);
        return new @Valid PetDTO(pet.name(), pet.species(), hungerLevel, pet.happiness());
    }

    private static PetDTO starvePet(Long key, PetDTO pet) {
        int hungerLevel = Math.clamp(pet.hungerLevel() + 10L, 0, 100);
        return new PetDTO(pet.name(), pet.species(), hungerLevel, pet.happiness());
    }

    private static PetDTO moreSadPet(Long key, PetDTO pet) {
        int happinessLevel = Math.clamp(pet.happiness() - 10L, 0, 100);
        return new PetDTO(pet.name(), pet.species(), pet.hungerLevel(), happinessLevel);
    }
}
