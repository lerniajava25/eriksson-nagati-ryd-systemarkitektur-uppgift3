package systemarkitektur.uppgift3.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.List;

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
     * Gets all pets.
     *
     * @return the all pets as a list of PetDTO
     */
    public List<PetDTO> getAllPets() {
        return petRepository.getAllPets().stream()
                .toList();
    }

    /**
     * Gets pet by id.
     *
     * @param id the id
     * @return the pet by id as a PetDTO
     */
    public PetDTO getPetById(int id) {
        return petRepository.getPetById(id);
    }

    /**
     * Create a new pet.
     *
     * @param name    the name
     * @param species the species
     * @return the new pet as a petDTO
     */
    public PetDTO createPet(String name, String species) {
        return petRepository.createPet(name, species);
    }

    /**
     * Create pet from a petdto.
     *
     * @param petdto the petDTO
     * @return the pet dto
     */
    public PetDTO createPet(@Valid PetDTO petdto) {
        return petRepository.createPet(petdto);
    }

    /**
     * Delete pet by id.
     *
     * @param id the id
     */
    public void deletePetById(int id) {
        petRepository.deletePetById(id);
    }

    /**
     * Feed pet by id.
     *
     * @param id the id
     */
    public void feedPetById(int id) {
        petRepository.feedPetById(id);
    }

    /**
     * Increase hunger level.
     *
     * @param id the id
     */
    public void increaseHungerLevel(int id) {
        petRepository.increaseHungerLevel(id);
    }

    /**
     * Play with pet by id.
     *
     * @param id the id
     */
    public void playWithPetById(int id) {
        petRepository.playWithPetById(id);
    }

    /**
     * Decrease a pet's happiness.
     *
     * @param id the id
     */
    public void decreaseHappiness(int id) {
        petRepository.decreaseHappiness(id);
    }

}

