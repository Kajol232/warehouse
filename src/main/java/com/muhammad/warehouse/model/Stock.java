package com.muhammad.warehouse.model;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "stocks")
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(unique = true)
    private String name;
    private double price;
    private int quantity;
    @CreationTimestamp
    private LocalDateTime regDate;
    @UpdateTimestamp
    public LocalDateTime dateLastModified;

    public Stock() {
    }

    public Stock(long id, String name, double price, int quantity, LocalDateTime regDate, LocalDateTime dateLastModified) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.regDate = regDate;
        this.dateLastModified = dateLastModified;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getRegDate() {
        return regDate;
    }

    public LocalDateTime getDateLastModified() {
        return dateLastModified;
    }
}
