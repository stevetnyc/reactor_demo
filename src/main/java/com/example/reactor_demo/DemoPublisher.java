package com.example.reactor_demo;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Slf4j
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

    public Flux<String> getLots() {
        Flux<String> pub = Flux.generate(
                () -> 0,
                (state, sink) -> {
                    log.info("Entered generate()");
                    state++;
                    sink.next(DemoPOJOService.getById(state).toString());
                    if (state > DemoPOJOService.getLastId()) {
                        log.info("State: {}", state);
                        sink.complete();
                    }
                    return state;
                });
        pub.log()
            .delayElements(Duration.ofSeconds(25));
        return pub;
    }
}
