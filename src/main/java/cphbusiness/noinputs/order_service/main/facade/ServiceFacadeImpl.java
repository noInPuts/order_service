package cphbusiness.noinputs.order_service.main.facade;

import cphbusiness.noinputs.order_service.main.dto.FoodItemDTO;
import cphbusiness.noinputs.order_service.main.dto.OrderDTO;
import cphbusiness.noinputs.order_service.main.dto.OrderFoodItemDTO;
import cphbusiness.noinputs.order_service.main.dto.RestaurantDTO;
import cphbusiness.noinputs.order_service.main.exception.*;
import cphbusiness.noinputs.order_service.main.service.JwtService;
import cphbusiness.noinputs.order_service.main.service.MessageService;
import cphbusiness.noinputs.order_service.main.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServiceFacadeImpl implements ServiceFacade {
    private final OrderService orderService;

    private final JwtService jwtService;
    private final MessageService messageService;

    @Autowired
    public ServiceFacadeImpl(OrderService orderService, JwtService jwtService, MessageService messageService) {
        this.orderService = orderService;
        this.jwtService = jwtService;
        this.messageService = messageService;
    }

    @Override
    public OrderDTO createOrder(String jwtToken, Long restaurantId, List<OrderFoodItemDTO> foodItems) throws RestaurantNotFoundException, FoodItemNotFoundException, InvalidJwtTokenException {
        Long userId = jwtService.getUserIdFromJwtToken(jwtToken);
        RestaurantDTO restaurantDTO = messageService.getRestaurant(restaurantId);

        for (OrderFoodItemDTO foodItem : foodItems) {
            FoodItemDTO foodItemDTO = restaurantDTO.getMenu().stream().findFirst()
                    .filter(item -> foodItem.getId().equals(item.getFoodItemId()))
                    .orElseThrow(() -> new FoodItemNotFoundException("Food item not found"));

            foodItem.setPrice(foodItemDTO.getPrice());
            foodItem.setName(foodItemDTO.getName());
        }

        return orderService.createOrder(userId, restaurantId, foodItems);
    }

    @Override
    public OrderDTO getOrder(String jwtToken, Long orderId) throws OrderNotFoundException, InvalidJwtTokenException, NotAuthorizedException {
        Long userId = jwtService.getUserIdFromJwtToken(jwtToken);
        OrderDTO orderDTO = orderService.getOrder(orderId);

        if (orderDTO.getCustomerId() != userId) {
            throw new NotAuthorizedException("This user is not authorized to view this order");
        }

        return orderDTO;
    }
}
