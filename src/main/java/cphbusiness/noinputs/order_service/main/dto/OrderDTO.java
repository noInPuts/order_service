package cphbusiness.noinputs.order_service.main.dto;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public class OrderDTO {


    @NotNull
    private Long restaurantId;

    @NotNull
    private List<OrderFoodItemDTO> foodItems;

    private Long orderId;

    public OrderDTO() {
    }

    public OrderDTO(Long restaurantId, List<OrderFoodItemDTO> foodItems) {
        this.restaurantId = restaurantId;
        this.foodItems = foodItems;
    }

    public OrderDTO(Long restaurantId, Long orderId) {
        this.restaurantId = restaurantId;
        this.orderId = orderId;
    }

    public OrderDTO(Long restaurantId, Long id, List<OrderFoodItemDTO> foodItems) {
        this.restaurantId = restaurantId;
        this.orderId = id;
        this.foodItems = foodItems;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public List<OrderFoodItemDTO> getFoodItems() {
        return foodItems;
    }

    public void setFoodItems(List<OrderFoodItemDTO> foodItems) {
        this.foodItems = foodItems;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return "OrderDTO{" +
                "restaurantId=" + restaurantId +
                ", foodItems=" + foodItems +
                ", orderId=" + orderId +
                '}';
    }
}
