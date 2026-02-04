package com.wotiwan.mapper;

import com.wotiwan.dto.NoteDto;
import com.wotiwan.entity.Note;

public class NoteMapper implements Mapper<NoteDto, Note> {
    private final static NoteMapper INSTANCE = new NoteMapper();
    private NoteMapper() {}
    public static NoteMapper getInstance() {
        return INSTANCE;
    }
    @Override
    public NoteDto mapFrom(Note note) {
        return new NoteDto(
                note.getId(),
                note.getNoteDescription()
        );
    }
}
