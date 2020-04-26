package com.notes_and_users.controllers;

import com.notes_and_users.error.NoteNotFoundException;
import com.notes_and_users.error.NoteUnSupportedFieldPatchException;
import com.notes_and_users.error.UnauthorizedUserException;
import com.notes_and_users.models.Note;
import com.notes_and_users.models.User;
import com.notes_and_users.repositories.NoteRepository;
import com.notes_and_users.repositories.UserRepository;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@RestController
@Validated
public class NoteController {

    @Autowired
    private NoteRepository repository;

    @Autowired
    private UserRepository userRepository;

    // Find
    @GetMapping("/notes")
    List<Note> findAll() {
        return repository.findAll();
    }

    // Save
    @PostMapping("/notes")
    @ResponseStatus(HttpStatus.CREATED)
    Note newNote(@Valid @RequestBody Note newNote) {
        Long userId = getUserIdForUserName();
        System.out.println("enter newNote!!! userId = " + userId);
        newNote.setUserId(userId);
        System.out.println("This is what we are saving " + newNote);
        return repository.save(newNote);
    }

    // Find
    @GetMapping("/notes/{id}")
    Note findOne(@PathVariable @Min(1) Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new NoteNotFoundException(id));
    }

    // Save or update
    @PutMapping("/notes/{id}")
    Note saveOrUpdate(@RequestBody Note newNote, @PathVariable Long id) {
        Long userId = getUserIdForUserName();
        if (!userId.equals(newNote.getUserId())) {
            throw new UnauthorizedUserException(userId, id);
        }
        return repository.findById(id)
                .map(x -> {
                    x.setTitle(newNote.getTitle());
                    x.setNote(newNote.getNote());
                    return repository.save(x);
                })
                .orElseGet(() -> {
                    newNote.setId(id);
                    return repository.save(newNote);
                });
    }

    @DeleteMapping("/notes/{id}")
    void deleteNote(@PathVariable Long id) {
        Long userId = getUserIdForUserName();
        Note noteToDelete = findOne(id);
        if (noteToDelete.getUserId().equals(userId)) {
            repository.deleteById(id);
        } else {
            throw new UnauthorizedUserException(userId, id);
        }
    }

    private Long getUserIdForUserName() {
        UserDetails userDetails =
                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = null;
        List<User> userList = userRepository.findAll();
        for (User user: userList) {
            if (userDetails.getUsername().equals(user.getName())) {
                userId = user.getId();
            }
        }
        return userId;
    }
}
