package com.practice.reactor.core.transformations;

import com.practice.reactor.spring.domain.Comment;
import com.practice.reactor.spring.domain.documents.Product;
import com.practice.reactor.utils.FluxUtilities;
import org.slf4j.Logger;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class ToOtherFluxMono {

    private static final Logger LOGGER = getLogger(ToOtherFluxMono.class);

    public static void main(String[] args) {

        Flux<Comment> comments = FluxUtilities.getDefaultFluxProducts()
                .flatMapIterable(Product::getComments);

        Flux<List<Comment>> commentsMap = FluxUtilities.getDefaultFluxProducts()
                .map(Product::getComments);

        returnManyFluxes();

        LOGGER.info("-- flatMapSequential with parallelism --");
        Flux.range(1, 1000)
                .flatMapSequential(integer -> {
                    LOGGER.warn("-- starting: " + integer + " --");
                    return Flux.range(integer, 4)
                            .subscribeOn(Schedulers.newParallel("myThread", 2));
                })
                .subscribe(x ->
                        LOGGER.info(x + " - " + Thread.currentThread().getName() + " - " + LocalTime.now()));


    }

    /**
     * Transform the elements emitted by this {@link Flux} asynchronously into Publishers,
     * then flatten these inner publishers into a single {@link Flux}, but merge them in
     * the order of their source element.
     */
    private static void returnManyFluxes() {
        LOGGER.info("-- flatMapSequential without parallelism --");
        Flux.just(1, 5, 9)
                .flatMapSequential(integer -> {
                    LOGGER.debug("-- starting: " + integer + " --");
                    return Flux.range(integer, 4);
                })
                .subscribe(x -> LOGGER.info(x + " - " + Thread.currentThread().getName() + " - " + LocalTime.now()));
    }

}
