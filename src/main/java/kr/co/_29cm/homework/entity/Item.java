package kr.co._29cm.homework.entity;

import kr.co._29cm.homework.exception.OrderErrorCode;
import kr.co._29cm.homework.exception.SoldOutException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Item extends BaseTimeEntity {
    @Id
    @Column(name = "item_id")
    private Long id;
    private String title;
    private long price;
    private int stockQuantity;

    /**
     * 상품 생성
     * @param id
     * @param title
     * @param price
     * @param stockQuantity
     * @return
     */
    public static Item createItem(Long id, String title, long price, int stockQuantity) {
        Item item = new Item();
        item.id = id;
        item.title = title;
        item.price = price;
        item.stockQuantity = stockQuantity;

        return item;
    }

    /**
     * 재고수량 감소
     * @param quantity
     * @throws SoldOutException
     */
    public void removeStock(int quantity) throws SoldOutException {
        int restStock = this.stockQuantity - quantity;
        if(restStock < 0) {
            throw new SoldOutException(OrderErrorCode.SOLD_OUT, getSoldOutErrorMessage(this, quantity));
        }
        this.stockQuantity = restStock;
    }

    /**
     *
     * @param item
     * @param quantity
     * @return
     */
    private String getSoldOutErrorMessage(Item item, int quantity) {
        return "재고 부족, 상품번호 : " + item.getId() + ", 주문수량: " + quantity + ", 재고수량: " + item.getStockQuantity();
    }
}
