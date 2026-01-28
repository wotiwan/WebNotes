package by.wotiwan.dao;

import by.wotiwan.dto.UserFilter;
import by.wotiwan.entity.User;
import by.wotiwan.exception.DuplicateEmailException;
import by.wotiwan.exception.DuplicateNicknameException;
import by.wotiwan.utils.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// CRUD операции над User
public class UserDao implements Dao<User, Long, UserFilter> {
    // singleton
    private final static UserDao INSTANCE = new UserDao();
    private UserDao() {}
    public static UserDao getInstance() {return INSTANCE;}

    private final ConnectionManager connectionManager = ConnectionManager.getInstance();

    private final static String SAVE_SQL = """
            insert into users(nickname, email, password) values (?, ?, ?);
            """;

    private final static String  FIND_ALL_SQL = """
            select * from users;
            """;

    private final static String FIND_BY_ID_SQL = """
            select * from users where id = ?
            """;

    private final static String FIND_BY_EMAIL_PASSWORD_SQL = """
            select * from users where email = ? and password = ?;
            """;

    private final static String UPDATE_SQL = """
            update users set nickname = ?, email = ?, password = ? where id = ?;
            """;

    private final static String DELETE_SQL = """
            delete from users where id = ?;
            """;

    @Override
    public User save(User user) {

        try (Connection connection = connectionManager.get();
        PreparedStatement statement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, user.getNickname());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPasswordHash());

            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                user.setId(resultSet.getLong("id"));
            }

            return user;

        } catch (SQLException e) {

            if (e.getMessage().contains("users_email_key")) {
                throw new DuplicateEmailException();
            }
            if (e.getMessage().contains("users_nickname_key")) {
                throw new DuplicateNicknameException();
            }
            throw new RuntimeException(e);
        }

    }

    @Override
    public User findById(Long id) {

        User user = new User();

        try (Connection connection = connectionManager.get();
        PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_SQL)) {

            statement.setLong(1, id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = buildUser(resultSet);
            }

            return user;

        } catch (SQLException e) {
            throw new RuntimeException(e); // TODO: своё искл
        }

    }

    public User findByEmailPassword(String email, String password) {

        User user = new User();

        try (Connection connection = connectionManager.get();
        PreparedStatement statement = connection.prepareStatement(FIND_BY_EMAIL_PASSWORD_SQL)) {

            statement.setString(1, email);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                user = buildUser(resultSet);
            }

            return user;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<User> findAll() {

        List<User> users = new ArrayList<>();

        try (Connection connection = connectionManager.get();
             PreparedStatement statement = connection.prepareStatement(FIND_ALL_SQL)) {

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                users.add(buildUser(resultSet));
            }

            return users;

        } catch (SQLException e) {
            throw new RuntimeException(e); // TODO: своё искл
        }

    }

    @Override
    public List<User> findAll(UserFilter filter) {
        return null;
    }

    @Override
    public boolean update(User user) {

        try (Connection connection = connectionManager.get();
         PreparedStatement statement = connection.prepareStatement(UPDATE_SQL)) {

            statement.setString(1, user.getNickname());
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getPasswordHash());
            statement.setLong(4, user.getId());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            throw new RuntimeException(e); // TODO: своё искл
        }

    }

    @Override
    public boolean delete(Long id) {

        try (Connection connection = connectionManager.get();
        PreparedStatement statement = connection.prepareStatement(DELETE_SQL)) {

            statement.setLong(1, id);
            return statement.executeUpdate() > 0;


        } catch (SQLException e) {
            throw new RuntimeException(e); // TODO: своё искл
        }

    }

    private User buildUser(ResultSet resultSet) throws SQLException {
        return new User(
                resultSet.getLong("id"),
                resultSet.getString("nickname"),
                resultSet.getString("email"),
                resultSet.getString("password")
        );
    }

}
