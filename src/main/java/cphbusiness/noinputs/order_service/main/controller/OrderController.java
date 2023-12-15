package cphbusiness.noinputs.order_service.main.controller;


import cphbusiness.noinputs.order_service.main.dto.CreateOrderDTO;
import cphbusiness.noinputs.order_service.main.dto.OrderDTO;
import cphbusiness.noinputs.order_service.main.exception.FoodItemNotFoundException;
import cphbusiness.noinputs.order_service.main.exception.InvalidJwtTokenException;
import cphbusiness.noinputs.order_service.main.exception.RestaurantNotFoundException;
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
    public String createOrder(@ContextValue(name = "jwt-token") HttpCookie jwtToken, @Argument(name = "order") OrderDTO order) {
        CreateOrderDTO createdOrder;

        try {
            createdOrder = serviceFacade.createOrder(jwtToken.getValue(), order.getRestaurantId(), null);
        } catch (InvalidJwtTokenException e) {
            return "Invalid JWT token";
        } catch (RestaurantNotFoundException e) {
            return "Restaurant not found";
        } catch (FoodItemNotFoundException e) {
            return "Food item not found";
        }

        return "Order created: " + createdOrder.getOrderId();
    }

    @QueryMapping(name = "getOrders")
    public String getOrders(@Argument Long id) {
        return "Endpoint not implemented yet";
    }
}
