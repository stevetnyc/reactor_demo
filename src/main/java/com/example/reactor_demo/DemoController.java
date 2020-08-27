/*
 *    ---------------------------------------------------
 *      Steve Thompson, 2020
 *
 *      A RestController to provide an interface for
 *      demoing some Reactor concepts
 *
 *    ---------------------------------------------------
 */

package com.example.reactor_demo;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Mono;

import java.util.List;


@Slf4j
@RestController
public class DemoController {

//    private static DemoPOJOService pojoService = new DemoPOJOService();

    @GetMapping("/objects/")
    public List<DemoPOJO> getAllPOJOs()
            throws ResponseStatusException {
        log.info("Get requested for all objects");
        List<DemoPOJO> pojoList = DemoPOJOService.getAll();
        if (pojoList != null && pojoList.size() > 0) {
            return pojoList;
        } else {
            log.info("Object list was empty");
            throw new ResponseStatusException(
                    HttpStatus.NO_CONTENT, "POJO List was empty"
            );
        }
    }

    @GetMapping("/objects/{id}")
    public DemoPOJO getPOJO(@PathVariable int id)
            throws InterruptedException, ResponseStatusException {
        log.info("Get requested for object with id {}", id);

        DemoPOJO pj = DemoPOJOService.getById(id);
        if (pj != null) {
            return pj;
        } else {
            log.info("Object with id {} not found", id);
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "POJO not found"
            );
        }

    }

//    *************************************
//    Routes to consume Reactor streams
//    *************************************

    @GetMapping("/stream/mono/")
    public DemoPOJO getMono()
            throws ResponseStatusException {
        log.info("Get requested for Mono stream");

        Mono<DemoPOJO> pub = new DemoPublisher().getMono();
        return pub.block();


    }

}
