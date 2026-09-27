package by.msa.traveljournal.service;

import java.util.List;

import org.springframework.stereotype.Service;

import by.msa.traveljournal.dto.TripCreateDTO;
import by.msa.traveljournal.dto.TripDTO;
import by.msa.traveljournal.dto.TripUpdateDTO;
import by.msa.traveljournal.exception.ResourceNotFoundException;
import by.msa.traveljournal.mapper.TripMapper;
import by.msa.traveljournal.repository.TripRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TripService {

    private final TripRepository repository;
    private final TripMapper mapper;

    public List<TripDTO> findAll() {
        return repository.findAll().stream()
                .map(mapper::map)
                .toList();
    }

    public TripDTO findById(Long id) {
        return mapper.map(findTrip(id));
    }

    public TripDTO create(TripCreateDTO data) {
        var trip = mapper.map(data);
        return mapper.map(repository.save(trip));
    }

    public TripDTO update(Long id, TripUpdateDTO data) {
        var trip = findTrip(id);
        mapper.update(data, trip);
        return mapper.map(repository.save(trip));
    }

    public void delete(Long id) {
        var trip = findTrip(id);
        repository.delete(trip);
    }

    private by.msa.traveljournal.model.Trip findTrip(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Путешествие не найдено: " + id));
    }
}
