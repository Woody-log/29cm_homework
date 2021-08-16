package kr.co._29cm.homework.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class OrderTest {

    @Test
    @DisplayName("주문 생성")
    void createOrder() {
        //given
        int orderQuantity = 3;
        Item item = Item.createItem(1L, "아이템1", 10000, 10);
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), orderQuantity);

        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem);

        //when
        Order order = Order.createOrder(orderItems);

        //then
        assertThat(order).isEqualTo(orderItem.getOrder());
        assertThat(order.getOrderItems()
                .size()).isEqualTo(orderItems.size());
        assertThat(order.getOrderItems()).contains(orderItem);
        assertThat(order.getShippingPrice()).isEqualTo(2500l);
        assertThat(order.getOrderTotalPrice()).isEqualTo(orderItem.getTotalPrice());
    }

    @Test
    @DisplayName("배송비 미발생")
    void shippingPrice_not_occur() throws Exception {
        //given
        int orderQuantity = 5;
        Item item = Item.createItem(1L, "아이템1", 10000, 10);
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), orderQuantity);

        //when
        List<OrderItem> orderItems = new ArrayList<>();
        orderItems.add(orderItem);

        //when
        Order order = Order.createOrder(orderItems);

        //then4
        assertThat(order.getShippingPrice()).isEqualTo(0);
    }

    public List<Item> getStubItemList()  {
        List<Item> itemList = Arrays.asList(Item.createItem(1L, "아이템1", 10000, 100),
                Item.createItem(2L, "아이템2", 10000, 100),
                Item.createItem(3L, "아이템3", 10000, 100),
                Item.createItem(4L, "아이템4", 10000, 100),
                Item.createItem(5L, "아이템5", 10000, 100),
                Item.createItem(6L, "아이템6", 10000, 100));

        return itemList;
    }
}