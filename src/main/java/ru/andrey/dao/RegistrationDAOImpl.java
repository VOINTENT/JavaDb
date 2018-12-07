
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.andrey.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import ru.andrey.util.DbWork;
import ru.andrey.domain.Registration;

/**
 *
 * @author AEGrishin
 */
public class RegistrationDAOImpl implements GenericDAO<Registration> {

    @Override
    public Registration create(Registration object) {
        try {
            // Запрос вставки нового кортежа
            final String SQL_INSERT = "INSERT INTO Регистрация(Имя, Дата_регистрации, Количество_позиций) VALUES (?, ?, ?)";
            
            // Обращаемся к объекту БД
            DbWork db = DbWork.getInstance();
            // Обращаемся к состоянию БД
            Connection connection = db.getConnection();
            // Создаем состояния
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT);
            
            // Устанавливаем значения в запрос
            preparedStatement.setString(1, object.getName());
            preparedStatement.setDate(2, (Date) object.getDate());
            preparedStatement.setInt(3, object.getCountPositions());
            
            // Выполняем запрос
            preparedStatement.executeUpdate();
            
            // Возвращаем объект вставки
            return object;
        } catch (SQLException e) {
            throw new Error(e);
        }
    }

    @Override
    public void update(Registration object) {
        try {
            // Запрос обновления данных кортежа
            final String SQL_UPDATE = "UPDATE Регистрация SET Дата_регистрации = ?, Количество_позиций = ? WHERE Имя = ?";

            // Обращаемся к объекту БД
            DbWork db = DbWork.getInstance();
            // Обращаемся к состоянию БД
            Connection connection = db.getConnection();
            // Создаем состояния
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE);

            // Устанавливаем значение в запрос
            preparedStatement.setString(3, object.getName());
            preparedStatement.setDate(1, (Date) object.getDate());
            preparedStatement.setInt(2, object.getCountPositions());

            // Выполняем запрос
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new Error(e);
        }
    }

    @Override
    public Registration delete(Registration object) {
        try {
            // Запрос на удаление кортежа
            final String SQL_DELETE = "DELETE FROM Регистрация WHERE Имя = ?";

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
    public Registration getByKey(String[] key) {
        try {
            // Запрос поиска кортежа по id
            final String SQL_SELECT_BY_ID = "SELECT * FROM Регистрация WHERE Имя = ?";

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
            Registration registration = null;
            if (resultSet.next()) {
                java.util.Date date = resultSet.getDate("Дата_регистрации");
                int countPositions = resultSet.getInt("Количество_позиций");

                registration = new Registration(name, date, countPositions);
            }

            // Возвращаем результат
            return registration;
        } catch (SQLException e) {
            throw new Error(e);
        }
    }


    @Override
    public List<Registration> getAll() {
        try {
            // Запрос поиска всех кортежей
            final String SQL_SELECT = "SELECT * FROM Регистрация";
            
            // Обращаемся к объекту БД
            DbWork db = DbWork.getInstance();
            // Обращаемся к состоянию БД
            Connection connection = db.getConnection();
            // Создаем состояния
            Statement statement = connection.createStatement();
            
            // Выполняем запрос
            ResultSet resultSet = statement.executeQuery(SQL_SELECT);
            
            // Чтение результатов поиска
            ArrayList<Registration> registrations = new ArrayList<>();
            while (resultSet.next()) {
                String name = resultSet.getString("Имя");
                java.util.Date date = resultSet.getDate("Дата_регистрации");
                int countPositions = resultSet.getInt("Количество_позиций");
                
                registrations.add(new Registration(name, date, countPositions));
            }
            
            // Возвращаем результат
            return registrations;
        } catch (SQLException e) {
            throw new Error(e);
        }
    }
    
}
