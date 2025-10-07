package com.pluralis.pluralis.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pluralis.pluralis.config.JwtAuthenticationFilter;
import com.pluralis.pluralis.dto.TrainingParticipationDTO;
import com.pluralis.pluralis.model.Employee;
import com.pluralis.pluralis.model.Training;
import com.pluralis.pluralis.model.TrainingParticipation;
import com.pluralis.pluralis.service.EmployeeService;
import com.pluralis.pluralis.service.TrainingParticipationService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TrainingParticipationController.class)
@AutoConfigureMockMvc(addFilters = false)
class TrainingParticipationControllerTest {

    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper om;

    @MockBean
    TrainingParticipationService participationService;
    @MockBean
    EmployeeService employeeService;
    @MockBean
    TrainingService trainingService;

    @MockBean
    JwtAuthenticationFilter jwtAuthenticationFilter;

    @InjectMocks
    TrainingParticipationController participationController;

    @Test
    @DisplayName("Given valid participation DTO When register Then returns saved entity")
    void givenValidDto_whenRegister_thenReturnSaved() throws Exception {
        // Given
        TrainingParticipationDTO dto = new TrainingParticipationDTO();
        dto.setEmployeeId(10L);
        dto.setTrainingId(20L);
        dto.setCompleted(true);

        Employee emp = new Employee();
        emp.setId(10L);
        emp.setName("My");

        Training tr = new Training();
        tr.setId(20L);
        tr.setName("Inclusão 101");

        TrainingParticipation saved = new TrainingParticipation();
        saved.setId(99L);
        saved.setEmployee(emp);
        saved.setTraining(tr);
        saved.setCompleted(true);

        given(employeeService.findById(10L)).willReturn(emp);
        given(trainingService.findById(20L)).willReturn(tr);
        given(participationService.registerParticipation(any(TrainingParticipation.class))).willReturn(saved);

        // When / Then
        mvc.perform(post("/training-participation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(99))
                .andExpect(jsonPath("$.employee.id").value(10))
                .andExpect(jsonPath("$.training.id").value(20))
                .andExpect(jsonPath("$.completed").value(true));
    }
}
