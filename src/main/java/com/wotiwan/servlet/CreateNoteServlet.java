package com.wotiwan.servlet;

import com.wotiwan.dto.CreateNoteDto;
import com.wotiwan.dto.UserDto;
import com.wotiwan.exception.CreateNoteException;
import com.wotiwan.service.NoteService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import static com.wotiwan.utils.UrlPath.CREATE_NOTE;
import static com.wotiwan.utils.UrlPath.HOME;

@WebServlet(CREATE_NOTE)
public class CreateNoteServlet extends HttpServlet {

    private final static NoteService noteService = NoteService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        UserDto user = (UserDto) session.getAttribute("user"); // Находим id пользователя

        CreateNoteDto createNoteDto = new CreateNoteDto(
                user.id(),
                req.getParameter("description")
        );

        // Пытаемся создать заметку - если есть ошибка валидации или ошибка бд - сообщаем об этом пользователю
        try {
            noteService.createNote(createNoteDto);
            // Обновляем кол-во страниц с заметками
            session.setAttribute("notesPages", noteService.getNotesPagesCount(user.id()));
        } catch (CreateNoteException e) {
            session.setAttribute("errors", e.getERRORS());
        }

        resp.sendRedirect(HOME);
    }
}
