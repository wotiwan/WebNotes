package by.wotiwan;

import by.wotiwan.dao.UserDao;
import by.wotiwan.dto.CreateUserDto;
import by.wotiwan.entity.User;
import by.wotiwan.service.UserService;
import by.wotiwan.utils.ConnectionManager;

public class App {
    public static void main(String[] args) {

        UserDao userDao = UserDao.getInstance();
        UserService userService = UserService.getInstance();

        User user = new User();
        user.setId(1L);
        user.setEmail("iwanpomogaev@yandex.ru");
        user.setNickname("wotiwan");
        user.setPasswordHash("12345");

//        System.out.println(userDao.save(user));
//        System.out.println(userDao.update(user));

        System.out.println(userService.save(new CreateUserDto("wotiwan9", "asfjasf", "dasdasd")));

    }
}
