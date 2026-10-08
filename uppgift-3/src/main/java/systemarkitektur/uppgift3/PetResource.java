package systemarkitektur.uppgift3;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import systemarkitektur.uppgift3.dto.PetDTO;
import systemarkitektur.uppgift3.service.PetService;

import java.util.List;

@Path("/pets")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PetResource {

    private final PetService petService;

    @Inject
    public PetResource(PetService petService) {
        this.petService = petService;
    }
    protected PetResource() {
        // Is demanded by CDI to be able to create a proxy.
        this.petService = null; // Silencing compiler warning
    }
    @GET
    public List<PetDTO> getPets() {
        assert petService != null;
        return petService.getAllPets();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public void createPet(@Valid PetDTO petDTO) {
        assert petService != null;
        petService.createPet(petDTO);
    }

    @Path("/{id}")
    @GET
    public PetDTO getPetById(@PathParam("id") int id) {
        assert petService != null;
        return petService.getPetById(id);
    }

    @Path("/{id}")
    @DELETE
    public void deletePet(@PathParam("id") int id) {
        assert petService != null;
        petService.deletePetById(id);
    }

    @Path("{id}/feed")
    @PUT
    public int feedPet(@PathParam("id") int id) {
        assert petService != null;
        petService.feedPetById(id);
        return petService.getPetById(id).hungerLevel();
    }

    @Path("{id}/play")
    @PUT
    public int playWithPet(@PathParam("id") int id) {
        assert petService != null;
        petService.playWithPetById(id);
        return getPetById(id).happiness();
    }
}