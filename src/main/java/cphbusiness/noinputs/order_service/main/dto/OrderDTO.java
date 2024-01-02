package cphbusiness.noinputs.order_service.main.dto;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public class OrderDTO {
    @NotNull
    private Long restaurantId;

    @NotNull
    private List<OrderFoodItemDTO> foodItems;

    private Long orderId;

    private long customerId;

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

    public OrderDTO(Long restaurantId, Long orderId, long customerId) {
        this.restaurantId = restaurantId;
        this.orderId = orderId;
        this.customerId = customerId;
    }

    public OrderDTO(Long restaurantId, Long id, List<OrderFoodItemDTO> foodItems) {
        this.restaurantId = restaurantId;
        this.orderId = id;
        this.foodItems = foodItems;
    }

    public OrderDTO(Long restaurantId, Long id, List<OrderFoodItemDTO> foodItems, long customerId) {
        this.restaurantId = restaurantId;
        this.orderId = id;
        this.foodItems = foodItems;
        this.customerId = customerId;
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

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
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
