package by.msa.traveljournal.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import by.msa.traveljournal.dto.TripGuideDTO;
import by.msa.traveljournal.service.TripGuideService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trips")
@Tag(name = "Feign-клиент", description = "Получение данных из другого REST-сервиса")
public class TripGuideController {

    private final TripGuideService service;

    @GetMapping("/{id}/guide")
    @Operation(summary = "Получить путешествие со справкой о стране через Feign")
    public TripGuideDTO show(@PathVariable Long id) {
        return service.findByTripId(id);
    }
}
