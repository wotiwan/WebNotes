package by.wotiwan.service;

import by.wotiwan.dao.UserDao;
import by.wotiwan.dto.CreateUserDto;
import by.wotiwan.dto.UserDto;
import by.wotiwan.mapper.CreateUserMapper;
import by.wotiwan.mapper.UserMapper;

public class UserService {
    // singleton
    private final static UserService INSTANCE = new UserService();
    private final static UserDao userDao = UserDao.getInstance();
    private final static CreateUserMapper createUserMapper = CreateUserMapper.getInstance();
    private final static UserMapper userMapper = UserMapper.getInstance();

    public UserDto save(CreateUserDto createUserDto) {
        // TODO: добавить валидацию
        return userMapper.mapFrom(userDao.save(createUserMapper.mapFrom(createUserDto)));
    }

    UserService() {}
    public static UserService getInstance() {return INSTANCE;}

}
