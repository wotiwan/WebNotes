package by.wotiwan.dto;

import java.time.LocalDateTime;

public record NoteFilter(
        Long id,
        Long userId,
        String noteDescription,
        LocalDateTime updatedAt,
        int limit,
        int offset
) {
}
