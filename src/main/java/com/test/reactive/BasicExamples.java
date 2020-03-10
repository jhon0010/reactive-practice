package com.test.reactive;


import com.test.domain.Comment;
import com.test.domain.User;
import com.test.domain.UserComments;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Mono y Fluxes .
 * <p>
 * Streams de datos trabajados de forma asincrona, basado en patr+ón observable, iterator y programación funcional.
 * <p>
 * Transforma y compone flujos mediante los operadores map, filter, merge, delay, fireach.
 * <p>
 * Suscribir a cualquier flujo observable.
 * <p>
 * Son cancelables, puede ser finito o infinito, son inmutables.
 * <p>
 * Abstraer concurrencia por schedules.
 * <p>
 * Tiene su propio manejo de errores y manejar reintentos.
 * <p>
 * Nunca bloquear los requests.
 * <p>
 * Map = aplicar operaciones y casteos al stream.
 * <p>
 * filter = realizar filtros basados en booleanos.
 * <p>
 * delay(20) = lapsus de tiempo entre llamados.
 * <p>
 * merge = combinar varios flujos
 * <p>
 * <p>
 * Netty = servidor reactivo.
 */
public class BasicExamples implements CommandLineRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(BasicExamples.class);

    public static void main(String[] args) throws InterruptedException {
        intervalContinuousExample();
    }

    /**
     * Use flatMap for check errors
     *
     * retry = cuando ocurran excepciones simplemente intenta x veces todo el flujo.
     *
     * @throws InterruptedException
     */
    private static void intervalContinuousExample() throws InterruptedException {

        CountDownLatch latch = new CountDownLatch(1);

        Flux.interval(Duration.ofSeconds(1))
                .doOnTerminate(() -> {
                    latch.countDown();
                    LOGGER.info(String.valueOf(latch.getCount()));
                })
                .flatMap(i -> {
                    if(i >= 5) {
                        return Flux.error(new InterruptedException("Only until 5"));
                    }
                    return Flux.just(i);
                })
                .map(i -> "Hola : " + i)
                .retry(2)
                .subscribe(s -> LOGGER.info(s), e -> LOGGER.error("error", e));

        latch.await();
    }

    /**
     * EL proceso main termina pero el flux con delay sigue ejecutandose por debajo permitiendo escalar.
     *
     * blockLast = se subscribe al flujo pero bloquea el main hasta que acabe el ultimo elemento emitido en delay.
     *
     * delayElements = más semantico
     */
    private static void intervalExample() {

        Flux<Integer> integerFlux =  Flux.range(1, 12);
        Flux<Long> delay = Flux.interval(Duration.ofSeconds(1));
        integerFlux.zipWith(delay, (in, de) -> in)
        .doOnNext(i -> LOGGER.info(i.toString()))
                .blockLast();

        integerFlux.delayElements(Duration.ofSeconds(1)).doOnNext(i -> LOGGER.info(i.toString())).subscribe();
    }

    /**
     * The size is the first stream size
     */
    private static void rangeExample() {
        Flux.just(1,2,3,4)
                .map(i -> i * 2 )
                .zipWith(
                        Flux.range(0,100) , (uno, dos) ->
                         String.format("First flux : %d, Segundo flux : %d", uno, dos)
                ).subscribe(string -> LOGGER.info(string));
    }

    private static void fluxToMono(List<User> users) {

        Mono<List<User>> fluxUsers = Flux
                .fromIterable(users)
                .collectList();

    //Indicates that a task or resource can be cancelled/disposed.
        Disposable some = fluxUsers
                .subscribe(list -> {
                    list.forEach(e -> LOGGER.info(e.toString()));
                });
    }

    private static void mergeEntity() {

        Mono<User> userMono = Mono.fromCallable(() -> new User("Jhon", "Lotero"));
        Mono<Comment> commentMono = Mono.fromCallable(() -> {
           Comment comment = new Comment();
           comment.addComment("aaa");
            comment.addComment("bbb");
            comment.addComment("ccc");

            return comment;
        });


        userMono.flatMap(u -> commentMono.map(c -> new UserComments(u,c)))
        .subscribe(uc -> LOGGER.info(uc.toString()));

    }


    private static void zipExample() {

        Mono<User> userMono = Mono.fromCallable(() -> new User("Jhon", "Lotero"));
        Mono<Comment> commentMono = Mono.fromCallable(() -> {
            Comment comment = new Comment();
            comment.addComment("aaa");
            comment.addComment("bbb");
            comment.addComment("ccc");

            return comment;
        });


        Mono<UserComments> uc = userMono.zipWith(commentMono, (u, c) -> {
            return new UserComments(u, c);
        });

        Mono<UserComments> uc2 = userMono.zipWith(commentMono).map(tuple -> {
            User u = tuple.getT1();
            Comment c = tuple.getT2();
            return new UserComments(u,c);
        });

        uc2.subscribe(s -> LOGGER.info(s.toString()));
        uc.subscribe(s -> LOGGER.info(s.toString()));

    }

    /**
     * The map method return only POJOS, but the flatMap can return
     * Mono o Flux an Observable.
     *
     * @param fluxUsers
     */
    private static void flatMapProblem(Flux<User> fluxUsers) {

        fluxUsers
                .flatMap(u -> {
                    if (u.getName().equalsIgnoreCase("Jhon")) {
                        return Mono.just(u);
                    }
                    return Mono.empty(); // delete of the stream
                })
                .subscribe(user -> LOGGER.info("flatMapProblem" + user.toString()));
    }

    private static Flux<User> getFLuxFromIterable(List<String> usuarios) {

        Flux<User> users = Flux.fromIterable(usuarios)
                .map(
                        fullName -> new User(fullName.split(" ")[0],
                                fullName.split(" ")[1]));
        users.subscribe(user -> LOGGER.info("getFLuxFromList" + user.toString()));

        return users;
    }

    @Override
    public void run(String... args) throws Exception {

        /**
         *
         * Flux publisher observable
         * doOnNext = for each element do
         *
         * Sin subscripción no va a pasar nada
         */
        Flux<String> nombres = Flux.just("Juan Gonzales", "Jhon Lotero", "Maria", "Mario Castaño", "Bruce Ramirez", "Pedro Infante")
                .doOnNext(e -> {
                    if (e.isEmpty()) {
                        throw new RuntimeException("Can be empty"); // Cuando se lanza un error no sigue procesando
                    }
                    System.out.println(e);
                });

        nombres.map(name -> name.toUpperCase())// retorna una nueva instancia del flux con los elementos cambiados
                .subscribe(LOGGER::info);

        // Una vez finaliza la ejecución del flux
        nombres.subscribe(LOGGER::info,
                error -> LOGGER.error(error.getMessage()), // observador o consumidor , manejar errores
                () -> LOGGER.info("Ha finalizado la ejecución con exito")); // una vez finalizada la ejecución


        Flux<User> FilteredUsers = nombres.map(
                fullName -> new User(fullName.split(" ")[0],
                        fullName.split(" ")[1]))
                .filter(user -> user.getName().contains("J"))
                .doOnNext(user -> LOGGER.info(user.toString()));


        List<String> userNames = Arrays.asList("Juan Gonzales", "Jhon Lotero", "Maria Algo", "Mario Castaño", "Bruce Ramirez", "Pedro Infante");

        List<User> users = Arrays.asList(
                new User("Juan", "Gonzales"),
                new User("Jhon", "Lotero"),
                new User("Maria", "Algo")
                );

        Flux<User> fluxUsers = getFLuxFromIterable(userNames);
        flatMapProblem(fluxUsers);
        fluxToMono(users);
        mergeEntity();
        zipExample();
        rangeExample();
//        intervalExample();
        intervalContinuousExample();
    }
}

