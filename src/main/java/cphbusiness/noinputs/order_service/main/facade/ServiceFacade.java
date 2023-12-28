package cphbusiness.noinputs.order_service.main.facade;

import cphbusiness.noinputs.order_service.main.dto.OrderDTO;
import cphbusiness.noinputs.order_service.main.dto.OrderFoodItemDTO;
import cphbusiness.noinputs.order_service.main.exception.FoodItemNotFoundException;
import cphbusiness.noinputs.order_service.main.exception.InvalidJwtTokenException;
import cphbusiness.noinputs.order_service.main.exception.RestaurantNotFoundException;

import java.util.List;

public interface ServiceFacade {
    OrderDTO createOrder(String jwtToken, Long restaurantId, List<OrderFoodItemDTO> foodItems) throws RestaurantNotFoundException, FoodItemNotFoundException, InvalidJwtTokenException;
}
