package com.broadfactor.handler;

import com.broadfactor.response.Response;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Response<ErrorResponse>> methodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {
        Response<ErrorResponse> response = new Response<>();
        ex.getBindingResult().getAllErrors().forEach(error -> response.setErrors(((FieldError) error).getField(), error.getDefaultMessage()));

        ErrorResponse error = ErrorResponse
                .builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .path(request.getRequestURI())
                .message("Field Validation failed")
                .detail("Verifique os campos")
                .build();

        response.setData(error);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public ResponseEntity<Response<ErrorResponse>> dataIntegrityViolationException(DataIntegrityViolationException ex, HttpServletRequest request) {
        Response<ErrorResponse> response = new Response<>();

        ErrorResponse error = ErrorResponse
                .builder()
                .status(HttpStatus.BAD_REQUEST.value())
                .path(request.getRequestURI())
                .message("Dados já cadastrado em nossa base de dados!")
                .detail("Verifique os campos e tente novamente!")
                .build();

        response.setData(error);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
}
