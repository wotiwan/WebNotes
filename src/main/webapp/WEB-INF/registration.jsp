<%@ page isELIgnored="false" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Регистрация</title>
    <link rel="stylesheet" href="/css/auth.css">
</head>
<body>

<div class="auth-card">
    <h2>Регистрация</h2>

    <c:if test="${not empty requestScope.errors}">
        <div class="error">
            <ul>
                <c:forEach var="error" items="${requestScope.errors}">
                    <li>${error}</li>
                </c:forEach>
            </ul>
        </div>
    </c:if>

    <form action="/registration" method="post">

        <div class="form-group">
            <label>Никнейм</label>
            <input type="text" name="nickname" >
        </div>

        <div class="form-group">
            <label>Email</label>
            <input type="email" name="email" >
        </div>

        <div class="form-group">
            <label>Пароль</label>
            <input type="password" name="password" required>
        </div>

        <button type="submit">Зарегистрироваться</button>
    </form>

    <div class="auth-footer">
        Уже есть аккаунт?
        <a href="/login">Войти</a>
    </div>
</div>

</body>
</html>
