package by.wotiwan.service;

import by.wotiwan.dao.UserDao;
import by.wotiwan.dto.CreateUserDto;
import by.wotiwan.dto.LoginUserDto;
import by.wotiwan.dto.UserDto;
import by.wotiwan.entity.User;
import by.wotiwan.exception.DuplicateEmailException;
import by.wotiwan.exception.DuplicateNicknameException;
import by.wotiwan.exception.LoginException;
import by.wotiwan.exception.RegistrationException;
import by.wotiwan.mapper.CreateUserMapper;
import by.wotiwan.mapper.UserMapper;
import by.wotiwan.utils.PasswordUtil;
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
        }
    }

    public UserDto login(LoginUserDto loginUserDto) {
        // Сначала находим пользователя по email
        User foundUser = userDao.findByEmail(loginUserDto.email());

        // Если пользователь найден - проверяем пароль по хешу
        if (foundUser.getId() != null) {

            if (PasswordUtil.checkPassword(loginUserDto.password(), foundUser.getPasswordHash())) {
                // Если хеши совпадают - авторизация успешна, возвращаем UserDto
                return userMapper.mapFrom(foundUser);
            } else {
                throw new LoginException();
            }

        } else {
            throw new LoginException();
        }

    }

    UserService() {}
    public static UserService getInstance() {return INSTANCE;}

}
