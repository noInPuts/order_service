package cphbusiness.noinputs.order_service.unit.facade;

import cphbusiness.noinputs.order_service.main.dto.CreateOrderDTO;
import cphbusiness.noinputs.order_service.main.exception.FoodItemNotFoundException;
import cphbusiness.noinputs.order_service.main.exception.InvalidJwtTokenException;
import cphbusiness.noinputs.order_service.main.exception.RestaurantNotFoundException;
import cphbusiness.noinputs.order_service.main.facade.ServiceFacade;
import cphbusiness.noinputs.order_service.main.service.JwtService;
import cphbusiness.noinputs.order_service.main.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

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

    @Test
    public void createOrderTest() throws InvalidJwtTokenException, RestaurantNotFoundException, FoodItemNotFoundException {
        // Arrange
        when(jwtService.getUserIdFromJwtToken(any(String.class))).thenReturn(1L);
        when(orderService.createOrder(any(Long.class), any(Long.class), any())).thenReturn(1L);

        // Act
        CreateOrderDTO createOrderDTO = serviceFacade.createOrder("dummy-token", 1L, null);

        // Assert
        assertEquals(1L, createOrderDTO.getOrderId());
    }

    @Test
    public void createOrderShouldThrowInvalidJwtTokenException() throws InvalidJwtTokenException, RestaurantNotFoundException, FoodItemNotFoundException {
        // Arrange
        when(jwtService.getUserIdFromJwtToken(any(String.class))).thenThrow(new InvalidJwtTokenException("Invalid JWT token"));
        when(orderService.createOrder(any(Long.class), any(Long.class), any())).thenReturn(1L);

        // Act and Assert
        assertThrows(InvalidJwtTokenException.class, () -> serviceFacade.createOrder("invalid-token", 1L, null));
    }

    @Test
    public void createOrderShouldThrowRestaurantNotFoundException() throws InvalidJwtTokenException, RestaurantNotFoundException, FoodItemNotFoundException {
        // Arrange
        when(jwtService.getUserIdFromJwtToken(any(String.class))).thenReturn(1L);
        when(orderService.createOrder(any(Long.class), any(Long.class), any())).thenThrow(new RestaurantNotFoundException("Restaurant not found"));

        // Act and Assert
        assertThrows(RestaurantNotFoundException.class, () -> serviceFacade.createOrder("dummy-token", 1L, null));
    }

    @Test
    public void createOrderShouldThrowFoodItemNotFoundException() throws InvalidJwtTokenException, RestaurantNotFoundException, FoodItemNotFoundException {
        // Arrange
        when(jwtService.getUserIdFromJwtToken(any(String.class))).thenReturn(1L);
        when(orderService.createOrder(any(Long.class), any(Long.class), any())).thenThrow(new FoodItemNotFoundException("Food item not found"));

        // Act and Assert
        assertThrows(FoodItemNotFoundException.class, () -> serviceFacade.createOrder("dummy-token", 1L, null));
    }
}
