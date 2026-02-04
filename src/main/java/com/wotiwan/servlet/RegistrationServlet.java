package com.wotiwan.servlet;

import com.wotiwan.dto.CreateUserDto;
import com.wotiwan.exception.RegistrationException;
import com.wotiwan.service.UserService;
import com.wotiwan.utils.JspHelper;
import com.wotiwan.utils.UrlPath;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(UrlPath.REGISTER)
public class RegistrationServlet extends HttpServlet {

    private final static UserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JspHelper.getPath(UrlPath.REGISTER)).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            userService.save(new CreateUserDto(
                    req.getParameter("nickname"),
                    req.getParameter("email"),
                    req.getParameter("password")
            ));
            resp.sendRedirect(UrlPath.LOGIN);
        } catch (RegistrationException e) {
            req.setAttribute("errors", e.getErrors());
            doGet(req, resp);
        }
    }
}
