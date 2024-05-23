package com.alexamy.nsa2.example.logging;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class LoggingExampleApplication {
    public static void main(String[] args) {
        SpringApplication.run(LoggingExampleApplication.class, args);

        log.info("Application started successfully.");
        log.debug("This message is shown only in debug mode. It is {} now.", new java.util.Date());
    }

}
