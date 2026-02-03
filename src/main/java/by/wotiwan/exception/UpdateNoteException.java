package by.wotiwan.exception;

import java.util.ArrayList;
import java.util.List;

public class UpdateNoteException extends RuntimeException {

    private final List<String> ERRORS;

    public UpdateNoteException(String message) {
        ERRORS = new ArrayList<>();
        ERRORS.add(message);
    }

    public UpdateNoteException(List<String> errors) {
        this.ERRORS = errors;
    }

    public List<String> getERRORS() {
        return ERRORS;
    }

}
