package com.axoulotl.alextheque.exception;

import lombok.Getter;

@Getter
public class AlexthequeStandardError extends Exception {
    private final StandardErrorEnum error;
    private final String comment;

    public AlexthequeStandardError(StandardErrorEnum error, String comment) {
        super();
        this.error = error;
        this.comment = comment;
    }
}
