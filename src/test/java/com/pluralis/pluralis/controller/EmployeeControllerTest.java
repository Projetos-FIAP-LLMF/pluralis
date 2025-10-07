package com.pluralis.pluralis.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pluralis.pluralis.config.JwtAuthenticationFilter;
import com.pluralis.pluralis.dto.EmployeeRequestDTO;
import com.pluralis.pluralis.model.Employee;
import com.pluralis.pluralis.service.EmployeeService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
@AutoConfigureMockMvc(addFilters = false)
class EmployeeControllerTest {

    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper om;

    @MockBean
    EmployeeService employeeService;

    @MockBean
    JwtAuthenticationFilter jwtAuthenticationFilter;

    @InjectMocks
    EmployeeController employeeController;

    @Test
    @DisplayName("Given employees exist When GET /employees Then returns list DTO")
    void givenEmployees_whenGetAll_thenList() throws Exception {
        // Given
        Employee e1 = new Employee();
        e1.setId(1L);
        e1.setName("A");
        e1.setEmail("a@a.com");
        e1.setGender("F");
        e1.setEthnicity("Parda");
        e1.setNeurodivergent(true);
        e1.setLgbtqia(false);

        Employee e2 = new Employee();
        e2.setId(2L);
        e2.setName("B");
        e2.setEmail("b@b.com");
        e2.setGender("M");
        e2.setEthnicity("Branca");
        e2.setNeurodivergent(false);
        e2.setLgbtqia(true);

        given(employeeService.getAllEmployees()).willReturn(List.of(e1, e2));

        // When / Then
        mvc.perform(get("/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("A"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].lgbtqia").value(true));
    }

    @Test
    @DisplayName("Given valid payload When POST /employees Then returns created DTO")
    void givenValidPayload_whenSave_thenReturnDto() throws Exception {
        // Given
        EmployeeRequestDTO req = new EmployeeRequestDTO();
        req.setName("My");
        req.setEmail("my@ex.com");
        req.setGender("F");
        req.setEthnicity("Parda");
        req.setNeurodivergent(true);
        req.setLgbtqia(false);

        Employee saved = new Employee();
        saved.setId(10L);
        saved.setName(req.getName());
        saved.setEmail(req.getEmail());
        saved.setGender(req.getGender());
        saved.setEthnicity(req.getEthnicity());
        saved.setNeurodivergent(req.getNeurodivergent());
        saved.setLgbtqia(req.getLgbtqia());

        given(employeeService.saveEmployee(any(Employee.class))).willReturn(saved);

        // When / Then
        mvc.perform(post("/employees")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(10))
                .andExpect(jsonPath("$.name").value("My"))
                .andExpect(jsonPath("$.neurodivergent").value(true));
    }
}
