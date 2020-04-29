package com.practice.reactor.spring.controller;

import com.practice.reactor.spring.domain.documents.Product;
import com.practice.reactor.spring.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.logging.Logger;

@Controller
public class ProductController {

    private final ProductRepository productRepository;
    private final Logger logger = Logger.getLogger(ProductController.class.getName());

    @Autowired
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @GetMapping("/v4/list")
    public Mono<String> chunkedList(Model model) {

        Flux<Product> productFlux = productRepository.findAll()
                .map(product -> {
                    product.setName(product.getName().toUpperCase());
                    return product;
                }).repeat(5000); // repeat x tomes the original flow

        // load the elements for each two is from thymeleaf
        model.addAttribute("products", new ReactiveDataDriverContextVariable(productFlux, 2));
        model.addAttribute("title", "Listado de productos");
        return Mono.just("list-chunked");
    }

    @GetMapping("/v3/list")
    public Mono<String> listFull(Model model) {

        Flux<Product> productFlux = productRepository.findAll()
                .map(product -> {
                    product.setName(product.getName().toUpperCase());
                    return product;
                }).repeat(5000); // repeat x tomes the original flow

        model.addAttribute("products", new ReactiveDataDriverContextVariable(productFlux, 2)); // load the elements for each two is from thymeleaf
        model.addAttribute("title", "Listado de productos");
        return Mono.just("list");
    }


    @GetMapping("/v2/list")
    public Mono<String> listDaraDriven(Model model) {

        Flux<Product> productFlux = productRepository.findAll()
                .map(product -> {
                    product.setName(product.getName().toUpperCase());
                    return product;
                })
                .delayElements(Duration.ofSeconds(1));  // Send each element each second the thymeleaf view refresh it automatically

        productFlux.subscribe(p -> logger.info(p.toString()));

        model.addAttribute("products", new ReactiveDataDriverContextVariable(productFlux, 2)); // load the elements for each two is from thymeleaf
        model.addAttribute("title", "Listado de productos");
        return Mono.just("list");
    }

    @GetMapping("/list")
    public Mono<String> list(Model model) {

        Flux<Product> productFlux = getProductFlux();

        productFlux.subscribe(p -> logger.info(p.toString()));

        // behind this thymeleaf subscribe to this stream flux and can access in thymeleaf code
        model.addAttribute("products", productFlux);
        model.addAttribute("title", "Listado de productos");
        return Mono.just("list");
    }

    private Flux<Product> getProductFlux() {
        return productRepository.findAll()
                    .map(product -> {
                        product.setName(product.getName().toUpperCase());
                        return product;
                    });
    }

}
