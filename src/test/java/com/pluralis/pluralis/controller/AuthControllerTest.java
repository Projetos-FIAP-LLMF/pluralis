package com.pluralis.pluralis.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pluralis.pluralis.dto.LoginRequestDTO;
import com.pluralis.pluralis.dto.RegisterRequestDTO;
import com.pluralis.pluralis.model.User;
import com.pluralis.pluralis.repository.UserRepository;
import com.pluralis.pluralis.service.TokenService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    MockMvc mvc;
    @Autowired
    ObjectMapper om;

    @MockBean
    UserRepository userRepository;
    @MockBean
    PasswordEncoder passwordEncoder;
    @MockBean
    AuthenticationManager authenticationManager;
    @MockBean
    TokenService tokenService;

    @Test
    @DisplayName("Given existing username When register Then returns 400")
    void givenExistingUsername_whenRegister_then400() throws Exception {
        // Given
        RegisterRequestDTO req = new RegisterRequestDTO();
        req.setUsername("my");
        req.setPassword("123");
        given(userRepository.findByUsername("my")).willReturn(Optional.of(new User()));

        // When / Then
        mvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(req)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("Given new username When register Then returns 200 and saves user")
    void givenNewUsername_whenRegister_then200() throws Exception {
        // Given
        RegisterRequestDTO req = new RegisterRequestDTO();
        req.setUsername("new");
        req.setPassword("secret");

        given(userRepository.findByUsername("new")).willReturn(Optional.empty());
        given(passwordEncoder.encode("secret")).willReturn("hashed");

        // When / Then
        mvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(req)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Given valid credentials When login Then returns JWT token")
    void givenValidCredentials_whenLogin_thenReturnsToken() throws Exception {
        // Given
        LoginRequestDTO req = new LoginRequestDTO();
        req.setUsername("my");
        req.setPassword("123");

        given(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .willReturn(new UsernamePasswordAuthenticationToken("my", "123"));
        given(tokenService.generateToken("my")).willReturn("JWT_TOKEN");

        // When / Then
        mvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("JWT_TOKEN"));
    }
}
