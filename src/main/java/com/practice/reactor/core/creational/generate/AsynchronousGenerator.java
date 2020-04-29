package com.practice.reactor.core.creational.generate;

import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * create is a more advanced form of programmatic creation of a
 * Flux which is suitable for multiple emissions per round, even from multiple threads.
 * <p>
 * It exposes a FluxSink, with its next, error, and complete methods. Contrary to generate,
 * it doesn’t have a state-based variant. On the other hand, it can trigger multi-threaded events in the callback.
 * <p>
 * create can be very useful to bridge an existing API with the reactive world - such as an asynchronous API based on listeners.
 */
public class AsynchronousGenerator {

    private static final Logger LOGGER = getLogger(AsynchronousGenerator.class);

    public static void main(String[] args) {

        /*
        Flux<String> bridge = Flux.create(sink -> {
            sink..register(
                    new MyEventListener<String>() {

                        public void onDataChunk(List<String> chunk) {
                            for(String s : chunk) {
                                sink.next(s);
                            }
                        }

                        public void processComplete() {
                            sink.complete();
                        }
                    });
        });
*/
    }

}
