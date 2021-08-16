package kr.co._29cm.homework.api;

import kr.co._29cm.homework.dto.ItemDto;
import kr.co._29cm.homework.dto.ItemResponseDto;
import kr.co._29cm.homework.entity.Item;
import kr.co._29cm.homework.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class ItemApiController {

    private final ItemRepository itemRepository;

    /**
     * 상품목록 조회
     * @return
     */
    @GetMapping("/api/v1/items")
    public ResponseEntity<ItemResponseDto> findAllItems() {
        List<Item> items = itemRepository.findAll();
        List<ItemDto> itemDtos = items.stream()
                .map(item -> new ItemDto(item))
                .collect(Collectors.toList());
        ItemResponseDto itemResponseDto = new ItemResponseDto(itemDtos);

        return ResponseEntity.ok().body(itemResponseDto);
    }
}
