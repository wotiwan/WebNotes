package com.wotiwan.service;

import com.wotiwan.dao.UserDao;
import com.wotiwan.dto.CreateUserDto;
import com.wotiwan.dto.LoginUserDto;
import com.wotiwan.dto.UserDto;
import com.wotiwan.entity.User;
import com.wotiwan.exception.*;
import com.wotiwan.exception.*;
import com.wotiwan.mapper.CreateUserMapper;
import com.wotiwan.mapper.UserMapper;
import com.wotiwan.utils.PasswordUtil;
import com.wotiwan.validator.UserCreateValidator;

import java.util.List;

public class UserService {
    // singleton
    private final static UserService INSTANCE = new UserService();
    private final static UserDao userDao = UserDao.getInstance();
    private final static CreateUserMapper createUserMapper = CreateUserMapper.getInstance();
    private final static UserMapper userMapper = UserMapper.getInstance();

    public UserDto save(CreateUserDto createUserDto) {

        // Валидация входных данных
        List<String> errors = UserCreateValidator.validate(createUserDto);
        if (!errors.isEmpty()) {
            throw new RegistrationException(errors);
        }

        // Хэшируем пароль
        User user = createUserMapper.mapFrom(createUserDto);
        user.setPasswordHash(PasswordUtil.hashPassword(user.getPasswordHash()));

        try { // В случае если email или nickname уже существуют, бросим ошибку
            // мапим полученный от dao entity в UserDto
            return userMapper.mapFrom(userDao.save(user));
        } catch (DuplicateEmailException e) {
            throw new RegistrationException("This email alredy taken!");
        } catch (DuplicateNicknameException e) {
            throw new RegistrationException("This nickname alredy taken!");
        } catch (DaoException e) {
            throw new RegistrationException("Failed to create account, try again later.");
        }
    }

    public UserDto login(LoginUserDto loginUserDto) {
        // Сначала находим пользователя по email
        try {
            User foundUser = userDao.findByEmail(loginUserDto.email());
            // Если пользователь найден - проверяем пароль по хешу
            if (foundUser.getId() != null) {

                if (PasswordUtil.checkPassword(loginUserDto.password(), foundUser.getPasswordHash())) {
                    // Если хеши совпадают - авторизация успешна, возвращаем UserDto
                    return userMapper.mapFrom(foundUser);
                } else {
                    throw new LoginException("email or password is incorrect!");
                }

            } else {
                throw new LoginException("email or password is incorrect!");
            }
        } catch (DaoException e) {
            throw new LoginException("Authentication unavailable, try again later");
        }
    }

    UserService() {}
    public static UserService getInstance() {return INSTANCE;}

}
