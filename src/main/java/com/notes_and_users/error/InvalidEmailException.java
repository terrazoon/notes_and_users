package com.notes_and_users.error;

public class InvalidEmailException extends RuntimeException {

    public InvalidEmailException(String email) {
        super("Invalid email:" + email);
    }

}
