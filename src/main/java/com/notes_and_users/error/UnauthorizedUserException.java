package com.notes_and_users.error;

public class UnauthorizedUserException extends RuntimeException {

    public UnauthorizedUserException(Long userId, Long id) {
        super("User with userId " + userId + " not allowed to perform action on note : " + id);
    }

}
