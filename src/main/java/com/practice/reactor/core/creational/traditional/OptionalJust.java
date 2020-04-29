package com.practice.reactor.core.creational.traditional;

import org.slf4j.Logger;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static org.slf4j.LoggerFactory.getLogger;

public class OptionalJust {

    private static final Logger LOGGER = getLogger(OptionalJust.class);

    public static void main(String[] args) {
        workingFromOptional();
        withOptionalNullValue(null);
        withNull(null);

    }

    private static void withNull(String name) {
        Mono.justOrEmpty(name)
                .map(String::valueOf)
                .subscribe(LOGGER::info);
    }

    /**
     * If the value is null just emits the onComplete event
     * and no error will be propagated.
     * @param name
     */
    private static void withOptionalNullValue(String name) {

        Optional<String> optName = Optional.ofNullable(name);

        LOGGER.info("Is present {}", optName.isPresent());
        Mono.justOrEmpty(optName)
                .map(String::valueOf)
                .subscribe(LOGGER::info);
    }

    private static void workingFromOptional() {
        Optional<Boolean> isOptional = Optional.of(true);
        Mono.justOrEmpty(isOptional)
        .map(Boolean::booleanValue)
        .map(String::valueOf)
        .subscribe(LOGGER::info);
    }

}
