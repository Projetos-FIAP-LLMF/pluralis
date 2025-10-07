package com.pluralis.pluralis.controller;

import com.pluralis.pluralis.config.JwtAuthenticationFilter;
import com.pluralis.pluralis.model.Employee;
import com.pluralis.pluralis.service.EmployeeService;
import com.pluralis.pluralis.service.TrainingParticipationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(InclusionReportController.class)
@AutoConfigureMockMvc(addFilters = false)
class InclusionReportControllerTest {

    @Autowired MockMvc mvc;

    @MockBean EmployeeService employeeService;
    @MockBean TrainingParticipationService participationService;

    @MockBean
    JwtAuthenticationFilter jwtAuthenticationFilter;

    @InjectMocks
    InclusionReportController inclusionReportController;

    @Test
    @DisplayName("Given employees and participations When GET report Then returns aggregated metrics")
    void givenData_whenGenerateReport_thenReturnAggregates() throws Exception {
        // Given
        Employee e = new Employee(); e.setId(1L);
        given(employeeService.getAllEmployees()).willReturn(List.of(e, new Employee()));
        given(participationService.getTotalParticipations()).willReturn(7L);

        // When / Then
        mvc.perform(get("/inclusion-report"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalEmployees").value(2))
                .andExpect(jsonPath("$.totalTrainingParticipations").value(7));
    }
}
