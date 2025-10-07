package com.pluralis.pluralis.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pluralis.pluralis.config.JwtAuthenticationFilter;
import com.pluralis.pluralis.dto.AnonymousFeedbackDTO;
import com.pluralis.pluralis.model.AnonymousFeedback;
import com.pluralis.pluralis.service.AnonymousFeedbackService;
import com.pluralis.pluralis.service.TokenService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AnonymousFeedbackController.class)
@AutoConfigureMockMvc(addFilters = false)
class AnonymousFeedbackControllerTest {

    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper om;

    @MockBean
    AnonymousFeedbackService feedbackService;

    @MockBean
    JwtAuthenticationFilter jwtAuthenticationFilter;

    @InjectMocks
    AnonymousFeedbackController anonymousFeedbackController;

    @Test
    @DisplayName("Given a valid feedbackDTO When submitting Then returns saved feedback")
    void givenValidFeedback_whenSubmit_thenReturnSaved() throws Exception {
        // Given
        AnonymousFeedbackDTO dto = new AnonymousFeedbackDTO();
        dto.setMessage("mensagem anônima");

        AnonymousFeedback saved = new AnonymousFeedback();
        saved.setId(123L);
        saved.setMessage("mensagem anônima");

        given(feedbackService.submitFeedback(any())).willReturn(saved);

        // When / Then
        mvc.perform(post("/anonymous-feedback")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(123))
                .andExpect(jsonPath("$.message").value("mensagem anônima"));

        ArgumentCaptor<AnonymousFeedback> captor = ArgumentCaptor.forClass(AnonymousFeedback.class);
        then(feedbackService).should().submitFeedback(captor.capture());
        assertThat(captor.getValue().getMessage()).isEqualTo("mensagem anônima");
    }
}
