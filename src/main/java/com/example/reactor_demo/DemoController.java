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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalTime;
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

    @GetMapping(value = "/stream/mono/",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public String getMono()
            throws ResponseStatusException, JsonProcessingException {
        log.info("Get requested for Mono stream");

        LocalTime startTime = LocalTime.now();

        Mono<DemoPOJO> pub = new DemoPublisher().getMono();
        DemoPOJO pojo = pub.block();

        LocalTime endTime = LocalTime.now();
        Duration duration = Duration.between(startTime, endTime);

        ObjectMapper mapper = new ObjectMapper();
        String pojoString = mapper.writeValueAsString(pojo);

        StringBuilder respJSON = new StringBuilder();
        respJSON.append("{");
        respJSON.append(System.getProperty("line.separator"));
        respJSON.append("startTime: " + startTime.toString());
        respJSON.append(",");
        respJSON.append(System.getProperty("line.separator"));
        respJSON.append("POJO: ");
        respJSON.append(pojoString);
        respJSON.append(",");
        respJSON.append(System.getProperty("line.separator"));
         respJSON.append("endTime: " + endTime.toString());
        respJSON.append(",");
        respJSON.append(System.getProperty("line.separator"));
        respJSON.append("duration: " + duration.getSeconds());
        respJSON.append(System.getProperty("line.separator"));
        respJSON.append("}");

        return respJSON.toString();

    }

    @GetMapping(value = "/stream/flux/",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<DemoPOJO> getFlux()
            throws ResponseStatusException {
        log.info("Get requested for Flux stream");

        List<DemoPOJO> pojos = new DemoPublisher().getFlux()
                .collectList().block();

        return pojos;

    }

    @GetMapping(value = "/stream/lots/")
    public Flux<String> getLotsFlux()
            throws ResponseStatusException {
        log.info("Get requested for Lots Flux stream");

        return new DemoPublisher().getLots()
                .map(String::toLowerCase);
    }
}
