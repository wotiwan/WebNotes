package com.wotiwan.exception;

import java.util.ArrayList;
import java.util.List;

public class CreateNoteException extends RuntimeException {

    private final List<String> ERRORS;

    public CreateNoteException(String message) {
        ERRORS = new ArrayList<>();
        ERRORS.add(message);
    }

    public CreateNoteException(List<String> errors) {
        this.ERRORS = errors;
    }

    public List<String> getERRORS() {
        return ERRORS;
    }

}
