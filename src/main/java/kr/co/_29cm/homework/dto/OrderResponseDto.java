package kr.co._29cm.homework.dto;

import kr.co._29cm.homework.entity.Order;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class OrderResponseDto {

    private long orderTotalPrice;
    private long shippingPrice;
    private List<OrderItemDto> orderItemDtos;

    public OrderResponseDto(Order order) {
        this.orderTotalPrice = order.getOrderTotalPrice();
        this.shippingPrice = order.getShippingPrice();
        this.orderItemDtos = order.getOrderItems()
                .stream()
                .map(orderItem -> {
                    return new OrderItemDto(orderItem.getItem(), orderItem.getQuantity());
                })
                .collect(Collectors.toList());
    }
}
