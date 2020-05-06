package com.practice.reactor.core.transformations;

import org.slf4j.Logger;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static java.util.Arrays.asList;
import static org.slf4j.LoggerFactory.getLogger;

public class Maps {

    private static final Logger LOGGER = getLogger(Maps.class);

    public static void main(String[] args) {

//        flatMapIntoAnotherFlux();
        flatMapIntoAnotherMonoType();

        mapIntoAnotherMonoType();
    }

    private static void mapIntoAnotherMonoType() {
        Mono<Character> firstLetter = Mono.just("Jhon")
                .map(name -> name.charAt(0));
        firstLetter.subscribe(character -> LOGGER.info(character.toString()));
    }

    private static void flatMapIntoAnotherMonoType() {
        Mono<Character> firstLetter = Mono.just("Jhon")
                .flatMap(name -> Mono.just(name.charAt(0)));
        firstLetter.subscribe(character -> LOGGER.info(character.toString()));
    }

    private static void flatMapIntoAnotherFlux() {
        List<String> names = asList("John", "Doe", "Bean", "Peter");
        Flux<String> manyLetters = Flux
                .fromIterable(names)
                .flatMap(
                        word -> Flux.fromArray(word.split(""))
                );
        manyLetters.subscribe(LOGGER::info);
    }

}
