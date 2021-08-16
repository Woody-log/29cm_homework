package kr.co._29cm.homework.entity;

import kr.co._29cm.homework.exception.SoldOutException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class ItemTest {

    @Test
    @DisplayName("아이템 생성")
    void createItem() throws Exception {
        //given

        //when
        Item item = Item.createItem(123L, "아이템1", 1000l, 10);

        //then
        assertThat(item.getId()).isEqualTo(123L);
        assertThat(item.getTitle()).isEqualTo("아이템1");
        assertThat(item.getPrice()).isEqualTo(1000l);
        assertThat(item.getStockQuantity()).isEqualTo(10);
    }

    @Test
    @DisplayName("재고수량 감소")
    void removeStock() throws Exception {
        //given
        Item item = Item.createItem(123L, "아이템1", 1000l, 10);

        //when
        item.removeStock(10);

        //then
        assertThat(item.getStockQuantity()).isEqualTo(0);
    }

    @Test
    @DisplayName("재고수량 초과감소")
    void overRemoveStock() throws Exception {
        //given
        Item item = Item.createItem(123L, "아이템1", 1000l, 10);

        //when
        SoldOutException soldOutException = assertThrows(SoldOutException.class, () -> item.removeStock(100));

        //then
        assertThat(soldOutException.getMessage()).isEqualTo("재고 부족, 상품번호 : " + item.getId() +
                ", 주문수량: " + 100 + ", 재고수량: " + item.getStockQuantity());
    }
}