package com.wotiwan.mapper;

import com.wotiwan.dto.UserDto;
import com.wotiwan.entity.User;

public class UserMapper implements Mapper<UserDto, User> {
    private final static UserMapper INSTANCE = new UserMapper();
    private UserMapper() {}
    public static UserMapper getInstance() {
        return INSTANCE;
    }
    @Override
    public UserDto mapFrom(User user) {

        return new UserDto(
                user.getId(),
                user.getNickname(),
                user.getEmail()
        );

    }
}
