package com.practice.reactor.utils;

import com.practice.reactor.spring.domain.User;
import com.practice.reactor.spring.domain.documents.Product;
import org.slf4j.Logger;
import reactor.core.publisher.Flux;

import static org.slf4j.LoggerFactory.getLogger;

public final class FluxUtilities {

    private static final Logger LOGGER = getLogger(FluxUtilities.class);

    public static Flux<Product> getDefaultFluxProducts() {

        LOGGER.info("Getting defaults products");

        return
                Flux.just(
                        new Product("TV", 6.500),
                        new Product("Mouse", 2.00),
                        new Product("Keys", 0.50),
                        new Product("PC", 2500.50),
                        new Product("Candle", 4.444),
                        new Product("Glass", 92.00),
                        new Product("Window", 0.50),
                        new Product("Cellphone", 2000.50),
                        new Product("TV", 6.500),
                        new Product("Mouse", 2.00),
                        new Product("Keys", 0.50),
                        new Product("PC", 2500.50),
                        new Product("Candle", 4.444),
                        new Product("Book", 92.00),
                        new Product("Wallet", 0.50),
                        new Product("Spoon", 2000.50)
                );
    }

    public static Flux<User> getDefaultFluxUsers() {

        LOGGER.info("Getting default user flux");

        return Flux.just(
                new User("Jhon", "Lotero"),
                new User("Peter", "Something"),
                new User("Dan", "Whatever"),
                new User("Cristy", "Peterson")
        );
    }

}
