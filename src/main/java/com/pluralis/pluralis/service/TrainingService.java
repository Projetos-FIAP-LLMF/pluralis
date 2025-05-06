package com.pluralis.pluralis.service;

import com.pluralis.pluralis.exception.TrainingNotFoundException;
import com.pluralis.pluralis.model.Training;
import com.pluralis.pluralis.repository.TrainingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrainingService {

    private final TrainingRepository trainingRepository;

    public Training createTraining(Training training) {
        return trainingRepository.save(training);
    }

    public List<Training> getAllTrainings() {
        return trainingRepository.findAll();
    }

    public Training findById(Long trainingId) {
        return trainingRepository.findById(trainingId).orElseThrow(() -> new TrainingNotFoundException("Transaction not found"));
    }
}
