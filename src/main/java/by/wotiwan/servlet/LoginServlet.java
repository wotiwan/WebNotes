package by.wotiwan.servlet;

import by.wotiwan.dto.LoginUserDto;
import by.wotiwan.dto.UserDto;
import by.wotiwan.exception.LoginException;
import by.wotiwan.service.UserService;
import by.wotiwan.utils.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    UserService userService = UserService.getInstance();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Проверяем, не авторизован ли уже пользователь
        // в случае если пользователь уже авторизован - отправляем его на главную страницу
        if (req.getSession().getAttribute("user") != null) {
            resp.sendRedirect("/home");
        } else {
            // Иначе - отправляем его на страницу авторизации
            req.getRequestDispatcher(JspHelper.getPath("login")).forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        LoginUserDto loginUserDto = new LoginUserDto(req.getParameter("email"), req.getParameter("password"));
        try {
            UserDto userDto = userService.login(loginUserDto);
            // При успешном логине добавляем информацию о юзере в сессию и отправляем на главную страницу
            req.getSession().setAttribute("user", userDto);
            resp.sendRedirect("/home");
        } catch (LoginException e) {
            req.setAttribute("errors", "email or password is incorrect!");
            doGet(req, resp);
        }

    }
}
