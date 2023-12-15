package cphbusiness.noinputs.order_service.main.service;

import cphbusiness.noinputs.order_service.main.dto.OrderFoodItemDTO;
import cphbusiness.noinputs.order_service.main.exception.FoodItemNotFoundException;
import cphbusiness.noinputs.order_service.main.exception.RestaurantNotFoundException;

import java.util.List;

public interface OrderService {
    long createOrder(Long userId, Long restaurantId, List<OrderFoodItemDTO> foodItems) throws RestaurantNotFoundException, FoodItemNotFoundException;
}
