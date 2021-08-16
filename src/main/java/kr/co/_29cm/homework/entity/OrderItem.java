package kr.co._29cm.homework.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class OrderItem {
    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private long orderPrice;
    private int quantity;

    /**
     * 주문상품 생성
     * @param item
     * @param orderPrice
     * @param quantity
     * @return
     */
    public static OrderItem createOrderItem(Item item, long orderPrice, int quantity) {
        if(quantity <= 0) { throw new IllegalArgumentException("주문 수량은 최소 1개 이상이여야합니다."); }

        OrderItem orderItem = new OrderItem();
        orderItem.item = item;
        orderItem.orderPrice = orderPrice;
        orderItem.quantity = quantity;

        item.removeStock(quantity);

        return orderItem;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    /**
     * 주문상품 전체 가격 조회
     */
    public long getTotalPrice() {
        return this.orderPrice * this.quantity;
    }
}
