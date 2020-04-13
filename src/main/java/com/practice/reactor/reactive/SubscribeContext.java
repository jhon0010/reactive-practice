package com.practice.reactor.reactive;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.context.Context;

public class SubscribeContext {


    public static void main(String[] args) {

        Flux<String> greetings = Flux.just("Hubert", "Sharon")
                .flatMap(SubscribeContext::nameToGreeting2)
                .flatMap(SubscribeContext::nameToGreeting)
                .subscriberContext(context ->
                        Context.of("greetingWord", "Hello")  // context initialized
                )
                .log();
        greetings.subscribe(System.out::println);
    }

    private static Mono<String> nameToGreeting2(final String name) {
        return Mono.subscriberContext()
                .filter(c -> c.hasKey("greetingWord"))
                .map(c -> c.get("greetingWord"))
                .flatMap(greetingWord -> Mono.just(greetingWord + " " + name + " " + "!!!"));// ALERT: we have Context here !!!
    }

    private static Mono<String> nameToGreeting(final String name) {
        return Mono.subscriberContext()
                .filter(c -> c.hasKey("greetingWord"))
                .map(c -> c.get("greetingWord"))
                .flatMap(greetingWord -> Mono.just(greetingWord + " " + name + " " + "!!!"));// ALERT: we have Context here !!!
    }


}
