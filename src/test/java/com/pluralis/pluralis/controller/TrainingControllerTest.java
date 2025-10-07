package com.pluralis.pluralis.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pluralis.pluralis.config.JwtAuthenticationFilter;
import com.pluralis.pluralis.dto.TrainingDTO;
import com.pluralis.pluralis.model.Training;
import com.pluralis.pluralis.service.TrainingService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TrainingController.class)
@AutoConfigureMockMvc(addFilters = false)
class TrainingControllerTest {

    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper om;

    @MockBean
    TrainingService trainingService;

    @MockBean
    JwtAuthenticationFilter jwtAuthenticationFilter;

    @InjectMocks
    TrainingController trainingController;

    @Test
    @DisplayName("Given valid DTO When create training Then returns created training")
    void givenValidDto_whenCreate_thenReturnEntity() throws Exception {
        // Given
        TrainingDTO dto = new TrainingDTO();
        dto.setName("Inclusion 101");
        dto.setDate(LocalDate.of(2025, 10, 1));
        dto.setMandatory(true);

        Training created = new Training();
        created.setId(7L);
        created.setName(dto.getName());
        created.setTrainingDate(dto.getDate());
        created.setMandatory(dto.getMandatory());

        given(trainingService.createTraining(any(Training.class))).willReturn(created);

        // When / Then
        mvc.perform(post("/trainings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(7))
                .andExpect(jsonPath("$.name").value("Inclusion 101"))
                .andExpect(jsonPath("$.mandatory").value(true));
    }

    @Test
    @DisplayName("Given stored trainings When listing Then returns array")
    void givenStoredTrainings_whenList_thenArray() throws Exception {
        // Given
        Training t = new Training();
        t.setId(1L);
        t.setName("A");
        t.setTrainingDate(LocalDate.now());
        t.setMandatory(false);
        given(trainingService.getAllTrainings()).willReturn(List.of(t));

        // When / Then
        mvc.perform(get("/trainings"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("A"));
    }
}
