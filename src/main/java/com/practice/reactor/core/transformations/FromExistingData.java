package com.practice.reactor.core.transformations;

import org.slf4j.Logger;
import reactor.core.publisher.Flux;

import java.util.List;

import static java.util.Arrays.asList;
import static org.slf4j.LoggerFactory.getLogger;

public class FromExistingData {

    private static final Logger LOGGER = getLogger(FromExistingData.class);

    public static void main(String[] args) {

//        castStringToNumberCharacters();

        List<String> names = asList("John", "Doe", "Bean", "Peter");
        Flux.fromIterable(names).cast(Integer.class).map(number -> {
            LOGGER.info(String.valueOf(number));
            return Flux.just(number);
        });

    }

    /**
     * on a 1-to-1 basis (eg. strings to their length)
     */
    private static void castStringToNumberCharacters() {
        List<String> names = asList("John", "Doe", "Bean", "Peter");
        Flux.fromIterable(names)
                .map(name -> String.valueOf(name.length()))
                .subscribe(LOGGER::info);
    }
}
