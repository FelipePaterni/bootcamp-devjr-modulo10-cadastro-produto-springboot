package com.paterni.product.resources.exceptions;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.paterni.product.services.exceptions.DatabaseException;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;


//Tratar exeções
// Anotação que indica que esta classe irá tratar exceções de forma global nos controllers
@ControllerAdvice
public class ResourceExeceptionHandler {
    
    // Método que trata exceções do tipo MethodArgumentNotValidException (erros de validação)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrors> validationException(MethodArgumentNotValidException exception, HttpServletRequest request){
        ValidationErrors error = new ValidationErrors();

        HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;


        error.setError("Validation error");
           error.setMessage(exception.getMessage());
           error.setPath(request.getRequestURI());
           error.setStatus(status.value());
           error.setTimeStamp(Instant.now());
           exception.getBindingResult()
                    .getFieldErrors()
                    .forEach(e -> error.addError(e.getDefaultMessage()));

        // Retorna uma resposta HTTP 400 (Bad Request) com a mensagem erro
        return ResponseEntity.status(status).body(error);
    }



     @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<StandardError> databaseException(DatabaseException exception, HttpServletRequest request){
        
        StandardError error = new StandardError();

        HttpStatus status = HttpStatus.BAD_REQUEST;


        error.setError("Database exception");
           error.setMessage(exception.getMessage());
           error.setPath(request.getRequestURI());
           error.setStatus(status.value());
           error.setTimeStamp(Instant.now());
          
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<StandardError> entityNotFoundException(EntityNotFoundException exception, HttpServletRequest request){
        
        StandardError error = new StandardError();

        HttpStatus status = HttpStatus.NOT_FOUND;


        error.setError("Resource not found exception");
           error.setMessage(exception.getMessage());
           error.setPath(request.getRequestURI());
           error.setStatus(status.value());
           error.setTimeStamp(Instant.now());
          
        return ResponseEntity.status(status).body(error);
    }
}
