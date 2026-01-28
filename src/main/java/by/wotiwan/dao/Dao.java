package by.wotiwan.dao;

import java.util.List;

public interface Dao<T, K, F> {
    // T - тип entity
    // K - тип primary key для этого entity
    // F - фильтр
    public T save(T o); // Возвращает в ответ entity
    public T findById(K id); // Ищем по первичному ключу
    public List<T> findAll(); // Найти всех, если нужно
    public List<T> findAll(F filter); // Найти всех по нужным полям
    public boolean update(T o); // Возвращает результат обновления
    public boolean delete(K id); // Возвращает результат удаления - успешно или неуспешно
}
