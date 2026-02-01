package by.wotiwan.service;

import by.wotiwan.dao.NoteDao;
import by.wotiwan.dto.CreateNoteDto;
import by.wotiwan.dto.DeleteNoteDto;
import by.wotiwan.dto.NoteDto;
import by.wotiwan.dto.UpdateNoteDto;
import by.wotiwan.entity.Note;
import by.wotiwan.mapper.CreateNoteMapper;
import by.wotiwan.mapper.CreateUserMapper;
import by.wotiwan.mapper.NoteMapper;
import by.wotiwan.mapper.UpdateNoteMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NoteService {
    private final static NoteService INSTANCE = new NoteService();

    private final static NoteDao noteDao = NoteDao.getInstance();
    private final static NoteMapper noteMapper = NoteMapper.getInstance();
    private final static CreateNoteMapper createNoteMapper = CreateNoteMapper.getInstance();
    private final static UpdateNoteMapper updateNoteMapper = UpdateNoteMapper.getInstance();

    private NoteService() {}
    public static NoteService getInstance() {return INSTANCE;}

    public boolean updateNote(UpdateNoteDto updateNoteDto) {
        return noteDao.update(updateNoteMapper.mapFrom(updateNoteDto));
    }

    public void deleteNote(DeleteNoteDto deleteNoteDto) {
        // TODO: Сюда надо добавить проверку на user_id ещё
        // TODO: Добавить обработку ошибок
        noteDao.delete(Long.parseLong(deleteNoteDto.id()));
    }

    public NoteDto createNote(CreateNoteDto createNoteDto) {
        // TODO: добавить валидатор
        var note = noteDao.save(createNoteMapper.mapFrom(createNoteDto));
        return noteMapper.mapFrom(note);
    }

    public List<NoteDto> loadNotes(Long userId) {
        // От сервлета приходит только id пользователя
        // По этому id загружаем все note
        // Далее мапим их NotesDto и отправляем в ответ сервлету
        List<Note> notes = noteDao.findAllByUserId(userId);
        return notes.stream().map(noteMapper::mapFrom).collect(Collectors.toList());
    }

}
