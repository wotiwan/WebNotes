package com.wotiwan.validator;

import com.wotiwan.dto.CreateNoteDto;

import java.util.ArrayList;
import java.util.List;

public final class NoteCreateValidator {

    private NoteCreateValidator() {}

    public static List<String> validate(CreateNoteDto createNoteDto) {

        List<String> errors = new ArrayList<>();

        if (createNoteDto.noteDescription().length() > 255) {
            errors.add("can't save description larger than 255 symbols!");
        }
        if (createNoteDto.UserId() == null) {
            errors.add("user id is null!");
        }
        return errors;
    }
}
