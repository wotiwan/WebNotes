package by.wotiwan.dto;

public record CreateNoteDto(
        Long UserId,
        String noteDescription
) {
}
