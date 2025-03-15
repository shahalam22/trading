package com.example.service;

import com.example.domain.OrderType;
import com.example.modal.Coin;
import com.example.modal.Order;
import com.example.modal.OrderItem;
import com.example.modal.User;

import java.util.List;

public interface OrderService {
    Order createOrder(User user, OrderItem orderItem, OrderType orderType);

    Order getOrderById(Long orderId) throws Exception;

    List<Order> getAllOrdersOFUser(Long userId, OrderType orderType, String assetSymbol);

    Order processOrder(Coin coin, double quantity, OrderType orderType, User user) throws Exception;
}
