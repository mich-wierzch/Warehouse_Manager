package com.michw.Warehouse_Manager.exceptions;

public class DuplicateProductException extends RuntimeException{
    public DuplicateProductException(String message){
        super(message);
    }
}
