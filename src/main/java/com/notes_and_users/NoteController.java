package com.notes_and_users;

import com.notes_and_users.error.NoteNotFoundException;
import com.notes_and_users.error.NoteUnSupportedFieldPatchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;
import java.util.Map;

@RestController
@Validated
public class NoteController {

    @Autowired
    private NoteRepository repository;

    // Find
    @GetMapping("/notes")
    List<Note> findAll() {
        return repository.findAll();
    }

    // Save
    @PostMapping("/notes")
    @ResponseStatus(HttpStatus.CREATED)
    Note newNote(@Valid @RequestBody Note newNote) {
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

        return repository.findById(id)
                .map(x -> {
                    x.setName(newNote.getName());
                    x.setUser(newNote.getUser());
                    x.setPrice(newNote.getPrice());
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
                    if (!StringUtils.isEmpty(user)) {
                        x.setUser(user);

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

}
