package kr.co._29cm.homework.dto;

import kr.co._29cm.homework.entity.Item;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ItemDto {
    private Long id;
    private String title;
    private long price;
    private int stockQuantity;

    public ItemDto(Item item) {
        this.id = item.getId();
        this.title = item.getTitle();
        this.price = item.getPrice();
        this.stockQuantity = item.getStockQuantity();
    }
}
