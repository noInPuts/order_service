package cphbusiness.noinputs.order_service.main.service;

import cphbusiness.noinputs.order_service.main.dto.OrderFoodItemDTO;
import cphbusiness.noinputs.order_service.main.exception.FoodItemNotFoundException;
import cphbusiness.noinputs.order_service.main.exception.RestaurantNotFoundException;
import cphbusiness.noinputs.order_service.main.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.UnknownHostException;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {


    private final OrderRepository orderRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public long createOrder(Long userId, Long restaurantId, List<OrderFoodItemDTO> foodItems) throws RestaurantNotFoundException, FoodItemNotFoundException {
        // Verify Restaurant with GRPC


        return 1L;


        //List<OrderFoodItem> orderFoodItems = new ArrayList<>();
        //Order order = new Order();

        /*
        List<FoodItem> foodItemList = restaurantOptional.get().getMenu();
        for (OrderFoodItemDTO orderFoodItemDTO : foodItems) {
            Optional<FoodItem> foodItemOptional = foodItemList.stream().filter(foodItem -> foodItem.getFoodItemId().equals(orderFoodItemDTO.getId())).findFirst();

            if(foodItemOptional.isPresent()) {
                orderFoodItems.add(new OrderFoodItem(foodItemOptional.get(), orderFoodItemDTO.getQuantity()));
            } else {
                throw new FoodItemNotFoundException("Food item not found");
            }
        }

        order.setCustomerId(userId);
        order.setRestaurant(restaurantOptional.get());
        order = orderRepository.save(order);

        for (OrderFoodItem orderFoodItem : orderFoodItems) {
            orderFoodItem.setOrder(order);
        }

        order.setFoodItems(orderFoodItems);

        orderRepository.save(order);

         */
    }
}
