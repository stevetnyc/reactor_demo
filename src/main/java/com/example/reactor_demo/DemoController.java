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
import org.apache.tomcat.util.http.fileupload.util.LimitedInputStream;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.ArrayList;
import java.util.NoSuchElementException;

@Slf4j
@SpringBootApplication
@RestController
public class DemoController {

    private static DemoPOJOService pojoService = new DemoPOJOService();

    public static void main(String[] args) {
        SpringApplication.run(DemoController.class, args);
    }

    @GetMapping("/objects/")
    public List<DemoPOJO> getAllPOJOs()
            throws ResponseStatusException {
        log.info("Get requested for all objects");
        List<DemoPOJO> pojoList = pojoService.getAll();
        if (pojoList != null && pojoList.size() > 0) {
            return pojoList;
        } else {
            log.info("Object list was empty");
            throw new ResponseStatusException(
                    HttpStatus.NO_CONTENT, "POJO List was empty"
            );
        }
    }

    @GetMapping("/object/{id}")
    public DemoPOJO getPOJO(@PathVariable int id)
            throws InterruptedException, ResponseStatusException {
        log.info("Get requested for object with id {}", id);

        DemoPOJO pj = pojoService.getById(id);
        if (pj != null) {
            return pj;
        } else {
            log.info("Object with id {} not found", id);
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "POJO not found"
            );
        }

    }
}
