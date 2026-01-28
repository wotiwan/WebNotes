package by.wotiwan.servlet;

import by.wotiwan.dto.CreateUserDto;
import by.wotiwan.exception.RegistrationException;
import by.wotiwan.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {

    private final static UserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            userService.save(new CreateUserDto(
                    req.getParameter("nickname"),
                    req.getParameter("email"),
                    req.getParameter("password")
            ));
            req.getRequestDispatcher("/login").forward(req, resp);
        } catch (RegistrationException e) {
            req.setAttribute("errors", e.getErrors());
            doGet(req, resp);
        }
    }
}
