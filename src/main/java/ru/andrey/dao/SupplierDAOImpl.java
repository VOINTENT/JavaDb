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
import ru.andrey.domain.Supplier;

/**
 *
 * @author AEGrishin
 */
public class SupplierDAOImpl implements GenericDAO<Supplier> {

    @Override
    public Supplier create(Supplier object) {
        try {
            // Запрос вставки нового кортежа
            final String SQL_INSERT = "INSERT INTO Поставщик(Тип, Поставщик) VALUES (?, ?)";
            
            // Обращаемся к объекту БД
            DbWork db = DbWork.getInstance();
            // Обращаемся к состоянию БД
            Connection connection = db.getConnection();
            // Создаем состояния
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT);
            Statement statement = connection.createStatement();
            
            // Устанавливаем значения в запрос
            preparedStatement.setString(1, object.getType());
            preparedStatement.setString(2, object.getSupplier());
            
            // Выполняем запрос
            preparedStatement.executeUpdate();
            
            return object;
        } catch (SQLException e) {
            throw new Error(e);
        }
    }

    @Override
    public void update(Supplier object) {
        try {
            // Запрос обновления данных кортежа
            final String SQL_UPDATE = "UPDATE Поставщик SET Поставщик = ? WHERE Тип = ?";

            // Обращаемся к объекту БД
            DbWork db = DbWork.getInstance();
            // Обращаемся к состоянию БД
            Connection connection = db.getConnection();
            // Создаем состояния
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE);

            // Устанавливаем значение в запрос
            preparedStatement.setString(2, object.getType());
            preparedStatement.setString(1, object.getSupplier());

            // Выполняем запрос
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new Error(e);
        }
    }

    @Override
    public Supplier delete(Supplier object) {
        try {
            // Запрос на удаление кортежа
            final String SQL_DELETE = "DELETE FROM Поставщик WHERE Тип = ?";

            // Обращаемся к объекту БД
            DbWork db = DbWork.getInstance();
            // Обращаемся к состоянию БД
            Connection connection = db.getConnection();
            // Создаем состояния
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE);

            // Устанавливаем значение в запрос
            preparedStatement.setString(1, object.getType());

            // Выполняем запрос
            preparedStatement.executeUpdate();
            preparedStatement.close();

            return object;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public Supplier getByKey(String[] key) {
        try {
            // Запрос поиска кортежа по id
            final String SQL_SELECT_BY_ID = "SELECT * FROM Поставщик WHERE Тип = ?";

            // Обращаемся к объекту БД
            DbWork db = DbWork.getInstance();
            // Обращаемся к состоянию БД
            Connection connection = db.getConnection();
            // Создаем состояния
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_ID);

            // Устанавливаем значение в запрос
            String type = key[0];
            preparedStatement.setString(1, type);

            // Выполняем запрос
            ResultSet resultSet = preparedStatement.executeQuery();

            // Чтение результатов запроса
            Supplier oSupplier = null;
            if (resultSet.next()) {
                String supplier = resultSet.getString("Поставщик");

                oSupplier = new Supplier(type, supplier);
            }

            // Возвращаем результат
            return oSupplier;
        } catch (SQLException e) {
            throw new Error(e);
        }
    }

    @Override
    public List<Supplier> getAll() {
        try {
            // Запрос поиска всех кортежей
            final String SQL_SELECT = "SELECT * FROM Поставщик";
            
            // Обращаемся к объекту БД
            DbWork db = DbWork.getInstance();
            // Обращаемся к состоянию БД
            Connection connection = db.getConnection();
            // Создаем состояния
            Statement statement = connection.createStatement();
            
            // Выполняем запрос
            ResultSet resultSet = statement.executeQuery(SQL_SELECT);
            
            // Чтение результатов поиска
            ArrayList<Supplier> suppliers = new ArrayList<>();
            while (resultSet.next()) {
                String type = resultSet.getString("Тип");
                String supplier = resultSet.getString("Поставщик");
                
                suppliers.add(new Supplier(type, supplier));
            }
            
            // Возвращаем результат
            return suppliers;
        } catch (SQLException e) {
            throw new Error(e);
        }
    }
    
}
