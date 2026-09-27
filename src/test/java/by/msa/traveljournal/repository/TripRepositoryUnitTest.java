package by.msa.traveljournal.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import by.msa.traveljournal.model.Trip;

@DataJpaTest
class TripRepositoryUnitTest {

    @Autowired
    private TripRepository repository;

    @Test
    void shouldSaveTrip() {
        var savedTrip = repository.saveAndFlush(newTrip());

        assertThat(savedTrip.getId()).isPositive();
        assertThat(savedTrip.getCreatedAt()).isNotNull();
    }

    @Test
    void shouldFindTripById() {
        var savedTrip = repository.saveAndFlush(newTrip());

        var foundTrip = repository.findById(savedTrip.getId());

        assertThat(foundTrip)
                .isPresent()
                .get()
                .extracting(Trip::getCity)
                .isEqualTo("Рим");
    }

    @Test
    void shouldFindAllTrips() {
        repository.saveAndFlush(newTrip());

        var trips = repository.findAll();

        assertThat(trips).hasSize(1);
        assertThat(trips.get(0).getCountry()).isEqualTo("Италия");
    }

    @Test
    void shouldUpdateTrip() {
        var savedTrip = repository.saveAndFlush(newTrip());
        savedTrip.setCity("Милан");

        repository.saveAndFlush(savedTrip);

        assertThat(repository.findById(savedTrip.getId()))
                .isPresent()
                .get()
                .extracting(Trip::getCity)
                .isEqualTo("Милан");
    }

    @Test
    void shouldDeleteTrip() {
        var savedTrip = repository.saveAndFlush(newTrip());

        repository.deleteById(savedTrip.getId());
        repository.flush();

        assertThat(repository.findById(savedTrip.getId())).isEmpty();
    }

    private Trip newTrip() {
        var trip = new Trip();
        trip.setTitle("Летний отпуск");
        trip.setCountry("Италия");
        trip.setCity("Рим");
        trip.setDescription("Колизей и прогулка по центру");
        trip.setTravelDate(LocalDate.of(2026, 7, 15));
        return trip;
    }
}
