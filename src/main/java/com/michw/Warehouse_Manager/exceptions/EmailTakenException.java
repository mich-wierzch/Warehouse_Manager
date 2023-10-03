package com.michw.Warehouse_Manager.exceptions;

public class EmailTakenException extends RuntimeException{
    public EmailTakenException(String message){
        super(message);
    }
}
