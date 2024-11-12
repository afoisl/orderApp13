package com.sparta.orderapp13.dto;

import com.sparta.orderapp13.entity.Order;
import com.sparta.orderapp13.entity.StoreOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class OrderResponseDto {

    private UUID orderId;
    private UUID storeId;

//    private List<OrderedFoodDto> foodList; // 주문한 음식 리스트
    private int totalPrice;

    private String paymentMethod;
    private String paymentStatus;

    private String orderType;
    private String orderStatus;
    private String orderInstructions;

    private String deliveryAddress;
    private String deliveryInstructions;

    public OrderResponseDto(Order order, UUID storeId) {
        this.orderId = order.getOrderId();
        for(StoreOrder storeOrder : order.getStoreOrderList()){
            if (storeOrder.getStore().getStoreId().equals(storeId)) {
                this.storeId = storeOrder.getStore().getStoreId();
            }
        }
        this.totalPrice = order.getTotalPrice();
        this.orderType = order.getOrderType().name();
        this.orderStatus = order.getOrderStatus().name();
        this.orderInstructions = order.getOrderInstructions();
        this.deliveryAddress = order.getDeliveryAddress();
        this.deliveryInstructions = order.getDeliveryInstructions();
    }
}
