package com.michw.Warehouse_Manager.exceptions;

public class EmailFormatException extends RuntimeException{
    public EmailFormatException(String message){
        super(message);
    }
}
