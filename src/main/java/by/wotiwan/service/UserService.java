package by.wotiwan.service;

import by.wotiwan.dao.UserDao;
import by.wotiwan.dto.CreateUserDto;
import by.wotiwan.dto.UserDto;
import by.wotiwan.exception.DuplicateEmailException;
import by.wotiwan.exception.DuplicateNicknameException;
import by.wotiwan.exception.RegistrationException;
import by.wotiwan.mapper.CreateUserMapper;
import by.wotiwan.mapper.UserMapper;
import by.wotiwan.validator.UserCreateValidator;

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
        if (errors.size() > 0) {
            throw new RegistrationException(errors);
        }

        try { // В случае если email или nickname уже существуют, бросим ошибку
            return userMapper.mapFrom(userDao.save(createUserMapper.mapFrom(createUserDto)));
        } catch (DuplicateEmailException e) {
            throw new RegistrationException("This email alredy taken!"); // TODO: Можно добавить локализацию, как нибудь через application.utils
        } catch (DuplicateNicknameException e) {
            throw new RegistrationException("This nickname alredy taken!");
        }
    }

    UserService() {}
    public static UserService getInstance() {return INSTANCE;}

}
