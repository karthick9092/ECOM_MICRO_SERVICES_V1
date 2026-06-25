package com.example.user.exception;

import com.example.user.dto.ErrorResponseDto;
import com.example.user.dto.UserDto;
import org.jspecify.annotations.Nullable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/***
 *  GlobalExceptionHandler is a class that handles exceptions globally across the application. It is annotated with @RestControllerAdvice, which allows it to handle exceptions thrown by any controller in the application. This class can be used to define custom exception handling logic, such as returning specific HTTP status codes or error messages when certain exceptions are thrown. By centralizing exception handling in this way, we can ensure consistent error responses and improve the maintainability of the codebase.`
 */

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected @Nullable ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
                .status("error")
                .errorCode(HttpStatus.BAD_REQUEST.value())
                .errorMessage(ex.getBindingResult().getFieldError().getDefaultMessage())
                .apiPath(request.getContextPath())
                .time(java.time.LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).body(errorResponseDto);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGlobalException(Exception ex, WebRequest webRequest) {
        ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
                .status("error")
                .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .errorMessage(ex.getMessage())
                .apiPath(webRequest.getContextPath())
                .time(java.time.LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).body(errorResponseDto);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleUserNotFoundException(UserNotFoundException ex, WebRequest webRequest) {
        ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
                .status("error")
                .errorCode(HttpStatus.NOT_FOUND.value())
                .errorMessage(ex.getMessage())
                .apiPath(webRequest.getContextPath())
                .time(java.time.LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(errorResponseDto);
    }

    @ExceptionHandler(EmailAlreadyAvailableException.class)
    public ResponseEntity<ErrorResponseDto> handleEmailAlreadyAvailableException(EmailAlreadyAvailableException ex, WebRequest webRequest) {
        ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
                .status("error")
                .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .errorMessage(ex.getMessage())
                .apiPath(webRequest.getContextPath())
                .time(java.time.LocalDateTime.now())
                .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(errorResponseDto);
    }
}
