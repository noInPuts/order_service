package cphbusiness.noinputs.order_service.main.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private Long restaurantId;

    @JoinColumn(name = "food_items")
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<OrderFoodItem> foodItems;

    @Column(nullable = false)
    private Long customerId;

    public Order() {
    }

    public Order(Long restaurantId, List<OrderFoodItem> foodItems, Long customerId) {
        this.restaurantId = restaurantId;
        this.foodItems = foodItems;
        this.customerId = customerId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public List<OrderFoodItem> getFoodItems() {
        return foodItems;
    }

    public void setFoodItems(List<OrderFoodItem> foodItems) {
        this.foodItems = foodItems;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}
