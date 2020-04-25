package com.notes_and_users;

import com.notes_and_users.error.NoteNotFoundException;
import com.notes_and_users.error.NoteUnSupportedFieldPatchException;
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
        // TODO refactor this ... it works but ...
        Long userId = getUserIdForUserName();
        List<Note> notes = new ArrayList<>();
        List<Note> noteList = repository.findAll();
        for (Note note: noteList) {
            if (note.getUserId().equals(userId)) {
                notes.add(note);
            }
        }
        return notes;
    }

    // Save
    @PostMapping("/notes")
    @ResponseStatus(HttpStatus.CREATED)
    Note newNote(@Valid @RequestBody Note newNote) {
        Long userId = getUserIdForUserName();
        if (userId != null && userId.equals(newNote.getUserId())) {
            return repository.save(newNote);
        }
        return null;
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

    // update author only
    @PatchMapping("/notes/{id}")
    Note patch(@RequestBody Map<String, String> update, @PathVariable Long id) {

        return repository.findById(id)
                .map(x -> {

                    String user = update.get("user");
                    // TODO fix this!! map user to user id
                    if (!StringUtils.isEmpty(user)) {
                        x.setUserId(1L);

                        // better create a custom method to update a value = :newValue where id = :id
                        return repository.save(x);
                    } else {
                        throw new NoteUnSupportedFieldPatchException(update.keySet());
                    }

                })
                .orElseGet(() -> {
                    throw new NoteNotFoundException(id);
                });

    }

    @DeleteMapping("/notes/{id}")
    void deleteNote(@PathVariable Long id) {
        repository.deleteById(id);
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
