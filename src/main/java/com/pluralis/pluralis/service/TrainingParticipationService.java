package com.pluralis.pluralis.service;

import com.pluralis.pluralis.model.TrainingParticipation;
import com.pluralis.pluralis.repository.TrainingParticipationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TrainingParticipationService {

    private final TrainingParticipationRepository participationRepository;

    public TrainingParticipation registerParticipation(TrainingParticipation participation) {
        return participationRepository.save(participation);
    }

    public long getTotalParticipations() {
        return participationRepository.count();
    }
}

