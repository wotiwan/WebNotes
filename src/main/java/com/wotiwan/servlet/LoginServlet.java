package com.wotiwan.servlet;

import com.wotiwan.dto.LoginUserDto;
import com.wotiwan.dto.UserDto;
import com.wotiwan.exception.LoginException;
import com.wotiwan.service.UserService;
import com.wotiwan.utils.JspHelper;
import com.wotiwan.utils.UrlPath;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(UrlPath.LOGIN)
public class LoginServlet extends HttpServlet {
    UserService userService = UserService.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Проверяем, не авторизован ли уже пользователь
        // в случае если пользователь уже авторизован - отправляем его на главную страницу
        if (req.getSession().getAttribute("user") != null) {
            resp.sendRedirect(UrlPath.HOME);
        } else {
            // Иначе - отправляем его на страницу авторизации
            req.getRequestDispatcher(JspHelper.getPath(UrlPath.LOGIN)).forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        LoginUserDto loginUserDto = new LoginUserDto(req.getParameter("email"), req.getParameter("password"));
        try {
            UserDto userDto = userService.login(loginUserDto);
            // При успешном логине добавляем информацию о юзере в сессию и отправляем на главную страницу
            req.getSession().setAttribute("user", userDto);
            resp.sendRedirect(UrlPath.HOME);
        } catch (LoginException e) {
            req.setAttribute("errors", e.getERRORS());
            doGet(req, resp);
        }

    }
}
