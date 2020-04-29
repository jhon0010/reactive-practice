package com.practice.reactor.spring;

import com.practice.reactor.spring.domain.documents.Product;
import com.practice.reactor.spring.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import reactor.core.publisher.Flux;

import java.util.Date;

@SpringBootApplication
public class MainSpring implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(MainSpring.class);

    @Autowired
    private ProductRepository repository;

    @Autowired
    private ReactiveMongoTemplate reactiveMongoTemplate;

    public MainSpring(ProductRepository repository, ReactiveMongoTemplate reactiveMongoTemplate) {
        this.repository = repository;
        this.reactiveMongoTemplate = reactiveMongoTemplate;
    }

    public static void main(String[] args) {
        SpringApplication.run(MainSpring.class, args);
    }

    /**
     * The flatmap operator return the POJO from the Mono representation.
     * If you use only map is for working for mono and fluxes.
     *
     * @param args some
     */
    @Override
    public void run(String... args) {

        reactiveMongoTemplate.dropCollection("product")
                .subscribe();

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
                new Product("Glass", 92.00),
                new Product("Window", 0.50),
                new Product("Cellphone", 2000.50)
        )
                .flatMap(product -> {
                            product.setCreatedAt(new Date());
                            return repository.save(product);
                        }
                ).subscribe(productMono -> log.info(productMono.toString()));

    }
}
