package systemarkitektur.uppgift3.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import systemarkitektur.uppgift3.dto.PetDTO;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PetRepositoryTest {

    PetRepository petRepository = new PetRepository();
    @Test
    @DisplayName("Should return all pets in repository")
    void getAllPets() {
        assertNotNull(petRepository.getAllPets());
        assertEquals(4, petRepository.getAllPets().size());
    }

    @Test
    @DisplayName("Should return one pet by id")
    void getPetById() {
        assertNotNull(petRepository.getPetById(2));
        assertEquals("Max", petRepository.getPetById(2).name());
    }

    @Test
    @DisplayName("Should create a pet in the repository, 2 versions")
    void createPet() {
        PetDTO testPet = new PetDTO("Gustaf", "cat", 0,100);
        petRepository.createPet(testPet);
        int repoLength = petRepository.getAllPets().size();
        assertEquals("Gustaf", petRepository.getPetById(repoLength).name());
        assertEquals(5, repoLength);
        petRepository.createPet("Pelle", "dog");
        assertNotNull(petRepository.getPetById(repoLength + 1));
        assertEquals("Pelle", petRepository.getPetById(repoLength + 1).name());
    }

    @Test
    @DisplayName("Should return a sequence of pets from the repository")
    void getSequenceOfPets() {
        List<PetDTO> sequence = petRepository.getSequenceOfPets(2, 2);
        assertEquals(2, sequence.size());
        assertEquals("Max", sequence.get(0).name());
        assertEquals("Charlie", sequence.get(1).name());
    }
}
