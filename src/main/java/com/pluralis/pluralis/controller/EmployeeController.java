package com.pluralis.pluralis.controller;

import com.pluralis.pluralis.dto.EmployeeRequestDTO;
import com.pluralis.pluralis.dto.EmployeeResponseDTO;
import com.pluralis.pluralis.model.Employee;
import com.pluralis.pluralis.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public List<EmployeeResponseDTO> getAllEmployees() {
        return employeeService.getAllEmployees()
                .stream()
                .map(EmployeeResponseDTO::new)
                .collect(Collectors.toList());
    }

    @PostMapping
    public EmployeeResponseDTO saveEmployee(@Valid @RequestBody EmployeeRequestDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setName(employeeDTO.getName());
        employee.setEmail(employeeDTO.getEmail());
        employee.setGender(employeeDTO.getGender());
        employee.setEthnicity(employeeDTO.getEthnicity());
        employee.setNeurodivergent(employeeDTO.getNeurodivergent());
        employee.setLgbtqia(employeeDTO.getLgbtqia());

        Employee saved = employeeService.saveEmployee(employee);
        return new EmployeeResponseDTO(saved);
    }
}
