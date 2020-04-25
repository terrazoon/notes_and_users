package com.notes_and_users.error;

import java.util.Set;

public class NoteUnSupportedFieldPatchException extends RuntimeException {

    public NoteUnSupportedFieldPatchException(Set<String> keys) {
        super("Field " + keys.toString() + " update is not allow.");
    }

}
