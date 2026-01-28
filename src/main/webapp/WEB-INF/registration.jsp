<%@ page isELIgnored="false" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Регистрация</title>
</head>
<body>

<h2>Форма регистрации</h2>

<c:if test="${not empty requestScope.errors}">
    <c:forEach var="error" items="${requestScope.errors}">
        <span>${error}</span>
        <br>
    </c:forEach>
</c:if>

<form action="/registration" method="post">
    <div>
        <label>nickname:</label><br>
        <input type="text" name="nickname" id="nickname" required>
    </div>
    <br>
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
    <button type="submit">Зарегистрироваться</button>
</form>

</body>
</html>
