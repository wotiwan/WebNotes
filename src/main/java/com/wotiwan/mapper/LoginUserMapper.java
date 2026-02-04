package com.wotiwan.mapper;

import com.wotiwan.dto.LoginUserDto;
import com.wotiwan.entity.User;

public class LoginUserMapper implements Mapper<User, LoginUserDto> {
    // singleton
    private final static LoginUserMapper INSTANCE = new LoginUserMapper();
    private LoginUserMapper() {}
    public static LoginUserMapper getInstance() {return INSTANCE;}


    @Override
    public User mapFrom(LoginUserDto loginUserDto) {
        User user = new User();
        user.setEmail(loginUserDto.email());
        user.setPasswordHash(loginUserDto.password());
        return user;
    }
}
