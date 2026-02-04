package com.wotiwan.mapper;

import com.wotiwan.dto.CreateNoteDto;
import com.wotiwan.entity.Note;

public class CreateNoteMapper implements Mapper<Note, CreateNoteDto> {
    private final static CreateNoteMapper INSTANCE = new CreateNoteMapper();
    private CreateNoteMapper() {}
    public static CreateNoteMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public Note mapFrom(CreateNoteDto createNoteDto) {
        return new Note(null, createNoteDto.UserId(), createNoteDto.noteDescription(), null);
    }
}
