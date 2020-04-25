package com.notes_and_users.error;

public class NoteNotFoundException extends RuntimeException {

    public NoteNotFoundException(Long id) {
        super("Note id not found : " + id);
    }

}
