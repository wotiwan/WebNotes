package by.wotiwan.servlet;

import by.wotiwan.dto.CreateNoteDto;
import by.wotiwan.dto.UserDto;
import by.wotiwan.service.NoteService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import static by.wotiwan.utils.UrlPath.CREATE_NOTE;
import static by.wotiwan.utils.UrlPath.HOME;

@WebServlet(CREATE_NOTE)
public class CreateNoteServlet extends HttpServlet {

    private final static NoteService noteService = NoteService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO: Добавить проверки создания
        HttpSession session = req.getSession();
        UserDto user = (UserDto) session.getAttribute("user"); // Находим id пользователя

        CreateNoteDto createNoteDto = new CreateNoteDto(
                user.id(),
                req.getParameter("description")
        );

        noteService.createNote(createNoteDto);

        // Обновляем кол-во страниц с заметками
        session.setAttribute("notesPages", noteService.getNotesPagesCount(user.id()));

        resp.sendRedirect(HOME);
    }
}
