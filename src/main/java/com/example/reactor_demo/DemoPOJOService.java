package com.example.reactor_demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@Slf4j
class DemoPOJOService {

    private static List<DemoPOJO> pojoList;
    private static int nextId;

    public DemoPOJOService() {
        nextId = 1;
        createInitialState(5);
    }

    static void createInitialState (int numElements) {
        pojoList = new ArrayList<DemoPOJO>();
        for (int i = 1; i<=numElements; i++){
            addOnePOJO();
        }
        log.info("POJOService: pojoList created with {} elements", numElements);
    }

    static void addOnePOJO() {
        String val = "Object #" + nextId;
        pojoList.add(new DemoPOJO(nextId, val));
        nextId++;
    }

    static List<DemoPOJO> getAll() {
        log.info("POJOService: getAll()");
        return pojoList;
    }

    static DemoPOJO getById(int id) {
        log.info("POJOService: object with id {} requested", id);
        try {
            return pojoList.stream().filter((object) -> object.getId() == id).findFirst().get();
        } catch (NoSuchElementException ex) {
            log.info("POJOService: Object with id {} not found", id);
            return null;
        }
    }

    static void eraseState() {
        pojoList.clear();
        log.info("POJOService: pojoList erased");
    }
}
