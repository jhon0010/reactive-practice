package com.practice.reactor.core.creational.generate;


import org.slf4j.Logger;
import reactor.core.publisher.Flux;

import java.util.concurrent.atomic.AtomicLong;

import static org.slf4j.LoggerFactory.getLogger;

public class SynchronousGenerator {

    private static final Logger LOGGER = getLogger(SynchronousGenerator.class);

    public static void main(String[] args) {

        writeResult(generate());
        writeResult(mutableGenerator());
        writeResult(generateWithConsumer());
    }

    /**
     * 	Again, we generate a mutable object as the state.
     * We mutate the state here.
     * We return the same instance as the new state.
     * We see the last state value (11) as the output of this Consumer lambda.
     *
     * @return Flux
     */
    private static Flux<Object> generateWithConsumer() {
        return Flux.generate(
                AtomicLong::new,
                (state, sink) -> {
                    long i = state.getAndIncrement();
                    sink.next("3 x " + i + " = " + 3 * i);
                    if (i == 10) sink.complete();
                    return state;
                }, (state) -> LOGGER.info("state: " + state));
    }


    private static void writeResult(Flux<Object> generate) {
        generate
                .map(x -> "" + x)
                .doOnNext(LOGGER::info)
                .subscribe();
    }

    /**
     * You can also mutate the state of the income object
     * this is possible because this generate method is synchronous.
     *
     * If your state object needs to clean up some resources,
     * use the generate(Supplier<S>, BiFunction, Consumer<S>)
     * variant to clean up the last state instance.
     *
     * @return Flux<Object>
     */
    private static Flux<Object> mutableGenerator() {
       return  Flux.generate(
                AtomicLong::new,
                (state, sink) -> {
                    long i = state.getAndIncrement();
                    sink.next("3 x " + i + " = " + 3*i);
                    if (i == 10) sink.complete();
                    return state;
                });
    }

    /**
     * 	We supply the initial state value of 0.
     * We use the state to choose what to emit (a row in the multiplication table of 3).
     * We also use it to choose when to stop.
     * We return a new state that we use in the next invocation (unless the sequence terminated in this one).
     * @return Flux<Object>
     */
    private static Flux<Object> generate() {
        return Flux.generate(
                () -> 0,
                (state, sink) -> {
                    sink.next("3 x " + state + " = " + 3*state);
                    if (state == 10) sink.complete();
                    return state + 1;
                });
    }
}
