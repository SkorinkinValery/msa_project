package by.msa.traveljournal.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import by.msa.traveljournal.dto.TripCreateDTO;
import by.msa.traveljournal.dto.TripDTO;
import by.msa.traveljournal.dto.TripUpdateDTO;
import by.msa.traveljournal.service.TripService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trips")
@Tag(name = "Путешествия", description = "CRUD-операции журнала путешествий")
public class TripController {

    private final TripService service;

    @GetMapping
    @Operation(summary = "Получить все путешествия")
    public List<TripDTO> index() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить путешествие по идентификатору")
    public TripDTO show(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Добавить путешествие")
    public TripDTO create(@Valid @RequestBody TripCreateDTO data) {
        return service.create(data);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Изменить путешествие")
    public TripDTO update(@PathVariable Long id, @RequestBody TripUpdateDTO data) {
        return service.update(id, data);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Удалить путешествие")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
