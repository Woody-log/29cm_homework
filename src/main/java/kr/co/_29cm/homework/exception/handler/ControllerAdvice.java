package kr.co._29cm.homework.exception.handler;

import kr.co._29cm.homework.dto.ErrorResponseDto;
import kr.co._29cm.homework.exception.IllegalItemIdException;
import kr.co._29cm.homework.exception.SoldOutException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@RequiredArgsConstructor
public class ControllerAdvice {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDto> illegalArgumentExceptionHandler(IllegalArgumentException e) {
        ErrorResponseDto errorDto = new ErrorResponseDto(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        return ResponseEntity.badRequest().body(errorDto);
    }

    @ExceptionHandler(IllegalItemIdException.class)
    public ResponseEntity<ErrorResponseDto> illegalItemIdExceptionHandler(IllegalItemIdException e) {
        ErrorResponseDto errorDto = new ErrorResponseDto(e.getOrderErrorCode().getCode(), e.getDetailMessage());
        return ResponseEntity.badRequest().body(errorDto);
    }

    @ExceptionHandler(SoldOutException.class)
    public ResponseEntity<ErrorResponseDto> soldOutExceptionHandler(SoldOutException e) {
        ErrorResponseDto errorDto = new ErrorResponseDto(e.getOrderErrorCode().getCode(), e.getDetailMessage());
        return ResponseEntity.internalServerError().body(errorDto);
    }
}
