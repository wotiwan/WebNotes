package by.wotiwan.dao;

import by.wotiwan.dto.NoteFilter;
import by.wotiwan.entity.Note;
import by.wotiwan.utils.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NoteDao implements Dao<Note, Long, NoteFilter> {
    // singleton
    private final static NoteDao INSTANCE = new NoteDao();
    private final ConnectionManager connectionManager = ConnectionManager.getInstance();

    private final static String SAVE_SQL = """
            insert into notes(user_id, note_description) values (?, ?);
            """;

    private final static String FIND_BY_ID_SQL = """
            select * from notes where id = ?;
            """;

    // Тут бы добавить пагинацию
    private final static String FIND_ALL_BY_USER_ID_SQL = """ 
            select * from notes where user_id = ? order by updated_at desc;
            """;

    private final static String DELETE_SQL = """
            delete from notes where id = ?;
            """;

    private final static String UPDATE_SQL = """
            update notes set note_description = ?, updated_at = now() where id = ? and user_id = ?; 
            """;

    @Override
    public Note save(Note o) {

        Note note = new Note();

        try (Connection connection = connectionManager.get();
             PreparedStatement statement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {

            statement.setLong(1, o.getUserId());
            statement.setString(2, o.getNoteDescription());
            statement.executeUpdate();

            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                note = findById(resultSet.getLong("id"));
            }

            return note;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Note findById(Long id) {

        Note note = new Note();

        try (Connection connection = connectionManager.get();
             PreparedStatement statement = connection.prepareStatement(FIND_BY_ID_SQL)) {

            statement.setLong(1, id);


            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                note = buidNote(resultSet);
            }

            return note;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Note> findAll() {
        return null;
    }

    @Override
    public List<Note> findAll(NoteFilter filter) {
        return null;
    }

    public List<Note> findAllByUserId(Long userId) {

        List<Note> notes = new ArrayList<>();

        try (Connection connection = connectionManager.get();
        PreparedStatement statement = connection.prepareStatement(FIND_ALL_BY_USER_ID_SQL)) {
            statement.setLong(1, userId);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                notes.add(buidNote(resultSet));
            }

            return notes;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean update(Note o) {
        try (Connection connection = connectionManager.get();
             PreparedStatement statement = connection.prepareStatement(UPDATE_SQL)) {

            statement.setString(1, o.getNoteDescription());
            statement.setLong(2, o.getId());
            statement.setLong(3, o.getUserId());

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(Long id) {

        try (Connection connection = connectionManager.get();
        PreparedStatement statement = connection.prepareStatement(DELETE_SQL)) {

            statement.setLong(1, id);

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private Note buidNote(ResultSet resultSet) throws SQLException {
        return new Note(
                resultSet.getLong("id"),
                resultSet.getLong("user_id"),
                resultSet.getString("note_description"),
                resultSet.getTimestamp("updated_at").toLocalDateTime()
        );
    }

    private NoteDao() {}
    public static NoteDao getInstance() {return INSTANCE;}

}
