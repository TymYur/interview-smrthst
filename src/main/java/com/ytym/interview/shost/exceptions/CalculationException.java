package com.ytym.interview.shost.exceptions;

public class CalculationException extends RuntimeException {
    private static final String MESSAGE = "Analysis could not be started - no income data";

    public CalculationException() {
        super(MESSAGE);
    }

}
