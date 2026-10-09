package systemarkitektur.uppgift3;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import systemarkitektur.uppgift3.dto.PetDTO;
import systemarkitektur.uppgift3.service.PetService;

import java.util.Collections;
import java.util.List;

/**
 * The type Pet resource.
 */
@Path("/pets")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PetResource {

    private final PetService petService;

    /**
     * Instantiates a new Pet resource.
     *
     * @param petService the pet service
     */
    @Inject
    public PetResource(PetService petService) {
        this.petService = petService;
    }

    /**
     * Instantiates a new Pet resource. CDI used to create a proxy.
     */
    protected PetResource() {
        // Is demanded by CDI to be able to create a proxy.
        this.petService = null; // Silencing compiler warning
    }

    /**
     * Gets pets.
     *
     * @return the pets
     */
    @GET
    public List<PetDTO> getPets(
            @QueryParam("offset") Integer offset,
            @QueryParam("limit") Integer limit,
            @QueryParam("sortBy") String sortBy,
            @QueryParam("order") String order) {
        assert petService != null;
        boolean sequence = (offset != null && limit != null && sortBy == null && order == null) ;
        boolean sorting = (offset == null && limit == null && sortBy != null && order != null);
        String sortType = sorting ? "sorted" : "all";
        String whatToGet = sequence ? "sequence" : sortType;
        switch (whatToGet) {
            case "sequence":
                if (offset <= 0 || limit <= 0) {
                    return Collections.emptyList();
                } else {
                    return petService.getSequenceOfPets((long) offset, limit);
                }

            case "sorted":
                return petService.getSortedPets(sortBy, order);

            case "all":
                return petService.getAllPets();

            default:
                break;
        }
        if (!sequence && !sorting) {
            return petService.getAllPets();
        } else if (sequence && (offset <= 0 || limit <= 0)) {
            return Collections.emptyList();
        } else {
            return petService.getSequenceOfPets((long) offset, limit);
        }
    }

    /**
     * Create pet.
     *
     * @param petDTO the pet dto
     * @return the pet dto
     */
    @POST
    // @Consumes(MediaType.APPLICATION_JSON)
    public PetDTO createPet(@Valid PetDTO petDTO) {
        assert petService != null;
        return petService.createPet(petDTO);
    }

    /**
     * Gets pet by id.
     *
     * @param id the id
     * @return the pet by id
     */
    @Path("/{id}")
    @GET
    public PetDTO getPetById(@PathParam("id") int id) {
        assert petService != null;
        return petService.getPetById(id);
    }

    /**
     * Delete pet.
     *
     * @param id the id
     * @return the pet dto
     */
    @Path("/{id}")
    @DELETE
    public PetDTO deletePet(@PathParam("id") int id) {
        assert petService != null;
        return petService.deletePetById(id);
    }

    /**
     * Feed pet.
     *
     * @param id the id
     * @return the pet dto
     */
    @Path("{id}/feed")
    @PUT
    public PetDTO feedPet(@PathParam("id") int id) {
        assert petService != null;
        return petService.feedPetById(id);
    }

    /**
     * Play with a pet.
     *
     * @param id the id
     * @return the pet dto
     */
    @Path("{id}/play")
    @PUT
    public PetDTO playWithPet(@PathParam("id") int id) {
        assert petService != null;
        return petService.playWithPetById(id);
    }
}