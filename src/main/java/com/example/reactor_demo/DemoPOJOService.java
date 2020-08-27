package com.example.reactor_demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Slf4j
class DemoPOJOService {

    private static List<DemoPOJO> pojoList;

    public DemoPOJOService() {
        this.createState();
    }

    void createState () {
        pojoList = new ArrayList<DemoPOJO>();
        pojoList.add(new DemoPOJO(1, "First Object"));
        pojoList.add(new DemoPOJO(2, "Second Object"));
        pojoList.add(new DemoPOJO(3, "Third Object"));
        pojoList.add(new DemoPOJO(4, "Fourth Object"));
        pojoList.add(new DemoPOJO(5, "Fifth Object"));
        log.info("POJOService: pojoList created");
    }

    List<DemoPOJO> getAll() {
        log.info("POJOService: getAll()");
        return pojoList;
    }

    DemoPOJO getById(int id) {
        log.info("POJOService: object with id {} requested", id);
        try {
            return pojoList.stream().filter((object) -> object.getId() == id).findFirst().get();
        } catch (NoSuchElementException ex) {
            log.info("POJOService: Object with id {} not found", id);
            return null;
        }
    }

    void eraseState() {
        pojoList.clear();
        log.info("POJOService: pojoList erased");
    }
}
