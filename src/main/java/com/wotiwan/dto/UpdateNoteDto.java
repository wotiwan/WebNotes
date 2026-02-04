package com.wotiwan.dto;

public record UpdateNoteDto (
        Long userId,
        String id,
        String noteDescription
) {}
