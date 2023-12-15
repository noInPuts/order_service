package cphbusiness.noinputs.order_service.unit.service;

import cphbusiness.noinputs.order_service.main.exception.InvalidJwtTokenException;
import cphbusiness.noinputs.order_service.main.service.JwtService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import javax.crypto.SecretKey;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class JwtServiceTests {

    @Autowired
    private JwtService jwtService;

    @Value("${jwt.secret}")
    private String pKey;

    @Test
    public void getUserIdFromJWTTokenShouldReturnUserID() throws InvalidJwtTokenException {
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


        // Act and Assert
        assertEquals(2L, jwtService.getUserIdFromJwtToken(jwtToken));
    }

    @Test
    public void getUserFromJWTTokenShouldThrowInvalidJWTTokenExceptionWhenParsingInvalidJWTToken() {
        // Arrange
        String badJwtToken = "";

        // Act and Assert
        assertThrows(InvalidJwtTokenException.class, () -> jwtService.getUserIdFromJwtToken(badJwtToken));
    }

    @Test
    public void getUserFromJwtTokenShouldThrowInvalidJWTTokenExceptionWhenParsingWrongComputedSignatureToken() {
        // Arrange
        String badJwtToken = "eyJpZCI6NTIsInVzZXJuYW1lIjoid3RhMnNhZiIsInJvcGUiOiJ1c2VyIiwiYWxnIjoiSFM1MTIifQ.eyJpYXQiOjE3MDE2NzEwMTIsImV4cCI6MTcwMTg0MzgxMn0.tIARH0mYN67ZuP8BFmPQpLV6I-wSRCpl7NQ9OB1bz6CXakGRH40amCgDfYq2deuaCh3XNGEjYS3YAEJBZPxqCg";

        // Act and Assert
        assertThrows(InvalidJwtTokenException.class, () -> jwtService.getUserIdFromJwtToken(badJwtToken));
    }
}
