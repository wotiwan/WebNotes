package com.wotiwan.utils;


import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionManager {

    // singleton
    private final static ConnectionManager INSTANCE = new ConnectionManager();
    private ConnectionManager() {}
    public static ConnectionManager getInstance() {
        return INSTANCE;
    }

    static {
        loadDriver();
        initConnectionPool();
    }

    // Значения получаемые из application.properties
    private static final String DB_URL_KEY = "db.url";
    private static final String DB_USERNAME_KEY = "db.username";
    private static final String DB_PASSWORD_KEY = "db.password";
    private static final String POOL_SIZE_KEY = "db.pool.size";
    private final static int DEFAULT_POOL_SIZE = 10;
    private static BlockingQueue<Connection> connectionPool;
    private static void initConnectionPool() {

        // Получаем размер нашего пула
        String poolSize = PropertiesUtil.get(POOL_SIZE_KEY);
        int size = poolSize == null ? DEFAULT_POOL_SIZE : Integer.parseInt(poolSize);

        connectionPool = new ArrayBlockingQueue<Connection>(size);

        for (int i = 0; i < size; i++) {

            Connection connection = open();

            // Возвращаем connection в pool после вызова метода close() не пересоздавая его.
            // proxy хранит в себе наш connection и следит за тем какие методы у него вызываются
            // вызванный метод сначала обрабатывается proxy, а затем в зависимости от того что это за метод
            // мы либо возвращаем наш connection обратно в пул, либо вызываем нужный нам метод (это лямбда)
            var proxyConnection = (Connection) Proxy.newProxyInstance(Connection.class.getClassLoader(),
                    new Class[]{Connection.class},
                    (proxy, method, args) -> method.getName().equals("close") ?
                            connectionPool.add((Connection) proxy) :
                            method.invoke(connection, args));
            connectionPool.add(proxyConnection);
        }

    }

    private static Connection open() {
        try {
            return DriverManager.getConnection(
                    PropertiesUtil.get(DB_URL_KEY),
                    PropertiesUtil.get(DB_USERNAME_KEY),
                    PropertiesUtil.get(DB_PASSWORD_KEY)
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // Метод к которому будем обращаться для получения коннекшена извне
    public Connection get() {
        try {
            return connectionPool.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    // Необходим для явной загрузки драйвера для бд, иначе томкат его не увидит
    private static void loadDriver() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

}
