package cphbusiness.noinputs.order_service.integration.controller;

import cphbusiness.noinputs.order_service.main.dto.FoodItemDTO;
import cphbusiness.noinputs.order_service.main.dto.RestaurantDTO;
import cphbusiness.noinputs.order_service.main.exception.RestaurantNotFoundException;
import cphbusiness.noinputs.order_service.main.service.MessageService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import net.datafaker.Faker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.graphql.test.tester.HttpGraphQlTester;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.client.MockMvcWebTestClient;
import org.springframework.web.context.WebApplicationContext;

import javax.crypto.SecretKey;
import java.util.ArrayList;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class OrderControllerIntegrationTests {

    private HttpGraphQlTester graphQlTester;

    @Value("${jwt.secret}")
    private String pKey;

    @Autowired
    private WebApplicationContext context;

    @MockBean
    private MessageService messageService;

    @PostConstruct
    public void init() {
        // Arrange
        // Creating JWT Token with the generated secret key
        SecretKey key = Keys.hmacShaKeyFor(pKey.getBytes());
        String jwtToken = Jwts.builder()
                .header()
                .add("id", "2")
                .add("username", "test_user")
                .add("role", "user")
                .and()
                .issuedAt(new Date(System.currentTimeMillis()))
                // Expires after 2 days of issue
                .expiration(new Date(System.currentTimeMillis() + 2 * 24 * 60 * 60 * 1000))
                .signWith(key)
                .compact();

        WebTestClient.Builder client =
                MockMvcWebTestClient.bindToApplicationContext(context)
                        .configureClient()
                        .defaultCookie("jwt-token", jwtToken)
                        .baseUrl("/api/order");

        graphQlTester = HttpGraphQlTester.builder(client)
                .build();
    }


    @BeforeEach
    public void setup() throws RestaurantNotFoundException, InterruptedException {
        Faker faker = new Faker();
        ArrayList<FoodItemDTO> foodItems = new ArrayList<>();
        foodItems.add(new FoodItemDTO(1L, "Lasagna", 4.93));
        when(messageService.getRestaurant(any(Long.class))).thenReturn(new RestaurantDTO(1L, faker.restaurant().name(), foodItems));
    }


    @Test
    public void testCreateOrder() {
        // Arrange
        String query = "mutation CreateOrder {   createOrder(order: {restaurantId: \"1\", foodItems: {quantity: 2, id: 1}})\n}";

        // Act and Assert
        graphQlTester.document(query)
                .execute()
                .path("data.createOrder")
                .entity(String.class)
                .isEqualTo("{ \"restaurantId\": 1, \"foodItems\": [{\"id\":1, \"quantity\":2, \"price\":4.93, \"name\":\"Lasagna\"}], \"orderId\": 1 }");
    }

    @Test
    public void createOrderShouldReturnRestaurantNotFoundException() throws RestaurantNotFoundException, InterruptedException {
        // Arrange
        when(messageService.getRestaurant(any(Long.class))).thenThrow(new RestaurantNotFoundException("Restaurant not found"));

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
    public void createOrderShouldReturnFoodItemNotFoundException() {
        // Act and Assert
        graphQlTester.document("mutation CreateOrder {   createOrder(order: {restaurantId: \"1\", foodItems: {quantity: 2, id: 5}})\n}")
                .execute()
                .errors()
                .satisfy(errors -> {
                    assertThat(errors).isNotEmpty(); // Ensure there are errors
                    assertThat(errors.get(0).getMessage()).contains("INTERNAL_ERROR");
                });
    }
}
