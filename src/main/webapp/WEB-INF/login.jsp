<%@ page isELIgnored="false" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Вход</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/auth.css">
</head>
<body>

<div class="auth-card">
    <h2>Форма входа</h2>

    <c:if test="${not empty requestScope.errors}">
        <div class="error">
            <ul>
                <c:forEach var="error" items="${requestScope.errors}">
                    <li>${error}</li>
                </c:forEach>
            </ul>
        </div>
    </c:if>

    <form action="/login" method="post">

        <div class="form-group">
            <label>Email</label>
            <input type="email" name="email" required>
        </div>

        <div class="form-group">
            <label>Пароль</label>
            <input type="password" name="password" required>
        </div>

        <button type="submit">Войти</button>
    </form>

    <div class="auth-footer">
        Нет аккаунта?
        <a href="/registration">Зарегистрироваться</a>
    </div>
</div>


</body>

</html>
