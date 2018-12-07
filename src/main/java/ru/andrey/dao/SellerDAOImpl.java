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
import ru.andrey.domain.Seller;

/**
 *
 * @author AEGrishin
 */
public class SellerDAOImpl implements GenericDAO<Seller> {

    @Override
    public Seller create(Seller seller) {
        try {
            // Запрос вставки нового кортежа
            final String SQL_INSERT = "INSERT INTO Продавцы(Имя, Город, Название, Цена, Количество) VALUES (?, ?, ?, ?, ?)";
            
            // Обращаемся к объекту БД
            DbWork db = DbWork.getInstance();
            // Обращаемся к состоянию БД
            Connection connection = db.getConnection();
            // Создаем состояния
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_INSERT);
            Statement statement = connection.createStatement();
            
            // Устанавливаем значения в запрос
            preparedStatement.setString(1, seller.getName());
            preparedStatement.setString(2, seller.getArea());
            preparedStatement.setString(3, seller.getNameProduct());
            preparedStatement.setInt(4, seller.getPrice());
            preparedStatement.setInt(5, seller.getCount());
            
            // Выполняем запрос
            preparedStatement.executeUpdate();
            preparedStatement.close();
            
            return seller;
        } catch (SQLException e) {
            throw new Error(e);
        }
    }

    @Override
    public void update(Seller object) {
        try {
            // Запрос обновления данных кортежа
            final String SQL_UPDATE = "UPDATE Продавцы SET Цена = ?, Количество = ? WHERE Имя = ? AND Город = ? AND Название = ?";

            // Обращаемся к объекту БД
            DbWork db = DbWork.getInstance();
            // Обращаемся к состоянию БД
            Connection connection = db.getConnection();
            // Создаем состояния
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE);

            // Устанавливаем значение в запрос
            preparedStatement.setString(3, object.getName());
            preparedStatement.setString(4, object.getArea());
            preparedStatement.setString(5, object.getNameProduct());
            preparedStatement.setInt(1, object.getPrice());
            preparedStatement.setInt(2, object.getCount());

            // Выполняем запрос
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new Error(e);
        }
    }

    @Override
    public Seller delete(Seller object) {
        try {
            // Запрос на удаление кортежа
            final String SQL_DELETE = "DELETE FROM Продавцы WHERE Имя = ? AND Город = ? AND Название = ?";

            // Обращаемся к объекту БД
            DbWork db = DbWork.getInstance();
            // Обращаемся к состоянию БД
            Connection connection = db.getConnection();
            // Создаем состояния
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE);

            // Устанавливаем значение в запрос
            preparedStatement.setString(1, object.getName());
            preparedStatement.setString(2, object.getArea());
            preparedStatement.setString(3, object.getNameProduct());

            // Выполняем запрос
            preparedStatement.executeUpdate();
            preparedStatement.close();

            return object;
        } catch (SQLException e) {
            return null;
        }
    }

    @Override
    public Seller getByKey(String[] key) {
        try {
            // Запрос поиска кортежа по id
            final String SQL_SELECT_BY_ID = "SELECT * FROM Продавцы WHERE Имя = ? AND Город = ? AND Название = ?";

            // Обращаемся к объекту БД
            DbWork db = DbWork.getInstance();
            // Обращаемся к состоянию БД
            Connection connection = db.getConnection();
            // Создаем состояния
            PreparedStatement preparedStatement = connection.prepareStatement(SQL_SELECT_BY_ID);

            // Устанавливаем значение в запрос
            String name = key[0];
            String area = key[1];
            String productName = key[2];
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, area);
            preparedStatement.setString(3, productName);

            // Выполняем запрос
            ResultSet resultSet = preparedStatement.executeQuery();

            // Чтение результатов запроса
            Seller seller = null;
            if (resultSet.next()) {
                int price = resultSet.getInt("Цена");
                int count = resultSet.getInt("Количество");

                seller = new Seller(name, area, productName, price, count);
            }

            // Возвращаем результат
            return seller;
        } catch (SQLException e) {
            throw new Error(e);
        }
    }

    @Override
    public List<Seller> getAll() {
        try {
            // Запрос поиска всех кортежей
            final String SQL_SELECT = "SELECT * FROM Продавцы";
            
            // Обращаемся к объекту БД
            DbWork db = DbWork.getInstance();
            // Обращаемся к состоянию БД
            Connection connection = db.getConnection();
            // Создаем состояния
            Statement statement = connection.createStatement();
            
            // Выполняем запрос
            ResultSet resultSet = statement.executeQuery(SQL_SELECT);
            
            // Чтение результатов поиска
            ArrayList<Seller> sellers = new ArrayList<>();
            while (resultSet.next()) {
                String name = resultSet.getString("Имя");
                String area = resultSet.getString("Город");
                String productName = resultSet.getString("Название");
                int price = resultSet.getInt("Цена");
                int count = resultSet.getInt("Количество");
                Seller seller = new Seller(name, area, productName, price, count);
                
                sellers.add(seller);
            }

            statement.close();
            resultSet.close();
            
            // Возвращаем результат
            return sellers;
        } catch (SQLException e) {
            throw new Error(e);
        }
    }
    
}
