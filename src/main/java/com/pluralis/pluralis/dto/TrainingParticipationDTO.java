package com.pluralis.pluralis.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TrainingParticipationDTO {

    @NotNull(message = "Employee ID is required")
    private Long employeeId;

    @NotNull(message = "Training ID is required")
    private Long trainingId;

    @NotNull(message = "Completion status is required")
    private Boolean completed;

}
