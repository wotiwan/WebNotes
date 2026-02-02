package by.wotiwan.servlet;

import by.wotiwan.dto.CreateNoteDto;
import by.wotiwan.dto.UserDto;
import by.wotiwan.exception.CreateNoteException;
import by.wotiwan.service.NoteService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static by.wotiwan.utils.UrlPath.CREATE_NOTE;
import static by.wotiwan.utils.UrlPath.HOME;

@WebServlet(CREATE_NOTE)
public class CreateNoteServlet extends HttpServlet {

    private final static NoteService noteService = NoteService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO: Добавить проверки создания // ГОТОВО
        UserDto user = (UserDto) req.getSession().getAttribute("user"); // Находим id пользователя

        CreateNoteDto createNoteDto = new CreateNoteDto(
                user.id(),
                req.getParameter("description")
        );

        // Пытаемся создать заметку - если есть ошибка валидации или ошибка бд - сообщаем об этом пользователю
        try {
            noteService.createNote(createNoteDto);
        } catch (CreateNoteException e) {
            req.setAttribute("errors", e.getERRORS());
        }

        resp.sendRedirect(HOME);
    }
}
