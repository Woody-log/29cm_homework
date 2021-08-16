package kr.co._29cm.homework.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@Table(name = "ORDERS")
public class Order {
    @Id @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @OneToMany(mappedBy = "order", cascade = ALL)
    private List<OrderItem> orderItems = new ArrayList<>();

    private long orderTotalPrice;
    private long shippingPrice;
    private LocalDateTime orderDate;

    /**
     * 주문 생성
     * @param orderItems
     * @return
     */
    public static Order createOrder(List<OrderItem>  orderItems) {
        Order order = new Order();
        for (OrderItem orderItem : orderItems) {
            order.orderTotalPrice += orderItem.getTotalPrice();
            order.addOderItem(orderItem);
        }
        order.shippingPrice = (order.orderTotalPrice >= 50000 ? 0 : 2500);
        order.orderDate = LocalDateTime.now();

        return order;
    }

    /**
     * 주문상품 추가
     * @param orderItem
     */
    public void addOderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
        orderItem.setOrder(this);
    }
}
