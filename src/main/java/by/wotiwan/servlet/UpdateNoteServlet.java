package by.wotiwan.servlet;

import by.wotiwan.dto.UpdateNoteDto;
import by.wotiwan.dto.UserDto;
import by.wotiwan.service.NoteService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static by.wotiwan.utils.UrlPath.HOME;
import static by.wotiwan.utils.UrlPath.UPDATE_NOTE;

@WebServlet(UPDATE_NOTE)
public class UpdateNoteServlet extends HttpServlet {

    private static final NoteService noteService = NoteService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UpdateNoteDto updateNoteDto = new UpdateNoteDto(
                ((UserDto) req.getSession().getAttribute("user")).id(),
                req.getParameter("id"),
                req.getParameter("description")
        );

        noteService.updateNote(updateNoteDto);

        resp.sendRedirect(HOME);

    }
}
