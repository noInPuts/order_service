package cphbusiness.noinputs.order_service.main.service;

import cphbusiness.noinputs.order_service.main.exception.InvalidJwtTokenException;

public interface JwtService {
    Long getUserIdFromJwtToken(String jwtToken) throws InvalidJwtTokenException;
}
