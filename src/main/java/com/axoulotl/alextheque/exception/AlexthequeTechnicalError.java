package com.axoulotl.alextheque.exception;

import lombok.Getter;

@Getter
public class AlexthequeTechnicalError extends Exception {
    private StandardErrorEnum error;
    private String comment;

    public AlexthequeTechnicalError(StandardErrorEnum error, String comment) {
        super();
        this.error = error;
        this.comment = comment;
    }
}
