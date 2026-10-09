package systemarkitektur.uppgift3.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Set;

import jakarta.validation.Valid;
import systemarkitektur.uppgift3.repository.PetRepository;
import systemarkitektur.uppgift3.dto.PetDTO;

/**
 * The type Pet service.
 */
@ApplicationScoped
public class PetService {
    private final PetRepository petRepository;

    /**
     * Instantiates a new Pet service.
     *
     * @param petRepository the pet repository
     */
    @Inject
    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    /**
     * Instantiates a new Pet service.
     */
    protected PetService() {
        // Is demanded by CDI to be able to create a proxy.
        this.petRepository = null; // Silencing compiler warning
    }

    /**
     * Gets all pets.
     *
     * @return all pets as a list of PetDTO
     */
    public List<PetDTO> getAllPets() {
        assert petRepository != null;
        return petRepository.getAllPets().stream()
                .toList();
    }

    private static final Set<String> ALLOWED_SORT_FIELD = Set.of("name", "species", "hunger", "happiness");

    public List<PetDTO> getSortedPets(String sortBy, String order) {
        assert petRepository != null;
        String key = sortBy == null ? "" : sortBy.toLowerCase();
        String safeSortValue = ALLOWED_SORT_FIELD.contains(key) ? key : "id";
        boolean isAscending = "asc".equalsIgnoreCase(order);
        return petRepository.getSortedPets(safeSortValue, isAscending)
                .stream()
                .toList();
    }

    /**
     * Gets pet by id.
     *
     * @param id the id
     * @return the pet by id as a PetDTO
     */
    public PetDTO getPetById(int id) {
        assert petRepository != null;
        return petRepository.getPetById(id);
    }


    /**
     * Create pet from a petdto.
     *
     * @param petdto the petDTO
     * @return the pet dto
     */
    public PetDTO createPet(@Valid PetDTO petdto) {
        assert petRepository != null;
        return petRepository.savePet(petdto);
    }

    /**
     * Delete pet by id.
     *
     * @param id the id
     */
    public PetDTO deletePetById(int id) {
        assert petRepository != null;
        return petRepository.deletePetById(id);
    }

    /**
     * Feed pet by id.
     *
     * @param id the id
     */
    public PetDTO feedPetById(int id) {
        assert petRepository != null;
        return petRepository.feedPetById(id);
    }

    /**
     * Increase hunger level.
     *
     * @param id the id
     */
    public PetDTO increaseHungerLevel(int id) {
        assert petRepository != null;
        return petRepository.increaseHungerLevel(id);
    }

    /**
     * Play with pet by id.
     *
     * @param id the id
     */
    public PetDTO playWithPetById(int id) {
        assert petRepository != null;
        return petRepository.playWithPetById(id);
    }

    /**
     * Decrease a pet's happiness.
     *
     * @param id the id
     */
    public PetDTO decreaseHappiness(int id) {
        assert petRepository != null;
        return petRepository.decreaseHappiness(id);
    }

    /**
     * Gets a sequence of pets from id to id + sequenceLength.
     *
     * @param id             the id
     * @param sequenceLength the sequence length
     * @return the sequence of pets
     */
    public List<PetDTO> getSequenceOfPets(Long id, int sequenceLength) {
        assert petRepository != null;
        return petRepository.getSequenceOfPets(id, sequenceLength);
    }

}

