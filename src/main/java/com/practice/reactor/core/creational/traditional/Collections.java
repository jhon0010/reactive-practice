package com.practice.reactor.core.creational.traditional;

import org.slf4j.Logger;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static org.slf4j.LoggerFactory.getLogger;

public class Collections {

    private static final Logger LOGGER = getLogger(Collections.class);

    public static void main(String[] args) {

        String[] lastNames = getArrayLastNames();
        fromArray(lastNames);
        fromIterable();
        fromStream();
    }

    private static void fromStream() {
        List<String> names = asList("John", "Doe", "Bean", "Peter");
        Stream<String> strame = names.stream();
        Flux.fromStream(strame);
    }

    private static void fromIterable() {
        List<String> names = asList("John", "Doe", "Bean", "Peter");
        Flux.fromIterable(names)
                .log()
                .subscribe(LOGGER::info);
    }

    private static void fromArray(String[] lastNames) {
        Flux.fromArray(lastNames)
                .subscribe(LOGGER::info);
    }

    private static String[] getArrayLastNames() {
        String[] lastNames = new String[5];
        lastNames[0] = "John";
        lastNames[1] = "Doe";
        lastNames[2] = "Harris";
        lastNames[3] = "Bean";
        lastNames[4] = "Another";
        return lastNames;
    }


}
