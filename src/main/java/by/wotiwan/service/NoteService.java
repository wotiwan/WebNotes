package by.wotiwan.service;

import by.wotiwan.dao.NoteDao;
import by.wotiwan.dto.CreateNoteDto;
import by.wotiwan.dto.DeleteNoteDto;
import by.wotiwan.dto.NoteDto;
import by.wotiwan.dto.UpdateNoteDto;
import by.wotiwan.entity.Note;
import by.wotiwan.exception.CreateNoteException;
import by.wotiwan.exception.DaoException;
import by.wotiwan.exception.LoadNotesException;
import by.wotiwan.mapper.CreateNoteMapper;
import by.wotiwan.mapper.CreateUserMapper;
import by.wotiwan.mapper.NoteMapper;
import by.wotiwan.mapper.UpdateNoteMapper;
import by.wotiwan.validator.NoteCreateValidator;

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
        // TODO: добавить валидатор // ГОТОВО

        List<String> validationErrors = NoteCreateValidator.validate(createNoteDto);

        if (!validationErrors.isEmpty()) {
            throw new CreateNoteException(validationErrors);
        }

        try {
            var note = noteDao.save(createNoteMapper.mapFrom(createNoteDto));
            return noteMapper.mapFrom(note);
        } catch (DaoException e) {
            throw new CreateNoteException("Internal server error! Could not save note");
        }

    }

    public List<NoteDto> loadNotes(Long userId) {
        // От сервлета приходит только id пользователя
        // По этому id загружаем все note
        // Далее мапим их NotesDto и отправляем в ответ сервлету
        try {
            List<Note> notes = noteDao.findAllByUserId(userId);
            return notes.stream().map(noteMapper::mapFrom).collect(Collectors.toList());
        } catch (DaoException e) { // Если случилась какая-то ошибка в бд, то сообщаем пользователю
            throw new LoadNotesException();
        }

    }

}
