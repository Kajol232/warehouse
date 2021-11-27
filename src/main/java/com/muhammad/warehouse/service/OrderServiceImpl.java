package com.muhammad.warehouse.service;

import com.muhammad.warehouse.exception.stock.StockNotFoundException;
import com.muhammad.warehouse.model.DTO.OrderDto;
import com.muhammad.warehouse.model.Order;
import com.muhammad.warehouse.model.Stock;
import com.muhammad.warehouse.model.User;
import com.muhammad.warehouse.repository.OrderRepository;
import com.muhammad.warehouse.repository.StockRepository;
import com.muhammad.warehouse.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements IOrderService{
    private final OrderRepository orderRepository;
    private final IStockService stockService;
    private final UserRepository userRepository;

    public OrderServiceImpl(OrderRepository orderRepository,IStockService stockService, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.stockService = stockService;
        this.userRepository = userRepository;
    }


    @Override
    public Order logOrder(OrderDto orderDto) throws StockNotFoundException {
        Order order = new Order();
        List<Stock> orderedStock = orderDto.getStocks();
        order.setStocks(orderedStock);
        double amountPaid = calculatePaidAmount(orderedStock);
        order.setAmountPaid(amountPaid);
        User cashier = userRepository.findUserById(orderDto.getCashierId());
        order.setSoldBy(cashier);
        if(orderRepository.save(order) != null){
            updateStocks(orderedStock);
        }

        return order;

    }

    private void updateStocks(List<Stock> orderedStock) throws StockNotFoundException {
        for (Stock s:orderedStock) {
            stockService.updateStockQuantity(s.getId(), "DELETE", s.getQuantity());
        }
    }

    private double calculatePaidAmount(List<Stock> orderedStock) {
        double total = 0;
        for (Stock s: orderedStock) {
            double stockAmount = s.getQuantity() * s.getPrice();
            total += stockAmount;

        }
        return total;
    }


    @Override
    public Order getById(long id) {
        return orderRepository.findById(id).get();
    }

    @Override
    public List<Order> getOrders() {
        return (List<Order>) orderRepository.findAll();
    }

    @Override
    public List<Order> generateReportWithDate(String date) {
        List<Order> orders = new ArrayList<>();
        Date d = formatDate(date);
        if(d != null){
            orders = orderRepository.getAllByRegDateAfter(d);
        }
        return orders;
    }

    @Override
    public List<Order> generateReportWithDate(String startDate, String endDate) {
        List<Order> orders = new ArrayList<>();
        Date start = formatDate(startDate);
        Date end = formatDate(endDate);

        if(start != null && end != null){
            orders = orderRepository.getAllByRegDateBetween(start, end);
        }
        return orders;
    }

    private Date formatDate(String date) {
        SimpleDateFormat DateFor = new SimpleDateFormat("dd/MM/yyyy");
        try{
            Date formattedDate = DateFor.parse(date);
            return formattedDate;

        }catch (ParseException  e) {e.printStackTrace();}
        return null;

    }


}
