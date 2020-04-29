package com.practice.reactor.core.errors;

import org.slf4j.Logger;
import reactor.core.publisher.Mono;

import static org.slf4j.LoggerFactory.getLogger;

public class ThrowError {

    private static final Logger LOGGER = getLogger(ThrowError.class);

    public static void main(String[] args) {

        createMonoError();

    }

    /**
     * that errors immediately
     */
    private static void createMonoError() {
        Mono.error(new RuntimeException("Example"))
                .map(ex -> new RuntimeException("Other"))
                .doOnError(e -> LOGGER.info("Handle the exception" + e.getMessage()))
                .subscribe();
    }
}
