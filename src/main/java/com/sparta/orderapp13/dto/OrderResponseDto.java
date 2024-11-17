package com.sparta.orderapp13.dto;

import com.sparta.orderapp13.entity.Order;
import com.sparta.orderapp13.entity.OrderFood;
import com.sparta.orderapp13.entity.StoreOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class OrderResponseDto {

    private UUID orderId;
    private UUID storeId;

    private List<OrderFoodDetailDto> foodList; // 주문한 음식 리스트
    private int totalPrice;

    private String orderType;
    private String orderStatus;
    private String orderInstructions;

    private String deliveryAddress;
    private String deliveryInstructions;

    public OrderResponseDto(Order order, List<OrderFood> foodList) {
        this.orderId = order.getOrderId();
        for(StoreOrder storeOrder : order.getStoreOrderList()){
            if (storeOrder.getStore().getStoreId().equals(storeId)) {
                this.storeId = storeOrder.getStore().getStoreId();
            }
        }

        this.foodList = new ArrayList<>();
        for (OrderFood orderFood : foodList) {
            this.foodList.add(new OrderFoodDetailDto(
                    orderFood.getFood().getFoodName(),
                    orderFood.getFood().getFoodImg(),
                    orderFood.getFood().getFoodPrice(),
                    orderFood.getQuantity()
            ));
        }

        this.totalPrice = order.getTotalPrice();
        this.orderType = String.valueOf(order.getOrderType());
        this.orderStatus = String.valueOf(order.getOrderStatus());
        this.orderInstructions = order.getOrderInstructions();
        this.deliveryAddress = order.getDeliveryAddress();
        this.deliveryInstructions = order.getDeliveryInstructions();
    }
}
