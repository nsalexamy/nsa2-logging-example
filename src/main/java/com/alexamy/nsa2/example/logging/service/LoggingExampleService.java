package com.alexamy.nsa2.example.logging.service;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.event.Level;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Slf4j
public class LoggingExampleService {

    /**
     * Write log with given level and message.
     *
     * @param level   log level
     * @param message log message
     * @return true if log written, false otherwise
     */
    public Mono<Boolean> writeLog(String level, String message) {
        return Mono.fromSupplier(() -> {

            Level logLevel = Level.valueOf(level.toUpperCase());
            boolean logWritten = false;

            final String messageFormat = "Writing log - level: {}, message: {}";

            switch (logLevel) {
                case TRACE:
                    log.trace(messageFormat, level, message);
                    logWritten = log.isTraceEnabled();
                    break;
                case DEBUG:
                    log.debug(messageFormat, level, message);
                    logWritten = log.isDebugEnabled();
                    break;
                case INFO:
                    log.info(messageFormat, level, message);
                    logWritten = log.isInfoEnabled();
                    break;
                case WARN:
                    log.warn(messageFormat, level, message);
                    logWritten = log.isWarnEnabled();
                    break;
                case ERROR:
                    log.error(messageFormat, level, message);
                    logWritten = log.isErrorEnabled();
                    break;

            }

            return logWritten;
        });

    }
}
