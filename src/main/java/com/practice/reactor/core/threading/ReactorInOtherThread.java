package com.practice.reactor.core.threading;

import org.slf4j.Logger;
import reactor.core.publisher.Mono;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Obtaining a Flux or a Mono does not necessarily mean that it runs in a dedicated Thread. Instead,
 * most operators continue working in the Thread on which the previous operator executed.
 */
public class ReactorInOtherThread {

    private static final Logger LOGGER = getLogger(ReactorInOtherThread.class);

    public static void main(String[] args) {

        sameThread();
    }

    /**
     * The Mono<String> is assembled in thread main.
     * However, it is subscribed to in thread Thread-0.
     * As a consequence, both the map and the onNext callback actually run in Thread-0
     */
    private static void sameThread() {
        final Mono<String> mono = Mono.just("hello ");

        Thread t = new Thread(() -> mono
                .map(msg -> msg + "thread ")
                .subscribe(v ->
                        LOGGER.warn(v + Thread.currentThread().getName())
                )
        );
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
