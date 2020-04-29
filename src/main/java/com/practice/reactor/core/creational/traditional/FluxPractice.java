package com.practice.reactor.core.creational.traditional;

import reactor.core.publisher.Flux;

import java.util.Arrays;
import java.util.List;

/**
 * The creation operators are factory methods.
 */
public class FluxPractice {

    public static void main(String[] args) {
        Flux<Integer> numbersFromFiveToSeven = fluxFromRange();
    }

    /**
     * 	The first parameter is the start of the range, while the second parameter is the number of items to produce
     * @return Flux of integers
     */
    private static Flux<Integer> fluxFromRange() {
        Flux<Integer> numbersFromFiveToSeven = Flux.range(5, 3);
        return numbersFromFiveToSeven;
    }

    private static Flux<String> fluxJust() {
        return Flux.just("foo", "bar", "foobar");
    }

    private static Flux<String> fluxFromIterable() {
        List<String> iterable = Arrays.asList("foo", "bar", "foobar");
        return Flux.fromIterable(iterable);
    }



}
