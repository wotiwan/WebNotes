package by.wotiwan.mapper;

import by.wotiwan.dto.CreateUserDto;
import by.wotiwan.entity.User;

import java.time.LocalDate;

public class CreateUserMapper implements Mapper<User, CreateUserDto> {
    private final static CreateUserMapper INSTANCE = new CreateUserMapper();
    private CreateUserMapper() {}
    public static CreateUserMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public User mapFrom(CreateUserDto o) {

        User user = new User();

        user.setNickname(o.nickname());
        user.setEmail(o.email());
        user.setPasswordHash(o.password()); // TODO: добавить хеширование

        return user;
    }
}
