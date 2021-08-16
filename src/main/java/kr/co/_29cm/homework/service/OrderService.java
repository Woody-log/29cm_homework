package kr.co._29cm.homework.service;

import kr.co._29cm.homework.dto.ItemQuantityDto;
import kr.co._29cm.homework.dto.OrderRequestDto;
import kr.co._29cm.homework.entity.Item;
import kr.co._29cm.homework.entity.Order;
import kr.co._29cm.homework.entity.OrderItem;
import kr.co._29cm.homework.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final ItemService itemService;

    @Transactional
    public Order order(OrderRequestDto orderRequestDto) {
        List<OrderItem> orderItems = new LinkedList<>();
        for (ItemQuantityDto itemQuantityDto : orderRequestDto.getItemQuantityDtos()) {
            Item item = itemService.findById(itemQuantityDto.getId());
            int quantity = itemQuantityDto.getQuantity();

            OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), quantity);
            orderItems.add(orderItem);
        }

        Order order = Order.createOrder(orderItems);
        orderRepository.save(order);

        return order;
    }
}
