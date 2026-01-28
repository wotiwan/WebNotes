package by.wotiwan.servlet;

import by.wotiwan.dto.CreateNoteDto;
import by.wotiwan.dto.UserDto;
import by.wotiwan.service.NoteService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/createNote")
public class CreateNoteServlet extends HttpServlet {

    private final static NoteService noteService = NoteService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // TODO: Добавить проверки создания
        UserDto user = (UserDto) req.getSession().getAttribute("user"); // Находим id пользователя

        CreateNoteDto createNoteDto = new CreateNoteDto(
                user.id(),
                req.getParameter("description")
        );

        noteService.createNote(createNoteDto);

        resp.sendRedirect("/home");
    }
}
