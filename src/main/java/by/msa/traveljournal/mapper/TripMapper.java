package by.msa.traveljournal.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import by.msa.traveljournal.dto.TripCreateDTO;
import by.msa.traveljournal.dto.TripDTO;
import by.msa.traveljournal.dto.TripUpdateDTO;
import by.msa.traveljournal.model.Trip;

@Mapper(
        uses = JsonNullableMapper.class,
        componentModel = MappingConstants.ComponentModel.SPRING,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface TripMapper {

    Trip map(TripCreateDTO dto);

    TripDTO map(Trip trip);

    void update(TripUpdateDTO dto, @MappingTarget Trip trip);
}
