package cphbusiness.noinputs.order_service.main.service;

import cphbusiness.noinputs.order_service.main.exception.InvalidJwtTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
public class JwtServiceImpl implements JwtService {
    @Value("${jwt.secret}")
    private String pKey;

    public Long getUserIdFromJwtToken(String jwtToken) throws InvalidJwtTokenException {
        SecretKey key = Keys.hmacShaKeyFor(pKey.getBytes());
        Jws<Claims> jwtTokenParsed;
        try {
            jwtTokenParsed = Jwts.parser().verifyWith(key).build().parseSignedClaims(jwtToken);
        } catch (IllegalArgumentException | SignatureException e) {
            throw new InvalidJwtTokenException("Invalid JWT token");
        }

        return Long.parseLong(jwtTokenParsed.getHeader().get("id").toString());
    }
}
