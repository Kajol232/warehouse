package com.muhammad.warehouse.repository;

import org.springframework.data.repository.CrudRepository;
import com.muhammad.warehouse.model.Stock;

import java.util.Date;
import java.util.List;

public interface StockRepository extends CrudRepository<Stock, Long> {
    Stock findById(long id);
    //List<Stock> getAllByRegDateAfter(Date date);
    List<Stock> getAllByQuantityIsLessThanEqual(int quantity);
    boolean existsByName(String name);

}
