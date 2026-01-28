<%@ page isELIgnored="false" %>
<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Заметки</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; }
        .top-bar { display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px; }
        .note { border: 1px solid #ddd; padding: 10px; margin-bottom: 10px; border-radius: 6px; }
        .note form { margin-top: 8px; }
        textarea { width: 100%; min-height: 60px; }
        button { padding: 6px 12px; cursor: pointer; }
    </style>
</head>
<body>

<div class="top-bar">
    <h2>Мои заметки</h2>

    <!-- Кнопка выхода -->
    <form action="/logout" method="post">
        <button type="submit">Выйти</button>
    </form>
</div>

<!-- Создание новой заметки -->
<h3>Новая заметка</h3>
<form action="/createNote" method="post">
    <textarea name="description" placeholder="Введите описание заметки..." required></textarea><br><br>
    <button type="submit">Создать заметку</button>
</form>

<hr>

<!-- Список заметок -->
<h3>Существующие заметки</h3>

<c:if test="${empty notes}">
    <p>Заметок пока нет.</p>
</c:if>

<c:forEach var="note" items="${notes}">
    <div class="note">
        <div>${note.description}</div>

        <!-- Удаление заметки -->
        <form action="/deleteNote" method="post">
            <input type="hidden" name="id" value="${note.id}">
            <button type="submit">Удалить</button>
        </form>
    </div>
</c:forEach>

</body>
</html>
