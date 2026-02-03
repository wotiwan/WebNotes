package by.wotiwan.service;

import by.wotiwan.dao.NoteDao;
import by.wotiwan.dto.*;
import by.wotiwan.entity.Note;
import by.wotiwan.exception.*;
import by.wotiwan.mapper.CreateNoteMapper;
import by.wotiwan.mapper.CreateUserMapper;
import by.wotiwan.mapper.NoteMapper;
import by.wotiwan.mapper.UpdateNoteMapper;
import by.wotiwan.validator.NoteCreateValidator;
import by.wotiwan.utils.NotesPaginator;
import by.wotiwan.validator.UpdateNoteValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

        List<String> validationErrors = UpdateNoteValidator.validate(updateNoteDto);

        if (!validationErrors.isEmpty()) {
            throw new UpdateNoteException(validationErrors);
        }
        try {
            return noteDao.update(updateNoteMapper.mapFrom(updateNoteDto));
        } catch (DaoException e) {
            throw new UpdateNoteException("Internal server error! Could not save note");
        }

    }

    public void deleteNote(DeleteNoteDto deleteNoteDto) {

        try {
            if (Objects.equals(noteDao.findById(Long.parseLong(deleteNoteDto.id())).getUserId(), deleteNoteDto.user_id())) {
                noteDao.delete(Long.parseLong(deleteNoteDto.id()));
            }
        } catch (DaoException e) {
            throw new DeleteNoteException();
        }


    }

    public NoteDto createNote(CreateNoteDto createNoteDto) {

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

    // Метод для расчёта кол-ва страниц заметок пользователя
    public int getNotesPagesCount(Long userId) {
        int notesPerPage = NotesPaginator.getLimit();
        try {
            int notesCount = noteDao.findNotesCount(userId);
            if (notesCount % notesPerPage > 0) {
                return notesCount / notesPerPage + 1;
            } else {
                return notesCount / notesPerPage;
            }
        } catch (DaoException e) {
            // Пользователю необязательно знать что мы не смогли загрузить кол-во страниц, вернём просто дефолт
            // При этом можно залогировать
            return 1;
        }

    }

    public List<NoteDto> loadNotes(Long userId, int currentPage) {
        // От сервлета приходит только id пользователя
        // По этому id загружаем все note
        // Далее мапим их NotesDto и отправляем в ответ сервлету

        var notesFilter = new NoteFilter(
                null,
                userId,
                null,
                null,
                NotesPaginator.getLimit(),
                NotesPaginator.getOffset(currentPage)
        );
        
        try {
            List<Note> notes = noteDao.findAll(notesFilter);
            return notes.stream().map(noteMapper::mapFrom).collect(Collectors.toList());
        } catch (DaoException e) { // Если случилась какая-то ошибка в бд, то сообщаем пользователю
            throw new LoadNotesException();
        }

        // ПАГИНАЦИЯ:
        // в аргументы метода добавляем текущую страницу
        // Создан утил класс, который будет в себе хранить кол-во элементов на одной странице + готовить для нас данные
        // offset + limit

    }

}
