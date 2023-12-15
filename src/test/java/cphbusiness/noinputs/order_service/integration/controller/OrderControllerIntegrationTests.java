package cphbusiness.noinputs.order_service.integration.controller;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.test.tester.HttpGraphQlTester;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.servlet.client.MockMvcWebTestClient;
import org.springframework.web.context.WebApplicationContext;

import javax.crypto.SecretKey;
import java.util.Date;

@SpringBootTest
public class OrderControllerIntegrationTests {

    private HttpGraphQlTester graphQlTester;

    @Value("${jwt.secret}")
    private String pKey;

    @Autowired
    private WebApplicationContext context;

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

    @Test
    public void testCreateOrder() {
        // Arrange
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
