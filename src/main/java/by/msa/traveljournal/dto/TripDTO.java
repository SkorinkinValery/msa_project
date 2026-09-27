package by.msa.traveljournal.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TripDTO {

    private Long id;
    private String title;
    private String country;
    private String city;
    private String description;
    private LocalDate travelDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
