package com.example.reactor_demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
    public class DemoApp {
        private static DemoPOJOService svc = new DemoPOJOService();

        public static void main(String[] args) {
            svc.createInitialState(5);
            SpringApplication.run(DemoApp.class, args);
        }

}
