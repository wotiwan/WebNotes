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
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static by.wotiwan.utils.UrlPath.HOME;

@WebServlet(HOME)
public class HomeServlet extends HttpServlet {
    private final static NoteService noteService = NoteService.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();

        UserDto user = (UserDto) session.getAttribute("user");

        // Находим кол-во страниц с заметками
        // При каждом переключении страницы - находим кол-во заметок в бд, по идее пагинация сделала только хуже
        // По-этому добавим сохранение кол-ва страниц в сессии, обновлять будем только при удалении / добавлении
        if (session.getAttribute("notesPages") == null) {
            session.setAttribute("notesPages", noteService.getNotesPagesCount(user.id()));
        }

        // Узнаём из формы на какой мы странице заметок
        String pageParam = req.getParameter("page");
        // Если пользователь не нажимал на кнопки пагинации - параметр будет null, отдаём просто первую страницу
        int curPage = pageParam == null ? 1 : Integer.parseInt(pageParam);


        try {
            // Загружаем заметки текущей страницы
            List<NoteDto> notes = noteService.loadNotes(user.id(), curPage);

            // Возвращаем полученные заметки текущей страницы и номер текущей страницы для фронта
            req.setAttribute("notes", notes);
            req.setAttribute("currentPage", curPage);
        } catch (LoadNotesException e) {
            req.setAttribute("errors", "Unable to load notes, try again later.");
        }

        req.getRequestDispatcher(JspHelper.getPath(HOME)).forward(req, resp);

        // Удаляем полученные из других сервлетов ошибки, т.к. показать их нужно один раз, а не каждый раз пока активна сессия
        session.removeAttribute("errors");
    }
}
