package com.alexamy.nsa2.example.logging.controller;

import com.alexamy.nsa2.example.logging.service.LoggingExampleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoggingExampleControllerTest {

    private LoggingExampleService loggingService;
    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        loggingService = mock(LoggingExampleService.class);
        webTestClient = WebTestClient.bindToController(
                new LoggingExampleController(loggingService)).build();
    }

    @Test
    void shouldWriteLogWhenValidLevelAndMessage() {
        // given
        String level = "INFO";
        String message = "Test message";
        when(loggingService.writeLog(level, message)).thenReturn(Mono.just(true));

        // when & then
        webTestClient.post()
                .uri("/v1.0.0/log/{level}", level)
                .bodyValue(message)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Boolean.class)
                .isEqualTo(true);

        verify(loggingService, times(1)).writeLog(level, message);
    }

    @Test
    void shouldReturnBadRequestWhenInvalidLevel() {
        // given
        String level = "INVALID";
        String message = "Test message";
        when(loggingService.writeLog(level, message)).thenReturn(
                Mono.error(new IllegalArgumentException("Invalid level")));


        // when & then
        webTestClient.post()
                .uri("/v1.0.0/log/{level}", level)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(message)
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(Boolean.class)
                .isEqualTo(false);

        verify(loggingService, times(1)).writeLog(level, message);
    }

}