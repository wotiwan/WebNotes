package com.wotiwan.exception;

import java.util.ArrayList;
import java.util.List;

public class LoginException extends RuntimeException {

    private final List<String> ERRORS;

    public LoginException(String message) {
        ERRORS = new ArrayList<>();
        ERRORS.add(message);
    }

    public LoginException(List<String> errors) {
        this.ERRORS = errors;
    }

    public List<String> getERRORS() {
        return ERRORS;
    }

}
