package cphbusiness.noinputs.order_service.unit.controller;

import cphbusiness.noinputs.order_service.main.dto.CreateOrderDTO;
import cphbusiness.noinputs.order_service.main.exception.FoodItemNotFoundException;
import cphbusiness.noinputs.order_service.main.exception.InvalidJwtTokenException;
import cphbusiness.noinputs.order_service.main.exception.RestaurantNotFoundException;
import cphbusiness.noinputs.order_service.main.facade.ServiceFacade;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.graphql.test.tester.HttpGraphQlTester;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.client.MockMvcWebTestClient;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class OrderControllerTests {

    private final HttpGraphQlTester graphQlTester;

    @MockBean
    private ServiceFacade serviceFacade;

    @Autowired
    public OrderControllerTests(WebApplicationContext context) {
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
        when(serviceFacade.createOrder(any(String.class), any(Long.class), any())).thenReturn(new CreateOrderDTO(1L));
        String query = "mutation CreateOrder { createOrder(order: { restaurantId: \"1\" })}";

        // Act and Assert
        graphQlTester.document(query)
                .execute()
                .path("data.createOrder")
                .entity(String.class)
                .isEqualTo("Order created: 1");
    }

    @Disabled
    @Test
    public void createOrderShouldReturnInvalidJwtTokenWhenParsingInvalidJWTCookie() {
        // TODO: Implement test
    }
}
