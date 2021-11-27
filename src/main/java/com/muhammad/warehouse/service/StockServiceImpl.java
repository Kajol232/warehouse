package com.muhammad.warehouse.service;

import com.muhammad.warehouse.exception.stock.StockExistException;
import com.muhammad.warehouse.exception.stock.StockNotFoundException;
import com.muhammad.warehouse.model.DTO.StockDTO;
import com.muhammad.warehouse.model.Operation;
import com.muhammad.warehouse.model.Stock;
import com.muhammad.warehouse.repository.StockRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockServiceImpl implements IStockService{
    private StockRepository stockRepository;

    public StockServiceImpl(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Override
    public Stock addStock(StockDTO stockDTO) throws StockExistException {
        if(stockRepository.existsByName(stockDTO.getName())){
            throw new StockExistException("Stock By name : " + stockDTO.getName() + "Already Exist");
        }
        Stock stock = new Stock();
        stock.setName(stockDTO.getName());
        stock.setPrice(stockDTO.getPrice());
        stock.setQuantity(stockDTO.getQuantity());

        stockRepository.save(stock);
        return stock;
    }

    @Override
    public List<Stock> getStocks() {
        return (List<Stock>) stockRepository.findAll();
    }

    @Override
    public Stock updateStockQuantity(long id, String operation, int quantity) throws StockNotFoundException {
        Stock stock = stockRepository.findById(id);
        int newQuantity = 0;
        if(stock == null){
            throw new StockNotFoundException("Stock does not exist");
        }
        if(operation.equalsIgnoreCase(Operation.ADD.name())){
            newQuantity = stock.getQuantity() + quantity;


        }else{
            newQuantity = stock.getQuantity() - quantity;
        }
        stock.setQuantity(newQuantity);
        stockRepository.save(stock);

        return stock;
    }

    @Override
    public List<Stock> getAllByQuantityIsLessThanEqual(int quantity) {
        return stockRepository.getAllByQuantityIsLessThanEqual(quantity);
    }

    @Override
    public void deleteStock(long id) {
        stockRepository.deleteById(id);
    }
}
