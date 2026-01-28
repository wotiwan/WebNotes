package by.wotiwan.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Set;

// Данный фильтр не пускает не авторизованного пользователя на непубличные страницы
@WebFilter("/*")
public class AuthorizationFilter implements Filter {

    private final static Set<String> PUBLIC_PATH = Set.of("/login", "/register");

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (isUserLoggedIn(servletRequest) || isPublic(servletRequest)) {
            // Если пользователь авторизован ИЛИ путь публичный - ничего не делаем
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            // Иначе отправляем его на авторизацию
            ((HttpServletResponse) servletResponse).sendRedirect("/login");
        }

    }

    private boolean isPublic(ServletRequest servletRequest) {
        return PUBLIC_PATH.contains(((HttpServletRequest) servletRequest).getServletPath());
    }

    private boolean isUserLoggedIn(ServletRequest servletRequest) {
        return ((HttpServletRequest) servletRequest).getSession().getAttribute("user") != null;
    }

}
