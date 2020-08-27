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

    private static List<DemoPOJO> pojoList = new ArrayList<>();
    static {
        pojoList.add(new DemoPOJO(1, "First Object"));
        pojoList.add(new DemoPOJO(2, "Second Object"));
        pojoList.add(new DemoPOJO(3, "Third Object"));
        pojoList.add(new DemoPOJO(4, "Fourth Object"));
        pojoList.add(new DemoPOJO(5, "Fifth Object"));
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoController.class, args);
    }
    
    @GetMapping("/object/{id}")
    public DemoPOJO getPOJO(@PathVariable int id)
            throws InterruptedException, ResponseStatusException {
        try {
            log.info("Get requested for object with id {}", id);
            return pojoList.stream().filter((object) -> object.getId() == id).findFirst().get();
        } catch (NoSuchElementException ex) {
            log.info("Object with id {} not found", id);
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "POJO not found"
            );
        }

    }
}
