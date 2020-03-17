package com.test.controller;

import com.test.domain.documents.Product;
import com.test.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/products")
public class ProductRestController {

    private final ProductRepository productRepository;
    private final Logger logger = Logger.getLogger(ProductRestController.class.getName());

    @Autowired
    public ProductRestController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping()
    public Flux<Product> list() {

        return productRepository.findAll()
                .map(producto -> {
                    producto.setName(producto.getName().toUpperCase());
                    return producto;
                })
                .doOnNext(product -> logger.info(product.toString()));
    }

    @GetMapping("/{id}")
    public Mono<Product> getById(@PathVariable String id ) {

        return productRepository.findAll()
                .filter(p -> p.getId().equalsIgnoreCase(id))
                .next() // emits the next item (we only have only here)
                .doOnNext(product -> logger.info(product.toString()));
//        return productRepository.findById(id).doOnNext(product -> logger.info(product.toString()));
    }

}
