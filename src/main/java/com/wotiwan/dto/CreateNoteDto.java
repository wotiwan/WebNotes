package com.wotiwan.dto;

public record CreateNoteDto(
        Long UserId,
        String noteDescription
) {
}
