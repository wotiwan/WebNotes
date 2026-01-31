package by.wotiwan.servlet;

import by.wotiwan.dto.NoteDto;
import by.wotiwan.dto.UserDto;
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
        List<NoteDto> notes = noteService.loadNotes(user.id());

        req.setAttribute("notes", notes);

        req.getRequestDispatcher(JspHelper.getPath(HOME)).forward(req, resp);
    }
}
