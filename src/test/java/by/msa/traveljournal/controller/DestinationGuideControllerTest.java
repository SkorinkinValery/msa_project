package by.msa.traveljournal.controller;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class DestinationGuideControllerTest {

    private final DestinationGuideController controller = new DestinationGuideController();

    @Test
    void shouldReturnKnownCountryGuide() {
        var result = controller.findByCountry("Италия");

        assertThat(result.capital()).isEqualTo("Рим");
        assertThat(result.currency()).isEqualTo("Евро");
    }

    @Test
    void shouldReturnDefaultGuideForUnknownCountry() {
        var result = controller.findByCountry("Исландия");

        assertThat(result.country()).isEqualTo("Исландия");
        assertThat(result.capital()).isEqualTo("Нет данных");
    }
}
