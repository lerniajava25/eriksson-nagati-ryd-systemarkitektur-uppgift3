package systemarkitektur.uppgift3.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.NotFoundException;
import systemarkitektur.uppgift3.dto.PetDTO;

import java.util.*;
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
     * @param isAscending the sort order is ascending
     * @return the sorted pets
     */
    public List<PetDTO> getSortedPets(String sortBy, boolean isAscending) {
        Comparator<Map.Entry<Long, PetDTO>> comparator = switch (sortBy.toLowerCase()) {
            case "id" -> Map.Entry.comparingByKey();
            case "name" -> Comparator.comparing(e -> e.getValue().name(), String.CASE_INSENSITIVE_ORDER);
            case "species" -> Comparator.comparing(e -> e.getValue().species(), String.CASE_INSENSITIVE_ORDER);
            case "hunger" -> Comparator.comparing(e -> e.getValue().hungerLevel());
            case "happiness" -> Comparator.comparing(e -> e.getValue().happiness());
            default -> Map.Entry.comparingByKey();
        };
        if (!isAscending) {
            comparator = comparator.reversed();
        }
        return pets.entrySet().stream()
                .sorted(comparator)
                .map(Map.Entry::getValue)
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
