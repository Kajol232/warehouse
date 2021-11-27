package com.muhammad.warehouse.service;

import com.muhammad.warehouse.exception.stock.StockExistException;
import com.muhammad.warehouse.exception.stock.StockNotFoundException;
import com.muhammad.warehouse.model.DTO.StockDTO;
import com.muhammad.warehouse.model.Operation;
import com.muhammad.warehouse.model.Stock;

import java.util.List;

public interface IStockService {
    Stock addStock(StockDTO stockDTO) throws StockExistException;
    List<Stock> getStocks();
    Stock updateStockQuantity(long id, String operation, int quantity) throws StockNotFoundException;
    List<Stock> getAllByQuantityIsLessThanEqual(int quantity);
    void deleteStock(long id);


}
