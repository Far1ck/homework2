package org.example.repository;

import org.example.model.Order;

// Абстракция на случай, если появятся другие способы хранения заказов, например в БД
public interface OrderRepository {
    void save(Order order);
}
