package com.muhammad.warehouse.controller;

import com.muhammad.warehouse.exception.stock.StockNotFoundException;
import com.muhammad.warehouse.model.DTO.OrderDto;
import com.muhammad.warehouse.model.Order;
import com.muhammad.warehouse.repository.OrderRepository;
import com.muhammad.warehouse.service.IOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/orders")
public class OrderController {
    private final IOrderService orderService;
    private final OrderRepository orderRepository;

    public OrderController(IOrderService orderService, OrderRepository orderRepository) {
        this.orderService = orderService;
        this.orderRepository = orderRepository;
    }

    @GetMapping(path = "/lists", consumes = "application/json", produces = "application/json")
    public List<Order> getAllOrders(){
        return (List<Order>) orderService.getOrders();
    }

    @GetMapping(path = "/getById/{id}", consumes = "application/json", produces = "application/json")
    public Order getOrderById(@PathVariable("id") long id){
        return orderRepository.findById(id).get();
    }

    @PostMapping(path = "/log", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Order> createOrder(@RequestBody OrderDto orderDto) throws StockNotFoundException {
        Order order = orderService.logOrder(orderDto);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }
    @GetMapping(path = "/reportFromDate/{date}", consumes = "application/json", produces = "application/json")
    public List<Order> getAllOrdersFromDate(@PathVariable String date){
        return (List<Order>) orderService.generateReportWithDate(date);

    }

}
