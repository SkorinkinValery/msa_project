package by.msa.traveljournal.dto;

import java.time.LocalDate;

import org.openapitools.jackson.nullable.JsonNullable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TripUpdateDTO {

    private JsonNullable<String> title = JsonNullable.undefined();
    private JsonNullable<String> country = JsonNullable.undefined();
    private JsonNullable<String> city = JsonNullable.undefined();
    private JsonNullable<String> description = JsonNullable.undefined();
    private JsonNullable<LocalDate> travelDate = JsonNullable.undefined();
}
