package systemarkitektur.uppgift3.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import systemarkitektur.uppgift3.dto.PetDTO;
import systemarkitektur.uppgift3.repository.PetRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PetServiceTest {

    PetRepository petRepository = new PetRepository();
    PetService petService = new PetService(petRepository);

    PetDTO testPetDTO1 = new PetDTO("Bella", "Dog",0,100);
    PetDTO testPetDTO2 = new PetDTO("Max", "Cat",0,100);

    @Test
    @DisplayName("Should return all pets in the repository")
    void getAllPets() {
        assertAll(
                () -> assertNotNull(petService.getAllPets()),
                () -> assertEquals(4, petService.getAllPets().size())
        );
    }

    @Test
    @DisplayName("Should return any pet by id")
    void getPetById() {
        assertAll(
                () -> assertNotNull(petService.getAllPets()),
                () -> assertEquals(testPetDTO1, petService.getPetById(1)),
                () -> assertEquals(testPetDTO2, petService.getPetById(2))
        );
    }

    @Test
    @DisplayName("Should create 2 new pet in the repository")
    void createPet() {
        assertAll(
                () -> assertEquals(4, petService.getAllPets().size()),
                () -> petService.createPet(testPetDTO2),
                () -> assertEquals(5, petService.getAllPets().size()),
                () -> petService.createPet(testPetDTO1),
                () -> assertEquals(6, petService.getAllPets().size()),
                () -> assertEquals(testPetDTO2, petService.getPetById(5)),
                () -> assertEquals(testPetDTO1, petService.getPetById(6)),
                () -> assertEquals(0, petService.getPetById(6).hungerLevel())
        );
    }

    @Test
    @DisplayName("Should feed a pet by id")
    void feedPetById() {
        petService.increaseHungerLevel(1);
        petService.increaseHungerLevel(1);
        assertEquals(20, petService.getPetById(1).hungerLevel());
        petService.feedPetById(1);
        assertEquals(10, petService.getPetById(1).hungerLevel());
    }

    @Test
    @DisplayName("Should play with a pet by id")
    void playWithPetById() {
        petService.decreaseHappiness(1);
        petService.decreaseHappiness(1);
        assertEquals(80, petService.getPetById(1).happiness().shortValue());
        petService.playWithPetById(1);
        assertEquals(90, petService.getPetById(1).happiness().shortValue());
    }

    @Test
    @DisplayName("Should get a sequence of pets")
    void getSequenceOfPets() {
        List<PetDTO> pets = petService.getSequenceOfPets(2L, 3);
        assertAll(
                () -> assertEquals(3, pets.size()),
                () -> assertEquals("Max", pets.getFirst().name()),
                () -> assertEquals("Charlie", pets.get(1).name()),
                () -> assertEquals("Lucy", pets.get(2).name())
        );
    }
}