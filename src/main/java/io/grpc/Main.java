package io.grpc;

import io.grpc.entities.Compte;
import io.grpc.repositories.CompteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    CommandLineRunner start(CompteRepository repo) {
        return args -> {
            repo.save(new Compte(null, 1500f, "2025-12-07", "COURANT"));
            repo.save(new Compte(null, 3200f, "2025-12-01", "EPARGNE"));
            repo.save(new Compte(null, 500f, "2025-11-15", "COURANT"));
        };

    }
}