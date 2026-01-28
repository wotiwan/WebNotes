package by.wotiwan.mapper;

import by.wotiwan.dto.NoteDto;
import by.wotiwan.entity.Note;

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
