package cphbusiness.noinputs.order_service.unit.service;

import cphbusiness.noinputs.order_service.main.dto.OrderDTO;
import cphbusiness.noinputs.order_service.main.dto.OrderFoodItemDTO;
import cphbusiness.noinputs.order_service.main.exception.FoodItemNotFoundException;
import cphbusiness.noinputs.order_service.main.exception.RestaurantNotFoundException;
import cphbusiness.noinputs.order_service.main.model.Order;
import cphbusiness.noinputs.order_service.main.repository.OrderRepository;
import cphbusiness.noinputs.order_service.main.service.OrderService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class OrderServiceTests {

    @MockBean
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    @Test
    public void createOrderShouldNotThrow() throws RestaurantNotFoundException, FoodItemNotFoundException {
        Order order = new Order(1L, null, 1L);
        order.setId(1L);

        when(orderRepository.save(any(Order.class))).thenReturn(order);

        // Arrange
        List<OrderFoodItemDTO> foodItems = new ArrayList<>();
        for(int i = 0; i < 5; i++) {
            foodItems.add(new OrderFoodItemDTO((long) i, 1));
        }

        // Act
        OrderDTO orderDTO = orderService.createOrder(2L, 1L, foodItems);

        // Assert
        assertEquals(1L, orderDTO.getOrderId());
    }
}
