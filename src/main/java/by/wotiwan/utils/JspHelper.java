package by.wotiwan.utils;

// Класс для удобного создания пути до .jsp файлов
public final class JspHelper {

    public static String getPath(String jsp) {
        return "/WEB-INF/%s.jsp".formatted(jsp);
    }

}
