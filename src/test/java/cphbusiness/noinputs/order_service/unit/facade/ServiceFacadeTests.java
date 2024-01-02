package cphbusiness.noinputs.order_service.unit.facade;

import cphbusiness.noinputs.order_service.main.dto.OrderDTO;
import cphbusiness.noinputs.order_service.main.dto.OrderFoodItemDTO;
import cphbusiness.noinputs.order_service.main.dto.RestaurantDTO;
import cphbusiness.noinputs.order_service.main.exception.*;
import cphbusiness.noinputs.order_service.main.facade.ServiceFacade;
import cphbusiness.noinputs.order_service.main.service.JwtService;
import cphbusiness.noinputs.order_service.main.service.MessageService;
import cphbusiness.noinputs.order_service.main.service.OrderService;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
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
public class ServiceFacadeTests {

    @Autowired
    private ServiceFacade serviceFacade;

    @MockBean
    private OrderService orderService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private MessageService messageService;

    @BeforeEach
    public void setup() throws RestaurantNotFoundException {
        Faker faker = new Faker();
        when(messageService.getRestaurant(any(Long.class))).thenReturn(new RestaurantDTO(1L, faker.restaurant().name(), null));
    }

    @Test
    public void createOrderTest() throws InvalidJwtTokenException, RestaurantNotFoundException, FoodItemNotFoundException {
        // Arrange
        when(jwtService.getUserIdFromJwtToken(any(String.class))).thenReturn(1L);
        OrderDTO orderDTO = new OrderDTO(1L, 1L);
        when(orderService.createOrder(any(Long.class), any(Long.class), any())).thenReturn(orderDTO);
        List<OrderFoodItemDTO> orderFoodItemDTOS = new ArrayList<>();

        // Act
        OrderDTO createOrderDTO = serviceFacade.createOrder("dummy-token", 1L, orderFoodItemDTOS);

        // Assert
        assertEquals(1L, createOrderDTO.getOrderId());
    }

    @Test
    public void createOrderShouldThrowInvalidJwtTokenException() throws InvalidJwtTokenException, RestaurantNotFoundException, FoodItemNotFoundException {
        // Arrange
        when(jwtService.getUserIdFromJwtToken(any(String.class))).thenThrow(new InvalidJwtTokenException("Invalid JWT token"));
        OrderDTO orderDTO = new OrderDTO(1L, 1L);
        when(orderService.createOrder(any(Long.class), any(Long.class), any())).thenReturn(orderDTO);

        // Act and Assert
        assertThrows(InvalidJwtTokenException.class, () -> serviceFacade.createOrder("invalid-token", 1L, null));
    }

    @Test
    public void createOrderShouldThrowRestaurantNotFoundException() throws InvalidJwtTokenException, RestaurantNotFoundException {
        // Arrange
        when(jwtService.getUserIdFromJwtToken(any(String.class))).thenReturn(1L);
        when(messageService.getRestaurant(any(Long.class))).thenThrow(new RestaurantNotFoundException("Restaurant not found"));
        ArrayList<OrderFoodItemDTO> orderFoodItemDTOS = new ArrayList<>();

        // Act and Assert
        assertThrows(RestaurantNotFoundException.class, () -> serviceFacade.createOrder("dummy-token", 1L, orderFoodItemDTOS));
    }

    @Test
    public void getOrderTest() throws OrderNotFoundException, InvalidJwtTokenException, NotAuthorizedException {
        // Arrange
        when(jwtService.getUserIdFromJwtToken(any(String.class))).thenReturn(1L);
        when(orderService.getOrder(any(Long.class))).thenReturn(new OrderDTO(1L, 1L, 1L));

        // Act
        OrderDTO order = serviceFacade.getOrder("dummyToken", 1L);

        // Assert
        assertEquals(1L, order.getOrderId());
        assertEquals(1L, order.getRestaurantId());
    }

    @Test
    public void getOrderShouldThrowInvalidJwtTokenException() throws OrderNotFoundException, InvalidJwtTokenException {
        // Arrange
        when(jwtService.getUserIdFromJwtToken(any(String.class))).thenThrow(new InvalidJwtTokenException("Invalid JWT token"));
        when(orderService.getOrder(any(Long.class))).thenReturn(new OrderDTO(1L, 1L));

        // Act and Assert
        assertThrows(InvalidJwtTokenException.class, () -> serviceFacade.getOrder("dummyToken", 1L));
    }

    @Test
    public void getOrderShouldThrowNotAuthorizedWhenUserIsNotAuthorized() throws OrderNotFoundException, InvalidJwtTokenException {
        // Arrange
        when(jwtService.getUserIdFromJwtToken(any(String.class))).thenReturn(1L);
        OrderDTO order = new OrderDTO(1L, 2L);
        order.setCustomerId(2L);
        when(orderService.getOrder(any(Long.class))).thenReturn(order);

        // Act and Assert
        assertThrows(NotAuthorizedException.class, () -> serviceFacade.getOrder("dummyToken", 1L));
    }
}
