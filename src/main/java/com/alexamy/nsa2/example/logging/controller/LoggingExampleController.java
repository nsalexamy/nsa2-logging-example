package com.alexamy.nsa2.example.logging.controller;


import com.alexamy.nsa2.example.logging.api.LoggingExampleApi;
import com.alexamy.nsa2.example.logging.service.LoggingExampleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LoggingExampleController implements LoggingExampleApi {

    private final LoggingExampleService loggingService;

    /**
     * {@inheritDoc
     */
    @Override
    public Mono<ResponseEntity<Boolean>> writeLog(String level,
                                                  String message) {
        return loggingService.writeLog(level, message)
                .map(ResponseEntity::ok)
                .onErrorResume(IllegalArgumentException.class, e -> {
                    log.error("=====> onErrorResume: " + e.getMessage(), e);
                    return Mono.just(ResponseEntity.badRequest().body(false));
                });
    }

}
