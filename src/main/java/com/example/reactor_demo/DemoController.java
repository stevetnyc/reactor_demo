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

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.ArrayList;

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
            throws InterruptedException {
        Thread.sleep(500);
        return pojoList.stream().filter((object) -> object.getId() == id).findFirst().get();

    }
}