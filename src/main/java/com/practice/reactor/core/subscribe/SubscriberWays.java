package com.practice.reactor.core.subscribe;

import reactor.core.Disposable;
import reactor.core.publisher.Flux;

/**
 * All these lambda-based variants of subscribe() have a Disposable return type. In this case, the Disposable interface
 * represents the fact that the subscription can be cancelled, by calling its dispose() method.
 *
 * For a Flux or Mono, cancellation is a signal that the source should stop producing elements.
 * However, it is NOT guaranteed to be immediate
 */
public class SubscriberWays {

    public static void main(String[] args) {
        simpleSubscribe();
        consumer();
        manageAnError();
        errorAndCompletion();
        useSubscription();

        Flux<Integer> ints = Flux.range(1, 3);
        Disposable disposable = ints.subscribe();
        disposable.dispose();

    }

    /**
     * hat variant requires you to do something with the Subscription
     * (perform a request(long) on it or cancel() it). Otherwise the Flux hangs.
     *
     * 	When we subscribe we receive a Subscription. Signal that we want up to 10 elements from the source
     * 	(which will actually emit 4 elements and complete).
     */
    private static void useSubscription() {
        Flux<Integer> ints = Flux.range(1, 4);
        ints.subscribe(System.out::println,
                error -> System.err.println("Error " + error),
                () -> System.out.println("Done"),
                sub -> sub.request(10));
    }

    /**
     * The completion callback has no input, as represented by an empty pair of parentheses:
     * It matches the run method in the Runnable interface
     */
    private static void errorAndCompletion() {
        Flux<Integer> ints = Flux.range(1, 4);
        ints.subscribe(System.out::println,
                error -> System.err.println("Error " + error),
                () -> System.out.println("Done"));
    }

    private static void manageAnError() {
        Flux<Integer> ints = Flux.range(1, 4)
                .map(i -> {
                    if (i <= 3) return i;
                    throw new RuntimeException("Got to 4");
                });
        ints.subscribe(System.out::println,
                error -> System.err.println("Error: " + error));
    }

    private static void consumer() {
        Flux<Integer> ints = Flux.range(1, 3);
        ints.subscribe(System.out::println);
    }

    private static void simpleSubscribe() {
        Flux<Integer> ints = Flux.range(1, 3);
        ints.subscribe();
    }

}
