package org.spring.data.rest.exception.handler;

import org.spring.data.rest.exception.EntityAlreadyExistsException;
import org.spring.data.rest.exception.EntityNotFoundException;
import org.spring.data.rest.exception.InvalidUsernamePasswordException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@RestControllerAdvice
public class AppExceptionHandler {
    @ExceptionHandler(value = { EntityNotFoundException.class })
    public ResponseEntity<Object> entityNotFoundException( EntityNotFoundException ex ) {
        ErrorMessage errorMessage = ErrorMessage.builder()
                .message(ex.getMessage())
                .timestamp(new Date())
                .code(404)
                .build();
        return new ResponseEntity<>(errorMessage, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(value = { EntityAlreadyExistsException.class })
    public ResponseEntity<Object> entityAlreadyExistsException( EntityAlreadyExistsException ex ) {
        ErrorMessage errorMessage = ErrorMessage.builder()
                .message(ex.getMessage())
                .timestamp(new Date())
                .code(409)
                .build();
        return new ResponseEntity<>(errorMessage,HttpStatus.CONFLICT);
    }
    @ExceptionHandler(value = { InvalidUsernamePasswordException.class })
    public ResponseEntity<Object> invalidUsernamePasswordException( InvalidUsernamePasswordException ex ) {
        ErrorMessage errorMessage = ErrorMessage.builder()
                .message(ex.getMessage())
                .timestamp(new Date())
                .code(401)
                .build();
        return new ResponseEntity<>(errorMessage,HttpStatus.UNAUTHORIZED);
    }

}
