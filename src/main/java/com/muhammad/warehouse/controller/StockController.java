package com.muhammad.warehouse.controller;

import com.muhammad.warehouse.exception.stock.StockExistException;
import com.muhammad.warehouse.exception.stock.StockNotFoundException;
import com.muhammad.warehouse.model.DTO.StockDTO;
import com.muhammad.warehouse.model.DTO.UserLoginDTO;
import com.muhammad.warehouse.model.Stock;
import com.muhammad.warehouse.model.User;
import com.muhammad.warehouse.repository.StockRepository;
import com.muhammad.warehouse.service.IStockService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/stocks")
public class StockController {
    private final StockRepository stockRepository;
    private final IStockService stockService;

    public StockController(StockRepository stockRepository, IStockService stockService) {
        this.stockRepository = stockRepository;
        this.stockService = stockService;
    }

    @GetMapping(path = "/lists", consumes = "application/json", produces = "application/json")
    public List<Stock> getAllUsers(){
        return (List<Stock>) stockService.getStocks();
    }

    @GetMapping(path = "/getById/{id}", consumes = "application/json", produces = "application/json")
    public Stock getUserById(@PathVariable("id") long id){
        return stockRepository.findById(id);
    }

    @DeleteMapping(path = "/delete/{id}")
    public ResponseEntity deleteStock(@PathVariable("id") long id){
        stockService.deleteStock(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(value = "/add", consumes = "application/json", produces = "application/json")
    ResponseEntity<Stock> userLogin(@RequestBody StockDTO stockDTO) throws StockExistException {
        Stock stock = stockService.addStock(stockDTO);
        return new ResponseEntity<>(stock, HttpStatus.OK);
    }

    @PatchMapping(path = "/restock/{id}", consumes = "application/json", produces = "application/json")
    ResponseEntity<Stock> addToStock(@PathVariable long id, @RequestParam int quantity) throws StockNotFoundException {
        Stock stock = stockService.updateStockQuantity(id, "ADD", quantity);
        return new ResponseEntity<>(stock, HttpStatus.OK);
    }
}
