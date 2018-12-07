package ru.andrey.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DbWork {
    
    private Connection connection;  // Состояние
    private String dbAddress;        // Адрес БД
    private int dbPort;             // Порт БД
    private String dbName;          // Имя БД
    private String userName;        // Имя пользователя БД
    private String userPassword;    // Пароль пользователя БД
    private static DbWork db;       // Статичный объект БД
    
    // Установить соединения
    private void connect() {
        try {
            readProperties();
            connection = DriverManager.getConnection(dbAddress + dbPort + "/" + dbName, userName, userPassword);
        } catch (SQLException e) {
            throw new Error(e);
        }
    }
    
    // Получить экзмемпляр объект БД
    public static DbWork getInstance() {
        if (db == null) {
            db = new DbWork();
        }
        return db;
    }
    
    // Получить состояние
    public Connection getConnection() {
        if (connection == null) {
            connect();
        }
        return connection;
    }

    // Очистить таблицу
    public void cleanTable(String tableName) {
        //language=sql
        String SQL_TRUNCATE_TABLE = "TRUNCATE TABLE " + tableName;
        try {
            Statement statement = getConnection().createStatement();
            statement.executeUpdate(SQL_TRUNCATE_TABLE);
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    // Прочитать свойства из файла и записать в поля
    private void readProperties() {
        Properties properties;
        try {
            File file = new File("src/main/resources/data.properties");
            if (file.exists()) {
                properties = new Properties();
                properties.load(new FileInputStream(file));
                dbAddress = properties.getProperty("db_address");
                dbPort = Integer.parseInt(properties.getProperty("db_port"));
                dbName = properties.getProperty("db_name");
                userName = properties.getProperty("user_name");
                userPassword = properties.getProperty("user_password");
            }
        } catch (IOException | NumberFormatException e) {
            throw new Error(e);
        }
    }
}
