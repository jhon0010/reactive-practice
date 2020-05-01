package com.practice.reactor.core.transformations;

import org.slf4j.Logger;
import reactor.core.publisher.Flux;

import java.util.List;

import static java.util.Arrays.asList;
import static org.slf4j.LoggerFactory.getLogger;

public class IndexFlux {

    private static final Logger LOGGER = getLogger(IndexFlux.class);

    public static void main(String[] args) {

        List<String> names = asList("John", "Doe", "Bean", "Peter");

        Flux.fromIterable(names)
                .index()
                .doOnNext(tuple -> LOGGER.info("List with index {} and value {}", tuple.getT1(), tuple.getT2()))
                .subscribe();
    }

}
