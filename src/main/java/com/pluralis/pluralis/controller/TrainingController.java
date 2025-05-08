package com.pluralis.pluralis.controller;

import com.pluralis.pluralis.dto.TrainingDTO;
import com.pluralis.pluralis.model.Training;
import com.pluralis.pluralis.service.TrainingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/trainings")
public class TrainingController {

    private final TrainingService trainingService;

    @PostMapping
    public Training createTraining(@Valid @RequestBody TrainingDTO trainingDTO) {
        Training training = new Training();
        training.setName(trainingDTO.getName());
        training.setTrainingDate(trainingDTO.getDate());
        training.setMandatory(trainingDTO.getMandatory());

        return trainingService.createTraining(training);
    }

    @GetMapping
    public List<Training> getAllTrainings() {
        return trainingService.getAllTrainings();
    }
}
