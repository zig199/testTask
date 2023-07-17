package dev.ziggeek.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MeshGroupApplication {

    public static void main(String[] args) {
        SpringApplication.run(MeshGroupApplication.class, args);
    }

}
