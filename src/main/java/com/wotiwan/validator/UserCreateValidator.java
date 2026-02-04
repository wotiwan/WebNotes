package com.wotiwan.validator;

import com.wotiwan.dto.CreateUserDto;

import java.util.ArrayList;
import java.util.List;

public final class UserCreateValidator {

    private UserCreateValidator() {}

    public static List<String> validate(CreateUserDto createUserDto) {
        List<String> errors = new ArrayList<>();

        if (createUserDto.nickname() == null || createUserDto.nickname().isBlank()) {
            errors.add("nickname can't be blank!"); // TODO: Можно добавить локализацию, как нибудь через application.utils
        }
        if (createUserDto.email() == null || createUserDto.email().isBlank()) {
            errors.add("email can't be blank!");
        }
        if (createUserDto.password() == null || createUserDto.password().length() < 8) {
            errors.add("password must be at least 8 symbols!"); // TODO: тут вообще вынести восьмёрку в двух местах
        }
        return errors;
    }

}
