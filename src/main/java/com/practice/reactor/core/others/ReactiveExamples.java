package com.practice.reactor.core.others;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.util.Timer;
import java.util.TimerTask;

public class ReactiveExamples {

    private static final Logger log = LoggerFactory.getLogger(BasicExamples.class);

    public static void main(String[] args) {
        intervalTimeFromCreate();
        backPressureWithSubscriber();
        backPressureRateLimit();
    }

    private static void backPressureRateLimit() {

        Flux.range(1, 10)
                .log()
                .limitRate(2)
                .subscribe();
    }


    private static void backPressureWithSubscriber() {

        /**
         * request(unbounded) - whit out backPressure how many elements can get by default
         */
        Flux.range(1, 10)
                .log()
                .subscribe(new Subscriber<Integer>() { // this is a  custom crating of the consumer

                    private Subscription subscription; // this is the observer

                    private Integer limit = 2; // number of objects to get of stream
                    private Integer consumed = 0;

                    @Override
                    public void onSubscribe(Subscription s) {
                        this.subscription = s;
                        subscription.request(limit);// how many objects can get

                    }

                    @Override
                    public void onNext(Integer integer) {
                        log.info("each element = " + integer);
                        consumed++;
                        if (consumed == limit) {
                            consumed = 0;
                            subscription.request(limit);// how many objects can get
                        }
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * Creating an observable, emitting events.
     */
    private static void intervalTimeFromCreate() {
        Flux.create(emitter -> {
            Timer timer = new Timer();
            timer.schedule(new TimerTask() {

                private Integer counter = 0;

                @Override
                public void run() {
                    emitter.next(++counter);
                    if (counter == 10) {
                        timer.cancel();
                        emitter.complete();
                    }
                }
            }, 1000, 1000);
        })
                .doOnNext(next -> log.info("" + next))
                .doOnComplete(() -> log.info("Counter completed"))
                .subscribe();
    }

}
