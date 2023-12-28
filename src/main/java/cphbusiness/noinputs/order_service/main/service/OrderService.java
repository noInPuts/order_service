package cphbusiness.noinputs.order_service.main.service;

import cphbusiness.noinputs.order_service.main.dto.OrderDTO;
import cphbusiness.noinputs.order_service.main.dto.OrderFoodItemDTO;

import java.util.List;

public interface OrderService {
    OrderDTO createOrder(Long userId, Long restaurantId, List<OrderFoodItemDTO> foodItems);
}
