package kr.co._29cm.homework.service;

import kr.co._29cm.homework.dto.ItemQuantityDto;
import kr.co._29cm.homework.dto.OrderRequestDto;
import kr.co._29cm.homework.entity.Item;
import kr.co._29cm.homework.entity.Order;
import kr.co._29cm.homework.repository.ItemRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@Rollback
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ItemRepository itemRepository;

    private int THREAD_CNT = 100;
    private ExecutorService executorService = Executors.newFixedThreadPool(THREAD_CNT);
    private CountDownLatch latch = new CountDownLatch(THREAD_CNT);

    @Test
    @DisplayName("주문하기")
    void order() throws Exception {
        //given
        List<ItemQuantityDto> itemQuantityDtos = Arrays.asList(new ItemQuantityDto(768848L, 10),
                new ItemQuantityDto(748943L, 10),
                new ItemQuantityDto(779989L, 10));
        OrderRequestDto orderRequestDto = new OrderRequestDto(itemQuantityDtos);

        //when
        Order order = orderService.order(orderRequestDto);

        //then
        assertThat(order.getOrderItems().size()).isEqualTo(3);
        assertThat(order.getOrderItems().get(0).getItem().getStockQuantity()).isEqualTo(99990);
        assertThat(order.getOrderItems().get(1).getItem().getStockQuantity()).isEqualTo(99990);
        assertThat(order.getOrderItems().get(2).getItem().getStockQuantity()).isEqualTo(99990);
    }

    @Test
    @DisplayName("멀티스레드 주문하기")
    void order_multithreading() throws Exception {
        //given
        List<ItemQuantityDto> itemQuantityDtos = Arrays.asList(new ItemQuantityDto(748943L, 1));
        OrderRequestDto orderRequestDto = new OrderRequestDto(itemQuantityDtos);

        //when
        for(int i = 0; i < 100; i++) {
            executorService.execute(() -> {
                orderService.order(orderRequestDto);
                latch.countDown();
            });
        }

        //then
        latch.await();
        Optional<Item> optionalItem = itemRepository.findById(748943L);

        Item item = optionalItem.get();
        assertThat(item.getStockQuantity()).isEqualTo(99900);
    }

    @Test
    @DisplayName("주문하기 - 배송비 발생")
    void order_shippingPrice_occur() throws Exception {
        //given
        List<ItemQuantityDto> itemQuantityDtos = Arrays.asList(new ItemQuantityDto(748943L, 1));
        OrderRequestDto orderRequestDto = new OrderRequestDto(itemQuantityDtos);

        //when
        Order order = orderService.order(orderRequestDto);

        //then
        assertThat(order.getShippingPrice()).isEqualTo(2500);
    }

    @Test
    @DisplayName("주문하기 - 배송비 미발생")
    void order_shippingPrice_not_occur() throws Exception {
        //given
        List<ItemQuantityDto> itemQuantityDtos = Arrays.asList(new ItemQuantityDto(748943L, 10));
        OrderRequestDto orderRequestDto = new OrderRequestDto(itemQuantityDtos);

        //when
        Order order = orderService.order(orderRequestDto);

        //then
        assertThat(order.getShippingPrice()).isEqualTo(0);
    }
}