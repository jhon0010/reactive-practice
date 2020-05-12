package com.practice.reactor.core.transformations;

import org.slf4j.Logger;
import reactor.core.publisher.Mono;

import java.util.List;

import static java.util.Arrays.asList;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * Then return can return a default value after the on complete.
 */
public class ThenReturn {

    private static final Logger LOGGER = getLogger(ThenReturn.class);

    public static void main(String[] args) {
        thenReturnDefaultValue();
        thenReturnDoesntWorkWhitException();
    }

    private static void thenReturnDoesntWorkWhitException() {
        Mono.just("Hello")
                .thenReturn(5)
                .doOnNext(value -> LOGGER.info(""+ value))
                .then(Mono.error(RuntimeException::new))
                .subscribe();
    }

    private static void thenReturnDefaultValue() {
        List<String> names = asList("John", "Doe", "Bean", "Peter");

        Mono.just(names.get(0))
                .map(String::length)
                .thenReturn("Jhon")
                .doOnNext(name -> LOGGER.info("" + name))
                .subscribe();
    }

}
