package kr.co._29cm.homework.service;

import kr.co._29cm.homework.entity.Item;
import kr.co._29cm.homework.exception.IllegalItemIdException;
import kr.co._29cm.homework.exception.OrderErrorCode;
import kr.co._29cm.homework.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {

    private final ItemRepository itemRepository;

    public Item findById(Long id) {
        Optional<Item> optionalItem = itemRepository.findById(id);
        return optionalItem.orElseThrow(() -> new IllegalItemIdException(OrderErrorCode.INVALID_ITEM));
    }
}
