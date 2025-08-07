package com.paterni.product.services.exceptions;

public class DatabaseException extends RuntimeException {

    public DatabaseException(String messaage){
        super(messaage);
    }
}
