package com.practice.reactor.core.transformations;

import org.slf4j.Logger;
import reactor.core.publisher.Flux;

import java.util.List;

import static java.util.Arrays.asList;
import static org.slf4j.LoggerFactory.getLogger;
import static reactor.core.publisher.Flux.range;

public class Merge {

    private static final Logger LOGGER = getLogger(Merge.class);

    public static void main(String[] args) {

        List<String> names = asList("John", "Doe", "Bean", "Peter");

        sequenceMerge(names, names);
        merge(names);

    }

    /**
     * The important thing to note here is that .merge() does not give any ordering guarantees.
     *
     * @param names List of string.
     */
    private static void merge(List<String> names) {

        Flux<Integer> numberFlux = range(0, 5);
        Flux<String> fluxString = Flux.fromIterable(names);

        Flux.merge(numberFlux, fluxString)
                .subscribe(x -> {
                    LOGGER.info(x.toString());
                });
    }

    private static void sequenceMerge(List<String> namesA, List<String> namesB) {
        Flux.fromIterable(namesA).mergeWith(Flux.fromIterable(namesB))
                .subscribe(LOGGER::info);
    }

}
