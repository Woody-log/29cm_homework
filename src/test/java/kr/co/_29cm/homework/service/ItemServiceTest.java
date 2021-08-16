package kr.co._29cm.homework.service;

import kr.co._29cm.homework.entity.Item;
import kr.co._29cm.homework.exception.IllegalItemIdException;
import kr.co._29cm.homework.exception.OrderErrorCode;
import kr.co._29cm.homework.repository.ItemRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

    @Mock
    private ItemRepository itemRepository;

    @Test
    @DisplayName("상품 찾기")
    void findItemByIdTest() throws Exception {
        //given
        when(itemRepository.findById(1234L)).thenReturn(getStubItem());

        //when
        Optional<Item> optionalItem = itemRepository.findById(1234L);

        //then
        Item item = optionalItem.get();
        assertThat(item.getId()).isEqualTo(1234L);
        assertThat(item.getTitle()).isEqualTo("test");
        assertThat(item.getPrice()).isEqualTo(1000l);
        assertThat(item.getStockQuantity()).isEqualTo(20);
    }

    @Test
    @DisplayName("상품 찾기 - 상품 id null")
    void findItemByIdTest_id_null() throws Exception {
        //given
        Long id = null;
        when(itemRepository.findById(id)).thenThrow(new IllegalArgumentException());

        //when
        IllegalArgumentException illegalArgumentException =
                assertThrows(IllegalArgumentException.class, () -> itemRepository.findById(id));

        //then
        assertThat(illegalArgumentException.getMessage()).isEqualTo("");
    }

    @Test
    @DisplayName("상품 찾기 - 존재하지 않는 상품 id")
    void findItemByIdTest_not_exist_id() throws Exception {
        //given
        Long id = -999999L;
        Optional<Item> optionalItem = Optional.empty();
        when(itemRepository.findById(id)).thenReturn(optionalItem);

        //when
        IllegalItemIdException illegalItemIdException = assertThrows(IllegalItemIdException.class
                , () -> itemRepository.findById(id).orElseThrow(() -> new IllegalItemIdException(OrderErrorCode.INVALID_ITEM)));

        //then
        assertThat(illegalItemIdException.getOrderErrorCode().getCode()).isEqualTo(OrderErrorCode.INVALID_ITEM.getCode());
        assertThat(illegalItemIdException.getDetailMessage()).isEqualTo(OrderErrorCode.INVALID_ITEM.getDescription());
    }

    private Optional<Item> getStubItem() {
        Item item = Item.createItem(1234L, "test", 1000l, 20);
        return Optional.ofNullable(item);
    }
}