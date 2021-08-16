package kr.co._29cm.homework.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum OrderErrorCode {
    SOLD_OUT(-1000, "재고부족"),
    INVALID_ITEM(-2000, "등록되지 않은 상품 ID"),
    INVALID_QUANTITY(-3000, "주문수량은 1개 이상"),
    BAD_REQUEST(400, "잘못된 요청"),
    ERROR(-9999, "정의되지 않는 에러발생");

    private int code;
    private String description;


    public static OrderErrorCode getOrderErrorCode(int errorCode) {
        return Arrays.stream(values())
                .filter(e -> e.findByEnum(errorCode))
                .findAny()
                .orElse(ERROR);
    }

    public Boolean findByEnum(int errorCode) {
        return this.code == errorCode;
    }
}
