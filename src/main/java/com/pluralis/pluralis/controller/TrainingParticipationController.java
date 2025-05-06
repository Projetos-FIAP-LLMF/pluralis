package com.pluralis.pluralis.controller;

import com.pluralis.pluralis.dto.TrainingParticipationDTO;
import com.pluralis.pluralis.model.Employee;
import com.pluralis.pluralis.model.Training;
import com.pluralis.pluralis.model.TrainingParticipation;
import com.pluralis.pluralis.service.EmployeeService;
import com.pluralis.pluralis.service.TrainingParticipationService;
import com.pluralis.pluralis.service.TrainingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/training-participation")
@RequiredArgsConstructor
public class TrainingParticipationController {

    private final TrainingParticipationService participationService;
    private final EmployeeService employeeService;
    private final TrainingService trainingService;

    @PostMapping
    public TrainingParticipation registerParticipation(@Valid @RequestBody TrainingParticipationDTO participationDTO) {
        Employee employee = employeeService.findById(participationDTO.getEmployeeId());
        Training training = trainingService.findById(participationDTO.getTrainingId());

        TrainingParticipation participation = new TrainingParticipation();
        participation.setEmployee(employee);
        participation.setTraining(training);
        participation.setCompleted(participationDTO.getCompleted());

        return participationService.registerParticipation(participation);
    }
}
