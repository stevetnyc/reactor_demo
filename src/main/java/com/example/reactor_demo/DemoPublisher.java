package com.example.reactor_demo;

import org.reactivestreams.Publisher;
import reactor.core.publisher.Mono;

import java.util.concurrent.Flow;

public class DemoPublisher {

    public DemoPublisher(){
//        Make sure the DemoPojoService has state
        if (DemoPOJOService.getSize() == 0) {
            DemoPOJOService.createInitialState(25);
        }
    }

    public Mono<DemoPOJO> getMono() {
        Mono<DemoPOJO> pub = Mono.just(DemoPOJOService.getById(1));

        return pub;
    }
}
