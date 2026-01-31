package by.wotiwan.servlet;

import by.wotiwan.dto.CreateUserDto;
import by.wotiwan.exception.RegistrationException;
import by.wotiwan.service.UserService;
import by.wotiwan.utils.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static by.wotiwan.utils.UrlPath.LOGIN;
import static by.wotiwan.utils.UrlPath.REGISTER;

@WebServlet(REGISTER)
public class RegistrationServlet extends HttpServlet {

    private final static UserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JspHelper.getPath(REGISTER)).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            userService.save(new CreateUserDto(
                    req.getParameter("nickname"),
                    req.getParameter("email"),
                    req.getParameter("password")
            ));
            resp.sendRedirect(LOGIN);
        } catch (RegistrationException e) {
            req.setAttribute("errors", e.getErrors());
            doGet(req, resp);
        }
    }
}
