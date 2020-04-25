package com.notes_and_users;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.math.BigDecimal;

@SpringBootApplication
public class StartNoteApplication {

    // start everything
    public static void main(String[] args) {
        SpringApplication.run(StartNoteApplication.class, args);
    }

    @Profile("demo")
    @Bean
    CommandLineRunner initDatabase(NoteRepository repository) {
        return args -> {
            repository.save(new Note("A Guide to the Bodhisattva Way of Life", "Santideva", new BigDecimal("15.41")));
            repository.save(new Note("The Life-Changing Magic of Tidying Up", "Marie Kondo", new BigDecimal("9.69")));
            repository.save(new Note("Refactoring: Improving the Design of Existing Code", "Martin Fowler", new BigDecimal("47.99")));
        };
    }
}