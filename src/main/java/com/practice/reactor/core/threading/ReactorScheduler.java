package com.practice.reactor.core.threading;

import org.slf4j.Logger;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * The Schedulers class has static methods that give access to the following execution contexts:
 *
 * No execution context (Schedulers.immediate()): at processing time, the submitted Runnable will be directly executed,
 * effectively running them on the current Thread (can be seen as a "null object" or no-op Scheduler).
 *
 * A single, reusable thread (Schedulers.single()). Note that this method reuses the same thread for all callers,
 * until the Scheduler is disposed. If you want a per-call dedicated thread, use Schedulers.newSingle() for each call.
 *
 * An unbounded elastic thread pool (Schedulers.elastic()). This one is no longer preferred with the introduction of
 * Schedulers.boundedElastic(), as it has a tendency to hide backpressure problems and lead to too many threads (see below).
 *
 * A bounded elastic thread pool (Schedulers.boundedElastic()). Like its predecessor elastic(), it creates new worker
 * pools as needed and reuses idle ones. Worker pools that stay idle for too long (the default is 60s) are also disposed.
 * Unlike its elastic() predecessor, it has a cap on the number of backing threads it can create (default is number of
 * CPU cores x 10). Up to 100 000 tasks submitted after the cap has been reached are enqueued and will be re-scheduled
 * when a thread becomes available (when scheduling with a delay, the delay starts when the thread becomes available).
 * This is a better choice for I/O blocking work. Schedulers.boundedElastic() is a handy way to give a blocking process
 * its own thread so that it does not tie up other resources. See How Do I Wrap a Synchronous, Blocking Call?, but doesn’t
 * pressure the system too much with new threads.
 *
 * A fixed pool of workers that is tuned for parallel work (Schedulers.parallel()). It creates as many workers as you
 * have CPU cores.
 *
 * Additionally, you can create a Scheduler out of any pre-existing ExecutorService by using Schedulers.fromExecutorService(ExecutorService).
 * (You can also create one from an Executor, although doing so is discouraged.)
 */
public class ReactorScheduler {

    private static final Logger LOGGER = getLogger(ReactorScheduler.class);

    public static void main(String[] args) throws InterruptedException {

//        inOtherThread();
//        subscribeOn();
//        MultiInsideFlux();
        publishOn();

        Thread.sleep(1000);
    }

    /**
     * 	Creates a new Scheduler backed by four Thread instances.
     * The first map runs on the anonymous thread in <5>.
     * The publishOn switches the whole sequence on a Thread picked from <1>.
     * The second map runs on the Thread from <1>.
     * This anonymous Thread is the one where the subscription happens.
     * The print happens on the latest execution context, which is the one from publishOn.
     */
    private static void publishOn() {
        Scheduler s = Schedulers.newParallel("parallel-scheduler", 4);

        final Flux<String> flux = Flux
                .range(1, 2)
                .map(i -> 10 + i)
                .map(x -> {
                    LOGGER.info("" + x);
                    return "" + x;
                })
                .publishOn(s)
                .map(i -> "value " + i);

        new Thread(() -> flux.subscribe(LOGGER::info));
    }

    private static void MultiInsideFlux() {
        Flux.range(0,10)
                .flatMap(x -> {
                    return Flux.range(10,20)
                            .flatMap(y -> {
                                return Flux.range(20,30)
                                        .map(z -> {
                                            return x * y * z;
                                        });
                            });
                })
        .subscribe(number -> LOGGER.info(String.valueOf(number)));
    }

    /**
     * Reactor offers two means of switching the execution context (or Scheduler) in a reactive chain: publishOn and
     * subscribeOn. Both take a Scheduler and let you switch the execution context to that scheduler. But the placement
     * of publishOn in the chain matters, while the placement of subscribeOn does not. To understand that difference,
     * you first have to remember that nothing happens until you subscribe.
     */
    public static void subscribeOn(){

        Flux.range(0,10).map(x -> "" + x)
                .delayElements(Duration.ofMillis(10))
                .doOnNext(LOGGER::info)
                .subscribeOn(Schedulers.boundedElastic())
                .subscribe();
    }


    /**
     * Some operators use a specific scheduler from Schedulers by default (and usually give you the option of providing
     * a different one). For instance, calling the Flux.interval(Duration.ofMillis(300)) factory method produces a
     * Flux<Long> that ticks every 300ms. By default, this is enabled by Schedulers.parallel(). The following line
     * changes the Scheduler to a new instance similar to Schedulers.single():
     */
    private static void inOtherThread() {
        Flux.interval(Duration.ofMillis(300), Schedulers.newSingle("test"));
    }
}
