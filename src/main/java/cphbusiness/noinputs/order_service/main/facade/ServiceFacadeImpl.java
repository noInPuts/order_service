package cphbusiness.noinputs.order_service.main.facade;

import cphbusiness.noinputs.order_service.main.dto.CreateOrderDTO;
import cphbusiness.noinputs.order_service.main.dto.OrderFoodItemDTO;
import cphbusiness.noinputs.order_service.main.exception.FoodItemNotFoundException;
import cphbusiness.noinputs.order_service.main.exception.InvalidJwtTokenException;
import cphbusiness.noinputs.order_service.main.exception.RestaurantNotFoundException;
import cphbusiness.noinputs.order_service.main.service.JwtService;
import cphbusiness.noinputs.order_service.main.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceFacadeImpl implements ServiceFacade {
    private final OrderService orderService;

    private final JwtService jwtService;

    @Autowired
    public ServiceFacadeImpl(OrderService orderService, JwtService jwtService) {
        this.orderService = orderService;
        this.jwtService = jwtService;
    }

    @Override
    public CreateOrderDTO createOrder(String jwtToken, Long restaurantId, List<OrderFoodItemDTO> foodItems) throws RestaurantNotFoundException, FoodItemNotFoundException, InvalidJwtTokenException {
        Long userId = jwtService.getUserIdFromJwtToken(jwtToken);
        Long orderId = orderService.createOrder(userId, restaurantId, null);
        return new CreateOrderDTO(orderId);
    }
}
