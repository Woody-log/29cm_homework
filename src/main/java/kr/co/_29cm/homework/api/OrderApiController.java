package kr.co._29cm.homework.api;

import kr.co._29cm.homework.dto.OrderRequestDto;
import kr.co._29cm.homework.dto.OrderResponseDto;
import kr.co._29cm.homework.entity.Order;
import kr.co._29cm.homework.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderService orderService;

    /**
     * 주문하기
     * @param
     * @return
     */
    @PostMapping("/api/v1/order")
    public ResponseEntity<OrderResponseDto> order(@RequestBody OrderRequestDto orderRequestDto) {
        if(orderRequestDto.getItemQuantityDtos().isEmpty()) {
            throw new IllegalArgumentException("상품 ID와 수량을 입력하세요.");
        }

        Order order = orderService.order(orderRequestDto);
        OrderResponseDto orderResponseDto = new OrderResponseDto(order);
        return ResponseEntity.ok().body(orderResponseDto);
    }
}
