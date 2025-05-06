package com.pluralis.pluralis.dto;

import com.pluralis.pluralis.model.Employee;
import lombok.*;

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
        this.neurodivergent = employee.isNeurodivergent();
        this.lgbtqia = employee.isLgbtqia();
    }
}

