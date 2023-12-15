package cphbusiness.noinputs.order_service.unit.service;

import cphbusiness.noinputs.order_service.main.dto.OrderFoodItemDTO;
import cphbusiness.noinputs.order_service.main.exception.FoodItemNotFoundException;
import cphbusiness.noinputs.order_service.main.exception.RestaurantNotFoundException;
import cphbusiness.noinputs.order_service.main.repository.OrderRepository;
import cphbusiness.noinputs.order_service.main.service.OrderService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class OrderServiceTests {

    @MockBean
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    @Test
    public void createOrderShouldNotThrow() throws RestaurantNotFoundException, FoodItemNotFoundException {
        // Arrange
        List<OrderFoodItemDTO> foodItems = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            foodItems.add(new OrderFoodItemDTO((long) i, 1));
        }

        // Act
        long orderId = orderService.createOrder(2L, 1L, foodItems);

        // Assert
        assertEquals(1L, orderId);
    }

    // TODO: Fully implement
    @Disabled
    @Test
    public void createOrderShouldThrowRestaurantNotFoundException() throws RestaurantNotFoundException, FoodItemNotFoundException {
        // Arrange
        List<OrderFoodItemDTO> foodItems = new ArrayList<>();

        for(int i = 0; i < 5; i++) {
            foodItems.add(new OrderFoodItemDTO((long) i, 1));
        }

        // Act and Assert
        orderService.createOrder(2L, 1L, foodItems);
    }

    // TODO: Fully implement
    @Disabled
    @Test
    public void createOrderShouldThrowFoodItemNotFoundException() throws RestaurantNotFoundException, FoodItemNotFoundException, UnknownHostException {
        List<OrderFoodItemDTO> foodItems = new ArrayList<>();

        for(int i = 0; i < 5; i++) {
            foodItems.add(new OrderFoodItemDTO((long) i, 1));
        }

        orderService.createOrder(2L, 1L, foodItems);
    }
}
