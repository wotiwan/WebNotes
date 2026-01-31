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
    <link rel="stylesheet" href="/css/notes.css">
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
<div class="page">
    <div class="top-bar">
        <h2>Мои заметки</h2>

        <div>
            <div class="username">
                <p>${user.nickname()}</p>
            </div>
            <form action="/logout" method="post">
                <button class="logout-btn" type="submit">Выйти</button>
            </form>
        </div>

    </div>

    <div class="create-box">
        <h3>Новая заметка</h3>
        <form action="/createNote" method="post">
            <textarea name="description" placeholder="Введите описание заметки..." required></textarea><br><br>
            <button type="submit">Создать заметку</button>
        </form>
    </div>

    <h3 class="notes-title">Существующие заметки</h3>

    <c:if test="${empty notes}">
        <p class="empty">Заметок пока нет.</p>
    </c:if>

    <c:forEach var="note" items="${notes}">
        <div class="note">
            <div class="note-text">${note.noteDescription()}</div>

            <form action="/deleteNote" method="post">
                <input type="hidden" name="id" value="${note.id()}">
                <button class="delete-btn" type="submit">Удалить</button>
            </form>
        </div>
    </c:forEach>
</div>
</body>
</html>
