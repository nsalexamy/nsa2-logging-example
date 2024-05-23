package com.alexamy.nsa2.example.logging.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class LoggingExampleServiceTest {

    LoggingExampleService loggingService;

    @BeforeEach
    void setUp() {
        loggingService = new LoggingExampleService();
    }

    @DisplayName("Should write log when valid level and message")
    @Test
    void shouldWriteLogWhenValidLevelAndMessage() {
        // given
        String level = "INFO";
        String message = "This is a test message";

        // when
        Mono<Boolean> result = loggingService.writeLog(level, message);

        // then
        StepVerifier.create(result)
                .expectNext(true)
                .verifyComplete();



    }

    @DisplayName("Should return false when lower level")
    @Test
    void shouldReturnFalseWhenLowerLevel() {
        // given
        String level = "TRACE";
        String message = "This is a test message";

        // when
        Mono<Boolean> result = loggingService.writeLog(level, message);

        // then
        StepVerifier.create(result)
                .assertNext(isWritten -> assertFalse(isWritten))
                .verifyComplete();
    }


    @DisplayName("Should throw exception when invalid level")
    @Test
    void shouldThrowExceptionWhenInvalidLevel() {
        // given
        String level = "INVALID";
        String message = "This is a test message";

        // when
        Mono<Boolean> result = loggingService.writeLog(level, message);

        // then
        StepVerifier.create(result)
                .expectError(IllegalArgumentException.class)
                .verify();

    }

}