package by.wotiwan.dao;

public interface Dao<T, K, F> {
    // T - тип entity
    // K - тип primary key для этого entity
    public T save(); // Возвращает в ответ entity
    public T findById(K id); // Ищем по первичному ключу
    public T findAll(); // Найти всех, если нужно
    public T findAll(F filter); // Найти всех по нужным полям
    public boolean update(); // Возвращает результат обновления
    public boolean delete(K id); // Возвращает результат удаления - успешно или неуспешно
}
