package com.practice.reactor.core.transformations;

import org.slf4j.Logger;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static org.slf4j.LoggerFactory.getLogger;

public class FluxToObject {

    private static final Logger LOGGER = getLogger(FluxToObject.class);


    public static void main(String[] args) {
        List<String> names = asList("John", "Doe", "Bean", "Peter");
        List<String> namesFromFlux = Flux.fromIterable(names).toStream().collect(Collectors.toList());

        namesFromFlux.forEach(LOGGER::info);
    }
}
