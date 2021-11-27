package com.muhammad.warehouse.exception.domain;

public class InvalidLoginException extends Exception{
    public InvalidLoginException(String msg){
        super(msg);
    }
}
