package by.msa.traveljournal.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TripCreateDTO {

    @NotBlank
    private String title;

    @NotBlank
    private String country;

    @NotBlank
    private String city;

    private String description;

    @NotNull
    private LocalDate travelDate;
}
