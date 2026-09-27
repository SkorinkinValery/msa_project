package by.msa.traveljournal.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import by.msa.traveljournal.dto.DestinationGuideDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/internal/destination-guides")
@Tag(name = "Справочник стран", description = "Учебный REST-сервис для вызова через Feign")
public class DestinationGuideController {

    @GetMapping("/{country}")
    @Operation(summary = "Получить краткую справку о стране")
    public DestinationGuideDTO findByCountry(@PathVariable String country) {
        return switch (country.toLowerCase()) {
            case "беларусь" -> new DestinationGuideDTO(
                    "Беларусь", "Минск", "Белорусский рубль", "Русский и белорусский");
            case "италия" -> new DestinationGuideDTO(
                    "Италия", "Рим", "Евро", "Итальянский");
            default -> new DestinationGuideDTO(
                    country, "Нет данных", "Нет данных", "Нет данных");
        };
    }
}
