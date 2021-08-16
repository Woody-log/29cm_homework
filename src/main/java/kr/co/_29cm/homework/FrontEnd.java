package kr.co._29cm.homework;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co._29cm.homework.dto.*;
import kr.co._29cm.homework.exception.OrderErrorCode;
import org.apache.logging.log4j.util.Strings;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class FrontEnd {
    BufferedReader br;
    private RestTemplate restTemplate;

    public FrontEnd() {
        this.restTemplate = new RestTemplate();
        this.br = new BufferedReader(new InputStreamReader(System.in));
    }

    public void start() {
        while (true) {
            String command = getCommand();
            if ("o".equals(command) || "order".equals(command)) {
                printAllItems();
                List<ItemQuantityDto> itemQuantityDtos = getItemQuantityDtos();
                ResponseEntity<OrderResponseDto> orderResponse = order(itemQuantityDtos);
                printOrderResult(orderResponse);
            } else if ("q".equals(command) || "quit".equals(command)) {
                quit();
                break;
            }
        }
    }

    /**
     * 주문결과 노출
     *
     * @param orderResponse
     */
    private void printOrderResult(ResponseEntity<OrderResponseDto> orderResponse) {
        if (orderResponse == null) {
            return;
        }

        ObjectMapper mapper = new ObjectMapper();
        OrderResponseDto orderResponseDto = mapper.convertValue(orderResponse.getBody(), new TypeReference<OrderResponseDto>() {
        });

        System.out.println("주문내역:");
        System.out.println("----------------------------------------");

        for (OrderItemDto orderItemDto : orderResponse.getBody().getOrderItemDtos()) {
            System.out.println(orderItemDto.getTitle() + " - " + orderItemDto.getQuantity() + "개");
        }

        System.out.println("----------------------------------------");
        System.out.println("주문금액 : " + String.format("%,d", orderResponseDto.getOrderTotalPrice()) + "원");

        if (orderResponseDto.getShippingPrice() != 0) {
            System.out.println("배송비 : " + String.format("%,d", orderResponseDto.getShippingPrice()) + "원");
        }

        long payAmount = (orderResponseDto.getOrderTotalPrice() + orderResponseDto.getShippingPrice());
        System.out.println("----------------------------------------");
        System.out.println("지불금액 : " + String.format("%,d", payAmount) + "원");
        System.out.println("----------------------------------------");
    }

    /**
     * 명령 입력받기
     *
     * @return
     */
    private String getCommand() {
        System.out.print("입력(o[order]: 주문, q[quit]: 종료) : ");
        String command = getInput();

        return command;
    }

    /**
     * 주문하기
     */
    private ResponseEntity<OrderResponseDto> order(List<ItemQuantityDto> itemResponseDtos) {
        String url = "http://localhost:8080/api/v1/order";
        OrderRequestDto orderRequestDto = new OrderRequestDto(itemResponseDtos);
        HttpEntity<OrderRequestDto> request = new HttpEntity<>(orderRequestDto);
        ResponseEntity<OrderResponseDto> response = null;

        try {
            response = restTemplate.postForEntity(url, request, OrderResponseDto.class);
        } catch (HttpStatusCodeException e) {
            ErrorResponseDto errorResponseDto = errorRead(e);
            errorHandler(errorResponseDto);
        }
        return response;
    }

    /**
     * 주문 입력받기
     */
    private List<ItemQuantityDto> getItemQuantityDtos() {
        List<ItemQuantityDto> itemQuantityDtos = new LinkedList<>();

        while (true) {
            System.out.print("상품번호 : ");
            String itemId = getInput();

            if (" ".equals(itemId)) {
                break;
            }

            System.out.print("수량 : ");
            String quantity = getInput();

            if (" ".equals(quantity)) {
                break;
            }

            try {
                itemQuantityDtos.add(new ItemQuantityDto(Long.parseLong(itemId), Integer.parseInt(quantity)));
            } catch (Exception e) {
                System.out.println("상품번호와 수량을 정확히 입력해주세요.");
            }
        }
        return itemQuantityDtos;
    }

    /**
     * 값 입력
     *
     * @return
     * @throws IOException
     */
    private String getInput() {
        String input = null;
        try {
            input = br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return input;
    }

    /**
     * 종료
     */
    private void quit() {
        System.out.println("고객님의 주문 감사합니다.");
    }

    /**
     * 상품 노출
     */
    private void printAllItems() {
        ItemResponseDto ItemResponseDto = getItemResponseDto();
        StringBuilder sb = new StringBuilder();

        sb.append("상품번호\t상품명\t\t\t\t\t판매가격\t재고수\n");
        for (ItemDto itemDto : ItemResponseDto.getItemDtos()) {
            sb.append(itemDto.getId() + "\t" + itemDto.getTitle() + "\t\t"
                    + itemDto.getPrice() + "\t" + itemDto.getStockQuantity() + "\n");
        }
        sb.append("\n");
        System.out.println(sb.toString());
    }

    /**
     * 상품리스트 조회
     *
     * @return
     */
    private ItemResponseDto getItemResponseDto() {
        String url = "http://localhost:8080/api/v1/items";
        ResponseEntity<ItemResponseDto> response = restTemplate.getForEntity(url, ItemResponseDto.class);
        ObjectMapper mapper = new ObjectMapper();
        ItemResponseDto ItemResponseDto = mapper.convertValue(response.getBody(), new TypeReference<ItemResponseDto>() {
        });
        return ItemResponseDto;
    }

    /**
     * 에러 읽기
     *
     * @param e
     * @return
     */
    public ErrorResponseDto errorRead(HttpStatusCodeException e) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            return objectMapper.readValue(e.getResponseBodyAsByteArray(), ErrorResponseDto.class);
        } catch (IOException ex) {
            ex.printStackTrace();
            return new ErrorResponseDto();
        }
    }

    /**
     * 에러 노출
     *
     * @param errorResponseDto
     */
    public void errorHandler(ErrorResponseDto errorResponseDto) {
        OrderErrorCode orderErrorCode = OrderErrorCode.getOrderErrorCode(errorResponseDto.getErrorCode());
        switch (orderErrorCode) {
            case SOLD_OUT:
                System.out.println("SoldOutException 발생. 주문한 상품량이 재고량보다 큽니다.");
                break;
            case INVALID_ITEM:
                System.out.println("등록되지 않은 상품입니다.");
                break;
            case BAD_REQUEST:
                System.out.println(errorResponseDto.getMessage());
                break;
            default:
                System.out.println(errorResponseDto.getMessage());
        }
    }
}