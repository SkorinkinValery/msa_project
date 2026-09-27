package by.msa.traveljournal.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.jackson.nullable.JsonNullable;

import by.msa.traveljournal.dto.TripCreateDTO;
import by.msa.traveljournal.dto.TripDTO;
import by.msa.traveljournal.dto.TripUpdateDTO;
import by.msa.traveljournal.exception.ResourceNotFoundException;
import by.msa.traveljournal.mapper.TripMapper;
import by.msa.traveljournal.model.Trip;
import by.msa.traveljournal.repository.TripRepository;

@ExtendWith(MockitoExtension.class)
class TripServiceUnitTest {

    @Mock
    private TripRepository repository;

    @Mock
    private TripMapper mapper;

    @InjectMocks
    private TripService service;

    private Trip trip;
    private TripDTO tripDTO;

    @BeforeEach
    void setUp() {
        trip = new Trip();
        trip.setId(1L);
        trip.setTitle("Летний отпуск");
        trip.setCountry("Италия");
        trip.setCity("Рим");
        trip.setTravelDate(LocalDate.of(2026, 7, 15));

        tripDTO = new TripDTO();
        tripDTO.setId(1L);
        tripDTO.setTitle("Летний отпуск");
        tripDTO.setCountry("Италия");
        tripDTO.setCity("Рим");
        tripDTO.setTravelDate(LocalDate.of(2026, 7, 15));
    }

    @Test
    void shouldCreateTrip() {
        var createDTO = new TripCreateDTO();
        createDTO.setTitle("Летний отпуск");
        createDTO.setCountry("Италия");
        createDTO.setCity("Рим");
        createDTO.setTravelDate(LocalDate.of(2026, 7, 15));
        given(mapper.map(createDTO)).willReturn(trip);
        given(repository.save(trip)).willReturn(trip);
        given(mapper.map(trip)).willReturn(tripDTO);

        var result = service.create(createDTO);

        assertThat(result).isSameAs(tripDTO);
        verify(repository).save(trip);
    }

    @Test
    void shouldGetTripById() {
        given(repository.findById(1L)).willReturn(Optional.of(trip));
        given(mapper.map(trip)).willReturn(tripDTO);

        var result = service.findById(1L);

        assertThat(result.getCity()).isEqualTo("Рим");
    }

    @Test
    void shouldGetAllTrips() {
        given(repository.findAll()).willReturn(List.of(trip));
        given(mapper.map(trip)).willReturn(tripDTO);

        var result = service.findAll();

        assertThat(result).containsExactly(tripDTO);
    }

    @Test
    void shouldUpdateTrip() {
        var updateDTO = new TripUpdateDTO();
        updateDTO.setCity(JsonNullable.of("Милан"));
        tripDTO.setCity("Милан");
        given(repository.findById(1L)).willReturn(Optional.of(trip));
        given(repository.save(trip)).willReturn(trip);
        given(mapper.map(trip)).willReturn(tripDTO);

        var result = service.update(1L, updateDTO);

        verify(mapper).update(updateDTO, trip);
        verify(repository).save(trip);
        assertThat(result.getCity()).isEqualTo("Милан");
    }

    @Test
    void shouldDeleteTrip() {
        given(repository.findById(1L)).willReturn(Optional.of(trip));

        service.delete(1L);

        verify(repository).delete(trip);
    }

    @Test
    void shouldThrowExceptionWhenTripDoesNotExist() {
        given(repository.findById(99L)).willReturn(Optional.empty());

        assertThatThrownBy(() -> service.findById(99L))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("Путешествие не найдено: 99");
    }
}
