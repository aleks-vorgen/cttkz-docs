package ssu.cttkz.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<AppError> dataIntegrityViolationException(DataIntegrityViolationException e) {
        return new ResponseEntity<>(new AppError(HttpStatus.CONFLICT.value(), "Дублювання номеру службової записки"), HttpStatus.CONFLICT);
    }
}
