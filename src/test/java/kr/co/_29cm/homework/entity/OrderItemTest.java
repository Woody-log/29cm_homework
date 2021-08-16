package kr.co._29cm.homework.entity;

import kr.co._29cm.homework.exception.SoldOutException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class OrderItemTest {

    @Test
    @DisplayName("주문상품 생성")
    void createOrderItem() {
        // given
        int orderQuantity = 5;
        Item item = Item.createItem(123L, "아이템1", 1000l, 10);

        // when
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), orderQuantity);

        // then
        assertThat(orderItem.getQuantity()).isEqualTo(orderQuantity);
        assertThat(orderItem.getOrderPrice()).isEqualTo(item.getPrice());
        assertThat(orderItem.getItem()).isEqualTo(item);
    }

    @Test
    @DisplayName("주문상품 생성 - 주문수량 음수")
    void createOrderItemIllegalQuantity() {
        // given
        int orderQuantity = -1;
        Item item = Item.createItem(123L, "아이템1", 1000l, 10);

        // when
        IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> OrderItem.createOrderItem(item, item.getPrice(), orderQuantity));

        // then
        assertThat(illegalArgumentException.getMessage()).isEqualTo("주문 수량은 최소 1개 이상이여야합니다.");
    }
    
    @Test
    @DisplayName("주문상품 전체 가격 조회")
    void getTotalPrice() throws Exception {
        // given
        int orderQuantity = 5;
        Item item = Item.createItem(123L, "아이템1", 1000l, 10);

        // when
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), orderQuantity);
        
        //then
        assertThat(orderItem.getTotalPrice()).isEqualTo(orderQuantity * item.getPrice());
    }
}