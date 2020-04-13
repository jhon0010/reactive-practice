package com.practice.reactor;

import com.practice.reactor.domain.documents.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;



public class MainReactor {
    private static Logger logger = Logger.getLogger(MainReactor.class.getCanonicalName());


    public static void main(String args[]) {


        List<Product> products = getProductsListFromFlux();
        products.forEach(product -> logger.info("From list product = " + product));


        getNumberOfProducts(getDefaultProducts())
                .doOnNext(number -> logger.info("Number of products = " + number))
                .subscribe();

        timeOutExample()
                .doOnNext(product -> logger.info("Get product = " + product))
                .subscribe();
    }

    private static Flux<Product> timeOutExample() {

        return getProductsFromThirdParty()
                .timeout(Duration.ofMillis(800))
                .doOnError(e -> logger.info("Error getting third party products " + Arrays.toString(e.getStackTrace())))
                .onErrorResume(e -> getDefaultProducts())
                ;

    }

    private static Flux<Product> getDefaultProducts() {

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

    private static Flux<Product> getProductsFromThirdParty() {
        return Flux.just(new Product("TV", 6.500))
                .delaySequence(Duration.ofMillis(900))
                ;
    }

    private static List<Product> getProductsListFromFlux() {
        return getDefaultProducts().collectList().block();
    }

    /**
     * This is a special operator because transform a flux into a Mono<Long></>
     * @param productFlux
     * @return
     */
    private static Mono<Long> getNumberOfProducts(Flux<Product> productFlux){
        return productFlux.count();
    }

}
