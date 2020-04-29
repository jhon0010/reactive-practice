package com.practice.reactor.spring.controller;

import com.practice.reactor.spring.domain.documents.Product;
import com.practice.reactor.spring.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.logging.Logger;

@RestController
@RequestMapping("/api/products")
public class ProductRestController {

    private ProductRepository productRepository;
    private final Logger logger = Logger.getLogger(ProductRestController.class.getName());

    @Autowired
    public ProductRestController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping()
    public Flux<Product> list() {

        return productRepository.findAll()
                .map(product -> {
                    product.setName(product.getName().toUpperCase());
                    return product;
                })
                .doOnNext(product -> logger.info(product.toString()));
    }

    @GetMapping("/{id}")
    public Mono<Product> getById(@PathVariable String id) {

        return productRepository.findAll()
                .filter(p -> p.getId().equalsIgnoreCase(id))
                .next() // emits the next item (we only have only here)
                .doOnNext(product -> logger.info(product.toString()));
        //return productRepository.findById(id).doOnNext(product -> logger.info(product.toString()));
    }

}
