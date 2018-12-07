package ru.andrey.dao;

import java.util.List;

public interface GenericDAO<T> {
    // Добавление кортежа в таблицу
    T create(T object);
    // Обновление данных кортежа таблицы
    void update(T object);
    // Удаление кортежа из таблицы
    T delete(T object);
    // Поиск кортежа по id
    T getByKey(String[] key);
    // Поиск всех кортежей из таблицы
    List<T> getAll();
}
