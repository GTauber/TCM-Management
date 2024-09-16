package college.pb.managementunitapi.model.dto;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@JsonInclude(NON_NULL)
public record StoreDto(
    Long id,
    @NotBlank(message = "Address is required")
    @Size(min = 4, max = 100, message = "Address should have between 4 and 100 characters")
    String address,

    @NotBlank(message = "Zip code is required")
    @Size(min = 4, max = 10, message = "Zip code should have between 4 and 10 characters")
    String zipCode,

    @NotBlank(message = "City is required")
    @Size(min = 4, max = 50, message = "City should have between 4 and 25 characters")
    String city

) {}
