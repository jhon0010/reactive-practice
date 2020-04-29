package com.practice.reactor.core.backpresure.delay;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.time.Duration;

public class DelayedPublishing {

    private static final Logger LOGGER = LoggerFactory.getLogger(DelayedPublishing.class);

    public static void main(String[] args)  {

        long second = 1000;

        try {
            Flux<Integer> seconds = Flux.range(0,100)
                    .delayElements(Duration.ofSeconds(1));

            seconds
                    .map(String::valueOf)
                    .doOnNext(LOGGER::info)
                    .timeout(Duration.ofMillis(2000))
                    .doOnError(e -> LOGGER.error("Error consuming delayed flux", e))
                    .subscribe();

            Thread.sleep(second * 20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
