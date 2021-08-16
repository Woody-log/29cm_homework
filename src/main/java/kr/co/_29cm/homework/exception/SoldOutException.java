package kr.co._29cm.homework.exception;

import lombok.Getter;

@Getter
public class SoldOutException extends RuntimeException {
    private OrderErrorCode orderErrorCode;
    private String detailMessage;

    public SoldOutException(OrderErrorCode orderErrorCode) {
        this.orderErrorCode = orderErrorCode;
        this.detailMessage = orderErrorCode.getDescription();
    }

    public SoldOutException(OrderErrorCode orderErrorCode, String message) {
        super(message);
        this.detailMessage = message;
        this.orderErrorCode = orderErrorCode;
    }
}
