package by.wotiwan.servlet;

import by.wotiwan.dto.DeleteNoteDto;
import by.wotiwan.dto.UserDto;
import by.wotiwan.service.NoteService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/deleteNote")
public class DeleteNoteServlet extends HttpServlet {

    private final static NoteService noteService = NoteService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UserDto user = (UserDto) req.getSession().getAttribute("user"); // Находим id пользователя
        // Заполняем dto для удаления заметки id этой заметки и id пользователя
        DeleteNoteDto deleteNoteDto = new DeleteNoteDto(req.getParameter("id"), user.id());
        // Обращение к сервису для удаления заметки
        noteService.deleteNote(deleteNoteDto);
        // После чего снова открываем главную страницу
        resp.sendRedirect("/home");
    }
}
