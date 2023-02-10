package com.toyboardproject.Aspect;

import com.toyboardproject.exception.NotAuthorizedException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(IllegalAccessException.class)
    public String handleIllegalAccessException(IllegalAccessException e) {
        return "redirect:/account/login";
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleEntityNotFoundException(EntityNotFoundException e) {
        return  new ResponseEntity<>(e.getMessage(), org.springframework.http.HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgumentException(IllegalArgumentException e) {
        return new ResponseEntity<>(e.getMessage(), org.springframework.http.HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotAuthorizedException.class)
    public String handleNotAuthorizedException(NotAuthorizedException e) { return "redirect:/board/?type=FREE";}

    @ExceptionHandler(org.springframework.security.access.AccessDeniedException.class)
    public String handleAccessDeniedException(AccessDeniedException e) { return "redirect:/board/?type=FREE";}

}
