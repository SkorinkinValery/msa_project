package by.msa.traveljournal.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import by.msa.traveljournal.client.DestinationGuideClient;
import by.msa.traveljournal.dto.DestinationGuideDTO;
import by.msa.traveljournal.dto.TripDTO;

@ExtendWith(MockitoExtension.class)
class TripGuideServiceTest {

    @Mock
    private TripService tripService;

    @Mock
    private DestinationGuideClient destinationGuideClient;

    @InjectMocks
    private TripGuideService service;

    @Test
    void shouldCombineTripWithDataReceivedThroughFeignClient() {
        var trip = new TripDTO();
        trip.setId(1L);
        trip.setCountry("Италия");
        var guide = new DestinationGuideDTO("Италия", "Рим", "Евро", "Итальянский");
        given(tripService.findById(1L)).willReturn(trip);
        given(destinationGuideClient.findByCountry("Италия")).willReturn(guide);

        var result = service.findByTripId(1L);

        assertThat(result.trip()).isSameAs(trip);
        assertThat(result.destinationGuide()).isSameAs(guide);
        verify(destinationGuideClient).findByCountry("Италия");
    }
}
