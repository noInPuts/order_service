package cphbusiness.noinputs.order_service.main.service;

import cphbusiness.noinputs.order_service.main.dto.OrderDTO;
import cphbusiness.noinputs.order_service.main.dto.OrderFoodItemDTO;
import cphbusiness.noinputs.order_service.main.exception.OrderNotFoundException;
import cphbusiness.noinputs.order_service.main.model.FoodItem;
import cphbusiness.noinputs.order_service.main.model.Order;
import cphbusiness.noinputs.order_service.main.model.OrderFoodItem;
import cphbusiness.noinputs.order_service.main.repository.FoodItemRepository;
import cphbusiness.noinputs.order_service.main.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final FoodItemRepository foodItemRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, FoodItemRepository foodItemRepository) {
        this.orderRepository = orderRepository;
        this.foodItemRepository = foodItemRepository;
    }

    public OrderDTO createOrder(Long userId, Long restaurantId, List<OrderFoodItemDTO> foodItems) {
        Order order = new Order();

        order.setCustomerId(userId);
        order.setRestaurantId(restaurantId);

        order = orderRepository.save(order);

        List<OrderFoodItem> foodItemList = new ArrayList<>();

        for (OrderFoodItemDTO orderFoodItemDTO : foodItems) {
            FoodItem foodItem = new FoodItem(orderFoodItemDTO.getName(), orderFoodItemDTO.getPrice(), orderFoodItemDTO.getId());
            foodItem = foodItemRepository.save(foodItem);

            OrderFoodItem orderFoodItem = new OrderFoodItem(foodItem, orderFoodItemDTO.getQuantity());
            orderFoodItem.setOrder(order);
            foodItemList.add(orderFoodItem);
        }

        order.setFoodItems(foodItemList);
        order = orderRepository.save(order);

        return new OrderDTO(order.getRestaurantId(), order.getId(), foodItems);
    }

    @Override
    public OrderDTO getOrder(Long orderId) throws OrderNotFoundException {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);

        if(optionalOrder.isEmpty()) {
            throw new OrderNotFoundException("Order not found");
        }

        Order order = optionalOrder.get();

        // Converting OrderFoodItem to OrderFoodItemDTO
        List<OrderFoodItemDTO> orderFoodItemDTOList = new ArrayList<>();
        for(OrderFoodItem orderFoodItem : order.getFoodItems()) {
            OrderFoodItemDTO orderFoodItemDTO = new OrderFoodItemDTO(orderFoodItem.getId(), orderFoodItem.getQuantity());
            orderFoodItemDTO.setPrice(orderFoodItem.getFoodItem().getPrice());
            orderFoodItemDTO.setName(orderFoodItem.getFoodItem().getName());
            orderFoodItemDTOList.add(orderFoodItemDTO);
        }

        return new OrderDTO(order.getRestaurantId(), order.getId(), orderFoodItemDTOList, order.getCustomerId());
    }
}
