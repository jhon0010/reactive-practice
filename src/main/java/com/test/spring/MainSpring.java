package com.test.spring;

import com.test.domain.documents.Product;
import com.test.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;


@SpringBootApplication
public class MainSpring implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(MainSpring.class);

    @Autowired
    private ProductRepository repository;

    public static void main(String[] args) {
        SpringApplication.run(MainSpring.class, args);
    }

    /**
     * The flatmap operator return the POJO from the Mono representation.
     * If you use only map is for working for mono and fluxes.
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {

        Flux.just(
                new Product("TV", 6.500),
                new Product("Mouse", 2.00),
                new Product("Keys", 0.50),
                new Product("PC", 2000.50)
        )
                .flatMap(product -> repository.save(product))
                .subscribe(productMono -> log.info(productMono.toString()));

    }
}
