package io.everyonecodes.project_module.exception;

public class HeroNameConflictException extends RuntimeException {
    public HeroNameConflictException(String message) {
        super(message);
    }
}
