package project.libraryManagement.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> badRequest(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> upstream(RuntimeException e) {
        // 외부 API 실패 등: 상황에 따라 502/503
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body("외부 연동 실패: " + e.getMessage());
    }
}
