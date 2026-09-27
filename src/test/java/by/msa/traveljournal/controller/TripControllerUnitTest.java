package by.msa.traveljournal.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import by.msa.traveljournal.config.JacksonConfig;
import by.msa.traveljournal.dto.TripCreateDTO;
import by.msa.traveljournal.dto.TripDTO;
import by.msa.traveljournal.dto.TripUpdateDTO;
import by.msa.traveljournal.service.TripService;

@WebMvcTest(TripController.class)
@Import(JacksonConfig.class)
class TripControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TripService service;

    @MockitoBean
    private JpaMetamodelMappingContext jpaMetamodelMappingContext;

    @Test
    void shouldCreateTrip() throws Exception {
        given(service.create(any(TripCreateDTO.class))).willReturn(tripDTO());

        mockMvc.perform(post("/api/trips")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                  "title": "Летний отпуск",
                                  "country": "Италия",
                                  "city": "Рим",
                                  "description": "Колизей",
                                  "travelDate": "2026-07-15"
                                }
                                """))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Летний отпуск"))
                .andExpect(jsonPath("$.city").value("Рим"));
    }

    @Test
    void shouldGetAllTrips() throws Exception {
        given(service.findAll()).willReturn(List.of(tripDTO()));

        mockMvc.perform(get("/api/trips"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].country").value("Италия"));
    }

    @Test
    void shouldGetTripById() throws Exception {
        given(service.findById(1L)).willReturn(tripDTO());

        mockMvc.perform(get("/api/trips/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.travelDate").value("2026-07-15"));
    }

    @Test
    void shouldUpdateTrip() throws Exception {
        var updatedTrip = tripDTO();
        updatedTrip.setCity("Милан");
        given(service.update(eq(1L), any(TripUpdateDTO.class))).willReturn(updatedTrip);

        mockMvc.perform(put("/api/trips/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"city\":\"Милан\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.city").value("Милан"))
                .andExpect(jsonPath("$.country").value("Италия"));
    }

    @Test
    void shouldDeleteTrip() throws Exception {
        mockMvc.perform(delete("/api/trips/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(service).delete(1L);
    }

    private TripDTO tripDTO() {
        var trip = new TripDTO();
        trip.setId(1L);
        trip.setTitle("Летний отпуск");
        trip.setCountry("Италия");
        trip.setCity("Рим");
        trip.setDescription("Колизей");
        trip.setTravelDate(LocalDate.of(2026, 7, 15));
        trip.setCreatedAt(LocalDateTime.of(2026, 1, 1, 12, 0));
        trip.setUpdatedAt(LocalDateTime.of(2026, 1, 1, 12, 0));
        return trip;
    }
}
