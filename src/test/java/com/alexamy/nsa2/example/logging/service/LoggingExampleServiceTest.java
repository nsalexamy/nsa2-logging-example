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

    private static final String TEST_MESSAGE = "This is a test message";
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

        // when
        Mono<Boolean> result = loggingService.writeLog(level, TEST_MESSAGE);

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

        // when
        Mono<Boolean> result = loggingService.writeLog(level, TEST_MESSAGE);

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

        // when
        Mono<Boolean> result = loggingService.writeLog(level, TEST_MESSAGE);

        // then
        StepVerifier.create(result)
                .expectError(IllegalArgumentException.class)
                .verify();

    }

}