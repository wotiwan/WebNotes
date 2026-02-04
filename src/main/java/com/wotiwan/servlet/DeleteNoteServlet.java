package com.wotiwan.servlet;

import com.wotiwan.dto.DeleteNoteDto;
import com.wotiwan.dto.UserDto;
import com.wotiwan.exception.DeleteNoteException;
import com.wotiwan.service.NoteService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import static com.wotiwan.utils.UrlPath.DELETE_NOTE;
import static com.wotiwan.utils.UrlPath.HOME;

@WebServlet(DELETE_NOTE)
public class DeleteNoteServlet extends HttpServlet {

    private final static NoteService noteService = NoteService.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();

        UserDto user = (UserDto) session.getAttribute("user"); // Находим id пользователя
        // Заполняем dto для удаления заметки id этой заметки и id пользователя
        DeleteNoteDto deleteNoteDto = new DeleteNoteDto(req.getParameter("id"), user.id());
        // Обращение к сервису для удаления заметки
        try {
            noteService.deleteNote(deleteNoteDto);
            // Обновляем кол-во страниц с заметками
            session.setAttribute("notesPages", noteService.getNotesPagesCount(user.id()));
        } catch (DeleteNoteException e) {
            req.setAttribute("errors", "Internal server error! Could not delete note");
        }

        // После чего снова открываем главную страницу
        resp.sendRedirect(HOME);
    }
}
