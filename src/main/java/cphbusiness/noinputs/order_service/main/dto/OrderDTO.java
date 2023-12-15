package cphbusiness.noinputs.order_service.main.dto;

import java.util.List;

public class OrderDTO {


    private Long restaurantId;

    private List<OrderFoodItemDTO> foodItems;

    public OrderDTO() {
    }

    public OrderDTO(Long restaurantId, List<OrderFoodItemDTO> foodItems) {
        this.restaurantId = restaurantId;
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
}
