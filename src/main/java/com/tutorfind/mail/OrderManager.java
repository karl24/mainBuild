package com.tutorfind.mail;

import javax.persistence.criteria.Order;

public interface OrderManager {

    void placeOrder(Order order);
}
