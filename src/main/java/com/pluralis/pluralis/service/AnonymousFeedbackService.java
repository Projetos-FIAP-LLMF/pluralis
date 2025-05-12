package com.pluralis.pluralis.service;

import com.pluralis.pluralis.model.AnonymousFeedback;
import com.pluralis.pluralis.repository.AnonymousFeedbackRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnonymousFeedbackService {

    private final AnonymousFeedbackRepository feedbackRepository;

    public AnonymousFeedback submitFeedback(AnonymousFeedback feedback) {
        return feedbackRepository.save(feedback);
        // TODO: arrumar pra gerar a data
    }
}

