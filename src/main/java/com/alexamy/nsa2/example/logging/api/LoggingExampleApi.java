package com.alexamy.nsa2.example.logging.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;

@RequestMapping("/v1.0.0")
public interface LoggingExampleApi {

    /**
     * Write log with given level and message.
     *
     * @param level   log level
     * @param message log message
     * @return true if log written, false otherwise
     */
    @PostMapping(
            value = "/log/{level}",
            produces = "application/json")
    Mono<ResponseEntity<Boolean>> writeLog(@PathVariable String level,
                                           @RequestBody String message);

}
