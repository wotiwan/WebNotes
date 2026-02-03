package by.wotiwan.validator;

import by.wotiwan.dto.UpdateNoteDto;

import java.util.ArrayList;
import java.util.List;

public class UpdateNoteValidator {

    private UpdateNoteValidator() {}

    public static List<String> validate(UpdateNoteDto updateNoteDto) {
        List<String> errors = new ArrayList<>();
        if (updateNoteDto.id() == null) {
            errors.add("note id is null!");
        }
        if (updateNoteDto.userId() == null) {
            errors.add("user id is null!");
        }
        if (updateNoteDto.noteDescription().length() > 255) {
            errors.add("can't save description larger than 255 symbols!");
        }
        return errors;
    }

}
