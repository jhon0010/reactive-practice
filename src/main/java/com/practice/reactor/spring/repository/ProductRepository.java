package com.practice.reactor.spring.repository;

import com.practice.reactor.spring.domain.documents.Product;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends ReactiveCrudRepository<Product, String> {

}

