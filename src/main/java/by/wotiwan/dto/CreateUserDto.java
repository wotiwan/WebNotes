package by.wotiwan.dto;

public record CreateUserDto(
        String nickname,
        String email,
        String password
) {}
