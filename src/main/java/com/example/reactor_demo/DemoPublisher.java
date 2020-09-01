package com.example.reactor_demo;

import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.Flow;

public class DemoPublisher {

    public DemoPublisher(){
//        Make sure the DemoPojoService has state
        if (DemoPOJOService.getSize() == 0) {
            DemoPOJOService.createInitialState(25);
        }
    }

    public Mono<DemoPOJO> getMono() {
        Mono<DemoPOJO> pub = Mono.just(DemoPOJOService.getById(1))
                .delayElement(Duration.ofSeconds(5));
        return pub;
    }

    public Flux<DemoPOJO> getFlux() {
        Flux<DemoPOJO> pub = Flux.fromIterable(DemoPOJOService.getAll());

        return pub;
    }
}
