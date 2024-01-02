package cphbusiness.noinputs.order_service.unit.controller;

import cphbusiness.noinputs.order_service.main.dto.OrderDTO;
import cphbusiness.noinputs.order_service.main.dto.OrderFoodItemDTO;
import cphbusiness.noinputs.order_service.main.exception.*;
import cphbusiness.noinputs.order_service.main.facade.ServiceFacade;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.graphql.test.tester.HttpGraphQlTester;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.client.MockMvcWebTestClient;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class OrderControllerTests {

    private HttpGraphQlTester graphQlTester;

    @MockBean
    private ServiceFacade serviceFacade;

    @Autowired
    private WebApplicationContext context;

    @PostConstruct
    public void setup() {
        // Arrange
        WebTestClient.Builder client =
                MockMvcWebTestClient.bindToApplicationContext(context)
                        .configureClient()
                        .defaultCookie("jwt-token", "dummy-token")
                        .baseUrl("/api/order");

        graphQlTester = HttpGraphQlTester.builder(client)
                .build();
    }

    @Test
    public void testCreateOrder() throws InvalidJwtTokenException, RestaurantNotFoundException, FoodItemNotFoundException {
        // Arrange
        ArrayList<OrderFoodItemDTO> foodItems = new ArrayList<>();
        OrderFoodItemDTO orderFoodItemDTO = new OrderFoodItemDTO(1L, 2);
        orderFoodItemDTO.setName("Fettuccine Alfredo");
        orderFoodItemDTO.setPrice(4.93);
        foodItems.add(orderFoodItemDTO);
        when(serviceFacade.createOrder(any(String.class), any(Long.class), any())).thenReturn(new OrderDTO(1L, 1L, foodItems));
        String query = "mutation CreateOrder { createOrder(order: {restaurantId: \"1\", foodItems: {quantity: 2, id: 1}})}";

        // Act and Assert
        graphQlTester.document(query)
                .execute()
                .path("data.createOrder")
                .entity(String.class)
                .isEqualTo("OrderDTO{restaurantId=1, foodItems=[OrderFoodItemDTO{id=1, quantity=2, price=4.93, name='Fettuccine Alfredo'}], orderId=1}");
    }

    @Test
    public void createOrderShouldReturnInvalidJwtTokenWhenParsingInvalidJWTCookie() throws InvalidJwtTokenException, RestaurantNotFoundException, FoodItemNotFoundException {
        // Arrange
        when(serviceFacade.createOrder(any(String.class), any(Long.class), any())).thenThrow(new InvalidJwtTokenException("Invalid JWT token"));

        // Act and Assert
        graphQlTester.document("mutation CreateOrder {   createOrder(order: {restaurantId: \"1\", foodItems: {quantity: 2, id: 1}})\n}")
                .execute()
                .errors()
                .satisfy(errors -> {
                    assertThat(errors).isNotEmpty(); // Ensure there are errors
                    assertThat(errors.get(0).getMessage()).contains("INTERNAL_ERROR");
                });
    }

    @Test
    public void createOrderShouldReturnRestaurantNotFoundException() throws InvalidJwtTokenException, RestaurantNotFoundException, FoodItemNotFoundException {
        // Arrange
        when(serviceFacade.createOrder(any(String.class), any(Long.class), any())).thenThrow(new RestaurantNotFoundException("Restaurant not found"));

        // Act and Assert
        graphQlTester.document("mutation CreateOrder {   createOrder(order: {restaurantId: \"1\", foodItems: {quantity: 2, id: 1}})\n}")
                .execute()
                .errors()
                .satisfy(errors -> {
                    assertThat(errors).isNotEmpty();
                    assertThat(errors.get(0).getMessage()).contains("INTERNAL_ERROR");
                });
    }

    @Test
    public void createOrderShouldReturnFoodItemNotFoundException() throws InvalidJwtTokenException, RestaurantNotFoundException, FoodItemNotFoundException {
        // Arrange
        when(serviceFacade.createOrder(any(String.class), any(Long.class), any())).thenThrow(new FoodItemNotFoundException("Food item not found"));
        WebTestClient.Builder client =
                MockMvcWebTestClient.bindToApplicationContext(context)
                        .configureClient()
                        .baseUrl("/api/order");

        HttpGraphQlTester graphQlTester = HttpGraphQlTester.builder(client)
                .build();

        // Act and Assert
        graphQlTester.document("mutation CreateOrder {   createOrder(order: {restaurantId: \"1\", foodItems: {quantity: 2, id: 1}})\n}")
                .execute()
                .errors()
                .satisfy(errors -> {
                    assertThat(errors).isNotEmpty(); 
                    assertThat(errors.get(0).getMessage()).contains("INTERNAL_ERROR");
                });
    }

    @Test
    public void getOrder() throws OrderNotFoundException, InvalidJwtTokenException, NotAuthorizedException {
        // Arrange
        ArrayList<OrderFoodItemDTO> foodItems = new ArrayList<>();
        OrderFoodItemDTO orderFoodItemDTO = new OrderFoodItemDTO(1L, 2);
        orderFoodItemDTO.setName("Fettuccine Alfredo");
        orderFoodItemDTO.setPrice(4.93);
        foodItems.add(orderFoodItemDTO);
        when(serviceFacade.getOrder(any(String.class), any(Long.class))).thenReturn(new OrderDTO(1L, 1L, foodItems));
        String query = "query GetOrder { getOrder(id: 1)}";

        // Act and assert
        graphQlTester.document(query)
                .execute()
                .path("data.getOrder")
                .entity(String.class)
                .isEqualTo("OrderDTO{restaurantId=1, foodItems=[OrderFoodItemDTO{id=1, quantity=2, price=4.93, name='Fettuccine Alfredo'}], orderId=1}");
    }
}
