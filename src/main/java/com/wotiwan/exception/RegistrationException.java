package com.wotiwan.exception;

import java.util.ArrayList;
import java.util.List;

public class RegistrationException extends RuntimeException {
    private final List<String> ERRORS;
    public RegistrationException(List<String> message) {
        this.ERRORS = message;
    }
    public RegistrationException(String message) {
        ERRORS = new ArrayList<>();
        ERRORS.add(message);
    }

    public List<String> getErrors() {
        return ERRORS;
    }

}
