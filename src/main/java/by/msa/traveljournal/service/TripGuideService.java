package by.msa.traveljournal.service;

import org.springframework.stereotype.Service;

import by.msa.traveljournal.client.DestinationGuideClient;
import by.msa.traveljournal.dto.TripGuideDTO;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TripGuideService {

    private final TripService tripService;
    private final DestinationGuideClient destinationGuideClient;

    public TripGuideDTO findByTripId(Long tripId) {
        var trip = tripService.findById(tripId);
        var destinationGuide = destinationGuideClient.findByCountry(trip.getCountry());
        return new TripGuideDTO(trip, destinationGuide);
    }
}
