package cphbusiness.noinputs.order_service.unit.service;

import cphbusiness.noinputs.order_service.main.dto.OrderDTO;
import cphbusiness.noinputs.order_service.main.dto.OrderFoodItemDTO;
import cphbusiness.noinputs.order_service.main.exception.OrderNotFoundException;
import cphbusiness.noinputs.order_service.main.model.FoodItem;
import cphbusiness.noinputs.order_service.main.model.Order;
import cphbusiness.noinputs.order_service.main.model.OrderFoodItem;
import cphbusiness.noinputs.order_service.main.repository.OrderRepository;
import cphbusiness.noinputs.order_service.main.service.OrderService;
import net.datafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class OrderServiceTests {

    @MockBean
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    @Test
    public void createOrderShouldNotThrow() {
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

    @Test
    public void getOrder() throws OrderNotFoundException {
        // Arrange
        Faker faker = new Faker();
        Long restaurantId = 1L;
        Long customerId = 5L;
        List<OrderFoodItem> foodItems = new ArrayList<>();
        for(long i = 0L; i < 5L; i++) {
            foodItems.add(new OrderFoodItem(new FoodItem(faker.food().dish(), faker.random().nextDouble(10, 20), i), 1));
        }
        Order order = new Order(restaurantId, foodItems, customerId);
        when(orderRepository.findById(1L)).thenReturn(java.util.Optional.of(order));

        // Act
        OrderDTO orderDTO = orderService.getOrder(1L);

        // Assert
        assertEquals(restaurantId, orderDTO.getRestaurantId());
        assertEquals(customerId, orderDTO.getCustomerId());
        assertEquals(foodItems.size(), orderDTO.getFoodItems().size());
        assertEquals(foodItems.get(0).getFoodItem().getName(), orderDTO.getFoodItems().get(0).getName());
        assertEquals(foodItems.get(0).getFoodItem().getPrice(), orderDTO.getFoodItems().get(0).getPrice());
        assertEquals(foodItems.get(0).getQuantity(), orderDTO.getFoodItems().get(0).getQuantity());
    }

    @Test
    public void getOrderThrowsExceptionWhenNoOrderIsFound() {
        // Act and Assert
        assertThrows(OrderNotFoundException.class, () -> orderService.getOrder(1L));
    }
}
