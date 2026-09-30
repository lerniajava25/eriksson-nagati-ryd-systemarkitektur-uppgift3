package systemarkitektur.uppgift3.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

import systemarkitektur.uppgift3.model.Pet;
import systemarkitektur.uppgift3.repository.PetRepository;

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
     * Gets all pets.
     *
     * @return the all pets as a list of PetDTO
     */
    public List<PetDTO> getAllPets() {
        return petRepository.getAllPets().stream()
                .map(this::mapToPetDto)
                .toList();
    }

    /**
     * Gets pet by id.
     *
     * @param id the id
     * @return the pet by id as a PetDTO
     */
    public PetDTO getPetById(int id) {
        return mapToPetDto(petRepository.getPetById(id));
    }

    /**
     * Create a new pet.
     *
     * @param name    the name
     * @param species the species
     * @return the new pet as a petDTO
     */
    public PetDTO createPet(String name, String species) {
        Pet newPet = petRepository.savePet(name, species);
        return mapToPetDto(newPet);
    }

    /**
     * Feed pet by id.
     *
     * @param id the id
     */
    public void feedPetById(int id) {
        if (!petRepository.feedPetById(id)) {
            throw new IllegalArgumentException("Pet with id " + id + " does not exist");
        }
    }

    /**
     * Play with pet by id.
     *
     * @param id the id
     */
    public void playWithPetById(int id) {
        if (!petRepository.playWithPetById(id)) {
            throw new IllegalArgumentException("Pet with id " + id + " does not exist");
        }
    }

    // Private method that maps a Pet object to a PetDTO object.
    private PetDTO mapToPetDto(Pet pet) {
        return new PetDTO(
                pet.getName(),
                pet.getSpecies(),
                pet.getHungerLevel(),
                pet.getHappiness()
        );
    }
}

