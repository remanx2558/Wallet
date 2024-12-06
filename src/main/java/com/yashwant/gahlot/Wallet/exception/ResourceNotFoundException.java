package com.yashwant.gahlot.Wallet.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.logging.Level;
import java.util.logging.Logger;
//The @ResponseStatus annotation ensures that the appropriate HTTP response status code (404)
// is returned when this exception is thrown, indicating to the client that the requested resource was not found.
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public ResourceNotFoundException(String message){
        super(message);
        Logger logger = Logger.getLogger(this.getClass().getName());
        logger.log(Level.INFO, message.toString());

    }


}