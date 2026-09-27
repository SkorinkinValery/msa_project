package by.msa.traveljournal.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import by.msa.traveljournal.dto.DestinationGuideDTO;
import by.msa.traveljournal.dto.TripDTO;
import by.msa.traveljournal.dto.TripGuideDTO;
import by.msa.traveljournal.service.TripGuideService;

@WebMvcTest(TripGuideController.class)
class TripGuideControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TripGuideService service;

    @MockitoBean
    private JpaMetamodelMappingContext jpaMetamodelMappingContext;

    @Test
    void shouldReturnTripGuide() throws Exception {
        var trip = new TripDTO();
        trip.setId(1L);
        trip.setCountry("Италия");
        var guide = new DestinationGuideDTO("Италия", "Рим", "Евро", "Итальянский");
        given(service.findByTripId(1L)).willReturn(new TripGuideDTO(trip, guide));

        mockMvc.perform(get("/api/trips/{id}/guide", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.trip.id").value(1))
                .andExpect(jsonPath("$.destinationGuide.capital").value("Рим"));
    }
}
