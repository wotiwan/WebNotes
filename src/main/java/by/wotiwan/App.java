package by.wotiwan;

import by.wotiwan.dao.NoteDao;
import by.wotiwan.dao.UserDao;
import by.wotiwan.dto.CreateUserDto;
import by.wotiwan.entity.Note;
import by.wotiwan.entity.User;
import by.wotiwan.service.UserService;
import by.wotiwan.utils.ConnectionManager;

public class App {
    public static void main(String[] args) {


        NoteDao noteDao = NoteDao.getInstance();
        Note note = new Note();
        note.setNoteDescription("barabecus");
        note.setUserId(42L);

//        System.out.println(noteDao.save(note));

        noteDao.delete(2L);

        note = noteDao.findById(1L);

        note.setNoteDescription("Всем привет я покушал");

        noteDao.update(note);
        System.out.println(noteDao.findAllByUserId(42L));

    }
}
