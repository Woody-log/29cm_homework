package kr.co._29cm.homework.dto;

import kr.co._29cm.homework.entity.Item;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderItemDto {
    String title;
    int quantity;

    public OrderItemDto(Item item, int quantity) {
        this.title = item.getTitle();
        this.quantity = quantity;
    }
}
