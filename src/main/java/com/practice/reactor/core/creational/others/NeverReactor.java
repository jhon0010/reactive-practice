package com.practice.reactor.core.creational.others;

import org.slf4j.Logger;
import reactor.core.publisher.Mono;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * that will never signal any data, error or completion signal,
 * 	 * essentially running indefinitely.
 */
public class NeverReactor {

    private static final Logger LOGGER = getLogger(NeverReactor.class);

    public static void main(String[] args) {

        Mono.never()
                .map(empty -> "jhon") // never happen
                .subscribe(LOGGER::info); // never happen
    }

}
