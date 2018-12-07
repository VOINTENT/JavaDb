
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.andrey.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import ru.andrey.util.DbWork;
import ru.andrey.domain.Store;

/**
 *
 * @author AEGrishin
 */
public class StoreDAOImpl implements GenericDAO<Store> {

    @Override
    public Store create(Store object) {
        try {
            // Запрос вставки нового кортежа
            final String SQL_INSERT = "INSERT INTO Магазин(Название, Тип) VALUES (?, ?)";
            
            // Обращаемся к объекту БД
            DbWork db = DbWork.getInstance();
            // Обращаемся к состоянию БД
            Connection connection = db.getConnection();
            // Создаем состояния
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT);
            Statement statement = connection.createStatement();
            
            // Устанавливаем значения в запрос
            preparedStatement.setString(1, object.getName());
            preparedStatement.setString(2, object.getType());
            
            // Выполняем запрос
            preparedStatement.executeUpdate();
            
            // Возвращаем объект вставки
            return object;
        } catch (SQLException e) {
            throw new Error(e);
        }
    }

    @Override
    public void update(Store object) {
        try {
            // Запрос обновления данных кортежа
            final String SQL_UPDATE = "UPDATE Магазин SET Тип = ? WHERE Название = ?";

            // Обращаемся к объекту БД
            DbWork db = DbWork.getInstance();
            // Обращаемся к состоянию БД
            Connection connection = db.getConnection();
            // Создаем состояния
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE);

            // Устанавливаем значение в запрос
            preparedStatement.setString(1, object.getType());
            preparedStatement.setString(2, object.getName());

            // Выполняем запрос
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new Error(e);
        }
    }

    @Override
    public Store delete(Store object) {
        try {
            // Запрос на удаление кортежа
            final String SQL_DELETE = "DELETE FROM Магазин WHERE Название = ?";

            // Обращаемся к объекту БД
            DbWork db = DbWork.getInstance();
            // Обращаемся к состоянию БД
            Connection connection = db.getConnection();
            // Создаем состояния
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE);

            // Устанавливаем значение в запрос
            preparedStatement.setString(1, object.getName());

            // Выполняем запрос
            preparedStatement.executeUpdate();
            preparedStatement.close();

            return object;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public Store getByKey(String[] key) {
        try {
            // Запрос поиска кортежа по id
            final String SQL_SELECT_BY_ID = "SELECT * FROM Магазин WHERE Название = ?";

            // Обращаемся к объекту БД
            DbWork db = DbWork.getInstance();
            // Обращаемся к состоянию БД
            Connection connection = db.getConnection();
            // Создаем состояния
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_ID);

            // Устанавливаем значение в запрос
            String name = key[0];
            preparedStatement.setString(1, name);

            // Выполняем запрос
            ResultSet resultSet = preparedStatement.executeQuery();

            // Чтение результатов запроса
            Store store = null;
            if (resultSet.next()) {
                String type = resultSet.getString("Тип");

                store = new Store(name, type);
            }

            // Возвращаем результат
            return store;
        } catch (SQLException e) {
            throw new Error(e);
        }
    }

    @Override
    public List<Store> getAll() {
        try {
            // Запрос поиска всех кортежей
            final String SQL_SELECT = "SELECT * FROM Магазин";
            
            // Обращаемся к объекту БД
            DbWork db = DbWork.getInstance();
            // Обращаемся к состоянию БД
            Connection connection = db.getConnection();
            // Создаем состояния
            Statement statement = connection.createStatement();
            
            // Выполняем запрос
            ResultSet resultSet = statement.executeQuery(SQL_SELECT);
            
            // Чтение результатов поиска
            ArrayList<Store> stores = new ArrayList<>();
            while (resultSet.next()) {
                String name = resultSet.getString("Название");
                String type = resultSet.getString("Тип");
                
                stores.add(new Store(name, type));
            }
            
            // Возвращаем результат
            return stores;
        } catch (SQLException e) {
            throw new Error(e);
        }
    }
    
}
