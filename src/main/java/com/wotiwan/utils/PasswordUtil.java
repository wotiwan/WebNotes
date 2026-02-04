package com.wotiwan.utils;
import org.mindrot.jbcrypt.BCrypt;

// Утилитный класс хеширования паролей и проверки хешей
public final class PasswordUtil {

    private PasswordUtil() {}

    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean checkPassword(String password, String hashed) {
        return BCrypt.checkpw(password, hashed);
    }

}