package com.pluralis.pluralis.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeRequestDTO {

    @NotBlank(message = "Name is required")
    private String name;

    @Email(message = "Email must be valid")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Gender is required")
    private String gender;

    @NotBlank(message = "Ethnicity is required")
    private String ethnicity;

    @NotNull(message = "Neurodivergent field is required")
    private Boolean neurodivergent;

    @NotNull(message = "LGBTQIA+ field is required")
    private Boolean lgbtqia;
}
