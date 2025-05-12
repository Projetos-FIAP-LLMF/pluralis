package com.pluralis.pluralis.dto;

import com.pluralis.pluralis.model.Employee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponseDTO {
    private Long id;
    private String name;
    private String email;
    private String gender;
    private String ethnicity;
    private Boolean neurodivergent;
    private Boolean lgbtqia;

    public EmployeeResponseDTO(Employee employee) {
        this.id = employee.getId();
        this.name = employee.getName();
        this.email = employee.getEmail();
        this.gender = employee.getGender();
        this.ethnicity = employee.getEthnicity();
        this.neurodivergent = employee.getNeurodivergent();
        this.lgbtqia = employee.getLgbtqia();
    }
}

