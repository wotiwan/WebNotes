package by.wotiwan.mapper;

import by.wotiwan.dto.UpdateNoteDto;
import by.wotiwan.entity.Note;

public class UpdateNoteMapper implements Mapper<Note, UpdateNoteDto> {

    private final static UpdateNoteMapper INSTANCE = new UpdateNoteMapper();
    private UpdateNoteMapper() {}
    public static UpdateNoteMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public Note mapFrom(UpdateNoteDto updateNoteDto) {
        return new Note(
                Long.parseLong(updateNoteDto.id()),
                updateNoteDto.userId(),
                updateNoteDto.noteDescription(),
                null
        );
    }
}
