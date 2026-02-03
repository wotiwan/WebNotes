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

    <!-- Основные стили -->
    <link rel="stylesheet" href="/css/notes.css">
</head>
<body>

<div class="page">

    <!-- ===== Верхняя панель ===== -->
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

    <!-- ===== Создание заметки ===== -->
    <div class="create-box">
        <h3>Новая заметка</h3>
        <form action="/createNote" method="post">
            <textarea name="description"
                      placeholder="Введите описание заметки..."
                      required></textarea>
            <br><br>
            <button type="submit">Создать заметку</button>
        </form>
    </div>

    <!-- ===== Список заметок ===== -->
    <h3 class="notes-title">Существующие заметки</h3>

    <c:if test="${empty notes}">
        <p class="empty">Заметок пока нет.</p>
    </c:if>

    <c:forEach var="note" items="${notes}">
        <div class="note" id="note-${note.id()}">

            <!-- Текст заметки -->
            <div class="note-text" id="text-${note.id()}">
                ${note.noteDescription()}
            </div>

            <!-- Форма редактирования (скрыта) -->
            <form action="/updateNote"
                  method="post"
                  class="edit-form"
                  id="form-${note.id()}"
                  style="display: none;">

                <input type="hidden" name="id" value="${note.id()}">

                <textarea name="description" required>${note.noteDescription()}</textarea>

                <button type="submit">Сохранить</button>
            </form>

            <!-- Кнопки -->
            <div class="note-actions">
                <button type="button" onclick="editNote(${note.id()})">
                    Изменить
                </button>

                <form action="/deleteNote" method="post" style="display:inline;">
                    <input type="hidden" name="id" value="${note.id()}">
                    <button class="delete-btn" type="submit">Удалить</button>
                </form>
            </div>

        </div>
    </c:forEach>

    <!-- ===== Пагинация ===== -->
    <c:if test="${notesPages > 1}">
        <div class="pagination">
            <!-- Номера страниц -->
            <c:forEach begin="1" end="${notesPages}" var="i">
                <c:choose>
                    <c:when test="${i == currentPage}">
                        <span class="current">${i}</span>
                    </c:when>
                    <c:otherwise>
                        <a href="?page=${i}">${i}</a>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
    </c:if>

</div>
<script>
    function editNote(id) {
        document.getElementById("text-" + id).style.display = "none";
        document.getElementById("form-" + id).style.display = "block";
    }
</script>
</body>
</html>
