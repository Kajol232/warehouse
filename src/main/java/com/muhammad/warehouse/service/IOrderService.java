package com.muhammad.warehouse.service;

import com.muhammad.warehouse.exception.stock.StockNotFoundException;
import com.muhammad.warehouse.model.DTO.OrderDto;
import com.muhammad.warehouse.model.Order;

import java.util.Date;
import java.util.List;

public interface IOrderService {
    Order logOrder(OrderDto orderDto) throws StockNotFoundException;
    Order getById(long id);
    List<Order> getOrders();
    List<Order> generateReportWithDate(String date);
    List<Order> generateReportWithDate(String startDate, String endDate);

}
