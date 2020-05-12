package com.practice.reactor.core.transformations;

import org.slf4j.Logger;
import reactor.core.publisher.Mono;

import static org.slf4j.LoggerFactory.getLogger;

public class DefaultIfEmpty {

    private static final Logger LOGGER = getLogger(DefaultIfEmpty.class);

    public static void main(String[] args) {

        getObjectMono()
        .subscribe();
    }

    private static Mono<String> getObjectMono() {
        return Mono.just("Hello world")
                .flatMap(x -> getEmpty())
                .defaultIfEmpty("Bye")
                .doOnNext(value -> LOGGER.info("" + value));
    }

    private static Mono<String> getEmpty() {
        return Mono.empty();
    }

}
