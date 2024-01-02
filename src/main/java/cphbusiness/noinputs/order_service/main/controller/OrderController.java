package cphbusiness.noinputs.order_service.main.controller;


import cphbusiness.noinputs.order_service.main.dto.OrderDTO;
import cphbusiness.noinputs.order_service.main.exception.*;
import cphbusiness.noinputs.order_service.main.facade.ServiceFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.ContextValue;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.http.HttpCookie;
import org.springframework.stereotype.Controller;

@Controller
public class OrderController {

    private final ServiceFacade serviceFacade;

    @Autowired
    public OrderController(ServiceFacade serviceFacade) {
        this.serviceFacade = serviceFacade;
    }

    @MutationMapping(name = "createOrder")
    public OrderDTO createOrder(@ContextValue(name = "jwt-token") HttpCookie jwtToken, @Argument(name = "order") OrderDTO order) throws InvalidJwtTokenException, RestaurantNotFoundException, FoodItemNotFoundException, InterruptedException {
        return serviceFacade.createOrder(jwtToken.getValue(), order.getRestaurantId(), order.getFoodItems());
    }
    @QueryMapping(name = "getOrder")
    public OrderDTO getOrder(@ContextValue(name = "jwt-token") HttpCookie jwtToken, @Argument Long id) throws OrderNotFoundException, InvalidJwtTokenException, NotAuthorizedException {
        return serviceFacade.getOrder(jwtToken.getValue(), id);
    }
}
