# reactive-practice


#### install mongo docker 

`docker pull mongo`

`docker run -d -p 27017:27017 -v ~/data:/data/db --name mongoContainer mongo`

`docker start mongoContainer`

#### For get into the mongo console

`docker exec -it mongoContainer mongo`

## [About reactor 3.0](https://projectreactor.io/docs/core/release/reference/)
 
* Reactor is a fully non-blocking reactive programming foundation for the JVM.
* Integrates with CompletableFuture
* Reactive Streams 
* Reactor also supports non-blocking inter-process communication with the reactor-netty project
* Reactor Netty offers backpressure-ready network engines for HTTP (including Websockets), TCP, and UDP. Reactive encoding and decoding are fully supported.
* a standardization for Java emerged through the Reactive Streams effort, a specification that defines a set of interfaces and interaction rules for reactive libraries on the JVM. Its interfaces have been integrated into Java 9 under the Flow class.
* the equivalent of the above pair is Publisher-Subscriber. But it is the Publisher that notifies the Subscriber of newly available values as they come, and this push aspect is the key to being reactive


* Simplify clean coding 
* Asynchronous event driven programs
* Declarative tools for concise , error-free code.
* Think for high load and concurrency.
* Original by Netflix.
* Implementation for reactive streams.
* All is immutable Flux and Mono. 
* Transformations , generate and consume publisher and subscriber.
* All things have to been asynchronous non blocking.
* Akka is compatible with Flux and Mono (Publish , subscriber)
* You must think that you don't manipulate an object instance you have a Flux or Mono.
* THe filter operator return a new FLux with the predicate applied.
* Map is a replacement operator transform for example an person to a String 
* FlatMap map to flux or mono.

* Cold flux : Don't send data (pumping) until subscribers attach, will emit the whole data to each new subscriber.
* Hot flux : Reads live data for example mouse movements, begins possibly on creating.

* Can use StepVerifier 
 

 
##### Compare to subscriber and publisher pure
   
* you must to implement all methods onError, on Next etc.
* In publisher you only can subscribe, but how about make some transformations?.



* WithLatestFRom
* OnErrorReturn (exception, defaultValue)
* checkpoint = for debugging


##### Imperative programming

* Say step by step what the program do

##### Declarative programming

*  you write code that describes what you want, but not necessarily how to get it (declare your desired results, 
but not the step-by-step), Here, we're saying "Give us everything where it's odd", not "Step through the collection. 
Check this item, if it's odd, add it to a result collection."





 Java offers two models of asynchronous programming:

Callbacks: Asynchronous methods do not have a return value but take an extra callback parameter (a lambda or anonymous class) that gets called when the result is available. A well known example is Swing’s EventListener hierarchy.

Futures: Asynchronous methods immediately return a Future<T>. The asynchronous process computes a T value, but the Future object wraps access to it. The value is not immediately available, and the object can be polled until the value is available. For instance, an ExecutorService running Callable<T> tasks use Future objects.



#From Imperative to Reactive Programming
Reactive libraries, such as Reactor, aim to address these drawbacks of “classic” asynchronous approaches on the JVM while also focusing on a few additional aspects:

Composability and readability

Data as a flow manipulated with a rich vocabulary of operators

Nothing happens until you subscribe

Backpressure or the ability for the consumer to signal the producer that the rate of emission is too high

High level but high value abstraction that is concurrency-agnostic


#####Hot vs Cold
The Rx family of reactive libraries distinguishes two broad categories of reactive sequences: hot and cold. This distinction mainly has to do with how the reactive stream reacts to subscribers:

A Cold sequence starts anew for each Subscriber, including at the source of data. For example, if the source wraps an HTTP call, a new HTTP request is made for each subscription.

A Hot sequence does not start from scratch for each Subscriber. Rather, late subscribers receive signals emitted after they subscribed. Note, however, that some hot reactive streams can cache or replay the history of emissions totally or partially. From a general perspective, a hot sequence can even emit when no subscriber is listening (an exception to the “nothing happens before you subscribe” rule).

For more information on hot vs cold in the context of Reactor, see this reactor-specific section.

#Own notes

* You have to validate if you subscriber time processing is less than the publisher processing time, if it is not 
you have to apply backpresure mechanism.

