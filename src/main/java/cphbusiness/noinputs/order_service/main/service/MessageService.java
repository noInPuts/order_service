package cphbusiness.noinputs.order_service.main.service;

import cphbusiness.noinputs.order_service.main.dto.RestaurantDTO;
import cphbusiness.noinputs.order_service.main.exception.RestaurantNotFoundException;

public interface MessageService {
    RestaurantDTO getRestaurant(long restaurantId) throws RestaurantNotFoundException, InterruptedException;
}
