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
import java.util.Optional;

@RestController
@Validated
public class NoteController {

    @Autowired
    private NoteRepository repository;

    @Autowired
    private UserRepository userRepository;

    // Find
    @GetMapping("/notes")
    @ResponseStatus(HttpStatus.OK)
    List<Note> findAll() {
        return repository.findAll();
    }

    // Save
    @PostMapping("/notes")
    @ResponseStatus(HttpStatus.CREATED)
    Note newNote(@Valid @RequestBody Note newNote) {
        Long userId = getUserIdForUserName();
        if (userId == null) {
            throw new UnauthorizedUserException(userId, newNote.getId());
        }
        newNote.setUserId(userId);
        return repository.save(newNote);
    }

    // Find
    @GetMapping("/notes/{id}")
    @ResponseStatus(HttpStatus.OK)
    Note findOne(@PathVariable @Min(1) Long id) {
        Long userId = getUserIdForUserName();
        Optional<Note> note = repository.findById(id);
        if (!note.isPresent()) {
            throw new NoteNotFoundException(id);
        } else if (!note.get().getUserId().equals(userId)) {
            throw new UnauthorizedUserException(userId, id);
        } else {
            return note.get();
        }
    }

    // Save or update
    @PutMapping("/notes/{id}")
    Note saveOrUpdate(@RequestBody Note newNote, @PathVariable Long id) {
        Long userId = getUserIdForUserName();
        if (!newNote.getUserId().equals(userId)) {
            throw new UnauthorizedUserException(userId, id);
        }
        Long now = System.currentTimeMillis();
        return repository.findById(id)
                .map(x -> {
                    x.setTitle(newNote.getTitle());
                    x.setNote(newNote.getNote());
                    x.setLastUpdateTime(now);
                    return repository.save(x);
                })
                .orElseGet(() -> {
                    newNote.setId(id);
                    newNote.setCreateTime(now);
                    newNote.setLastUpdateTime(now);
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
        Long userId = null;
        try {
            UserDetails userDetails =
                    (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            List<User> userList = userRepository.findAll();
            for (User user : userList) {
                if (userDetails.getUsername().equals(user.getName())) {
                    userId = user.getId();
                }
            }
        } catch (NullPointerException npe) {
            //For unit tests ... do nothing, this means no user
        }
        return userId;
    }
}
