<%@ page isELIgnored="false" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Вход</title>
</head>
<body>

<h2>Форма входа</h2>

<c:if test="${not empty requestScope.errors}">
    <h1>${requestScope.errors}</h1>
</c:if>

<form action="/login" method="post">
    <div>
        <label>Email:</label><br>
        <input type="email" name="email" id="email" required>
    </div>
    <br>
    <div>
        <label>Пароль:</label><br>
        <input type="password" name="password" id="password"required>
    </div>
    <br>
    <button type="submit">Войти</button>
</form>

</body>
</html>
