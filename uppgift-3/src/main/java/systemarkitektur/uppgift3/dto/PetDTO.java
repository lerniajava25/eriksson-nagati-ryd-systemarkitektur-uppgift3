package systemarkitektur.uppgift3.dto;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
public record PetDTO(

        @NotBlank(message = "Name must not be blank")
        String name,

        @NotBlank(message = "Species must not be blank")
        String species,

        @NotNull(message = "Hunger level is required")
        @Min(value = 0, message = "Hunger level must be at least 0")
        @Max(value = 100, message = "Hunger level must not exceed 100")
        Integer hungerLevel,

        @NotNull(message = "Happiness is required")
        @Min(value = 0, message = "Happiness must be at least 0")
        @Max(value = 100, message = "Happiness must not exceed 100")
        Integer happiness

) {
}