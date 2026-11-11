package com.oraltrainer.feedback.exception;

/** Conflit d'état (ex. feedback déjà évalué) → 409. */
public class InvalidStateException extends RuntimeException {

    public InvalidStateException(String message) {
        super(message);
    }
}
