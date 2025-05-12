package com.pluralis.pluralis.controller;

import com.pluralis.pluralis.dto.AnonymousFeedbackDTO;
import com.pluralis.pluralis.model.AnonymousFeedback;
import com.pluralis.pluralis.service.AnonymousFeedbackService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/anonymous-feedback")
@RequiredArgsConstructor
@Validated
public class AnonymousFeedbackController {

    private final AnonymousFeedbackService feedbackService;

    @PostMapping
    public AnonymousFeedback submitFeedback(@Valid @RequestBody AnonymousFeedbackDTO feedbackDTO) {
        AnonymousFeedback feedback = new AnonymousFeedback();
        feedback.setMessage(feedbackDTO.getMessage());

        return feedbackService.submitFeedback(feedback);
    }
}
