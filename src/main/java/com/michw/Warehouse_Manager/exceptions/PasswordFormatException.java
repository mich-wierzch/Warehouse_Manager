package com.michw.Warehouse_Manager.exceptions;

public class PasswordFormatException extends RuntimeException{
    public PasswordFormatException(String message){
        super(message);
    }
}
