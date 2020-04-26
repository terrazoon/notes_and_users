package com.notes_and_users;

import com.notes_and_users.models.Note;
import com.notes_and_users.models.User;
import com.notes_and_users.repositories.NoteRepository;
import com.notes_and_users.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import java.math.BigDecimal;
import java.util.List;

@SpringBootApplication
public class StartNoteApplication {

    // start everything
    public static void main(String[] args) {
        SpringApplication.run(StartNoteApplication.class, args);
    }

    @Profile("demo")
    @Bean
    CommandLineRunner initDatabase(NoteRepository repository, UserRepository urepository) {
        urepository.save(new User("user", "user@user.com", "password"));
        urepository.save(new User("admin", "admin@admin.com", "password"));

        return args -> {
            repository.save(new Note(1L, "User's First Post", "I have nothing to say"));
            repository.save(new Note(1L, "User's Second Post", "I still haven't thought of anything"));
            repository.save(new Note(2L, "Admin's First Post", "I have many things to say because I am the admin"));
        };
    }

}