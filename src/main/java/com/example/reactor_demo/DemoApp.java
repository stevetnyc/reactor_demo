package com.example.reactor_demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
    public class DemoApp {
        private static DemoPOJOService svc = new DemoPOJOService();

        public static void main(String[] args) {
            svc.createInitialState(500);
            log.info("Next id from DemoPojoService: {}", DemoPOJOService.getLastId());
            SpringApplication.run(DemoApp.class, args);
        }

}
