package com.test.controller;

import com.test.domain.documents.Product;
import com.test.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class ProductController {

    private final ProductRepository productRepository;

    @Autowired
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("/list")
    public Mono<String> list(Model model) {

        Flux<Product> productFlux = productRepository.findAll();

        model.addAttribute("products", productFlux); // behind this thymeleaf subscribe to this stream flux
        model.addAttribute("title", "Listado de productos");
        return Mono.just("list");
    }

}
