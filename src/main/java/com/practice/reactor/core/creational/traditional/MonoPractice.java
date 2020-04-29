package com.practice.reactor.core.creational.traditional;

import reactor.core.publisher.Mono;

/**
 * The creation operators are factory methods.
 */
public class MonoPractice {

    public static void main(String[] args) {


    }

    /**
     * Kind of mono that only have the concept of onComplete.
     * @return mono void.
     */
    private static Mono<Void> monoVoid(){

        //	Notice the factory method honors the generic type even though it has no value.
        Mono<String> noData = Mono.empty();

        return Mono.empty();
    }

    private static Mono<String> monoJust(){
        return Mono.just("foo");
    }

}
