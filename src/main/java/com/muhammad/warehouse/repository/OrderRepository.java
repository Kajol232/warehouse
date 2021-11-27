package com.muhammad.warehouse.repository;

import com.muhammad.warehouse.model.Order;
import com.muhammad.warehouse.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Date;
import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Long> {
    List<Order> findOrdersBySoldBy(User u);
    List<Order> getAllByRegDateAfter(Date date);
    List<Order> getAllByRegDateBetween(Date startDate, Date endDate);
}
