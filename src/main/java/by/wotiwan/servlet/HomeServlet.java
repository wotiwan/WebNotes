package by.wotiwan.servlet;

import by.wotiwan.dto.NoteDto;
import by.wotiwan.dto.UserDto;
import by.wotiwan.exception.LoadNotesException;
import by.wotiwan.service.NoteService;
import by.wotiwan.utils.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import static by.wotiwan.utils.UrlPath.HOME;

@WebServlet(HOME)
public class HomeServlet extends HttpServlet {
    private final static NoteService noteService = NoteService.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        UserDto user = (UserDto) req.getSession().getAttribute("user");

        try {
            List<NoteDto> notes = noteService.loadNotes(user.id());
            req.setAttribute("notes", notes);
        } catch (LoadNotesException e) {
            req.setAttribute("errors", "Unable to load notes, try again later.");
        }

        req.getRequestDispatcher(JspHelper.getPath(HOME)).forward(req, resp);
    }
}
