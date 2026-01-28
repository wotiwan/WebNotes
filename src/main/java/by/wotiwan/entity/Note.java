package by.wotiwan.entity;

import java.time.LocalDateTime;
import java.util.Objects;

public class Note {

    private Long id;
    private Long userId;
    private String noteDescription;
    private LocalDateTime updatedAt;

    public Note() {}

    public Note(Long id, Long userId, String noteDescription, LocalDateTime updatedAt) {
        this.id = id;
        this.userId = userId;
        this.noteDescription = noteDescription;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getNoteDescription() {
        return noteDescription;
    }

    public void setNoteDescription(String noteDescription) {
        this.noteDescription = noteDescription;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return Objects.equals(id, note.id) && Objects.equals(userId, note.userId) && Objects.equals(noteDescription, note.noteDescription) && Objects.equals(updatedAt, note.updatedAt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, noteDescription, updatedAt);
    }

    @Override
    public String toString() {
        return "Note{" +
                "id=" + id +
                ", userId=" + userId +
                ", noteDescription='" + noteDescription + '\'' +
                ", updatedAt=" + updatedAt +
                '}';
    }

}
