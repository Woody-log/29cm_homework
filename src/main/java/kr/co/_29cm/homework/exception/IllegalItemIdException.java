package kr.co._29cm.homework.exception;

import lombok.Getter;

@Getter
public class IllegalItemIdException extends RuntimeException {
    private OrderErrorCode orderErrorCode;
    private String detailMessage;

    public IllegalItemIdException(OrderErrorCode orderErrorCode) {
        this.orderErrorCode = orderErrorCode;
        this.detailMessage = orderErrorCode.getDescription();
    }

    public IllegalItemIdException(OrderErrorCode orderErrorCode, String message) {
        super(message);
        this.detailMessage = message;
        this.orderErrorCode = orderErrorCode;
    }
}
