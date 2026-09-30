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

    private PetDTO mapToPetDto(Pet pet) {
        return new PetDTO(
                pet.getName(),
                pet.getSpecies(),
                pet.getHungerLevel(),
                pet.getHappiness()
        );
    }
}

