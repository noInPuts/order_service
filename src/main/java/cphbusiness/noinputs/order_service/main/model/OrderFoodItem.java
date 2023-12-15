package cphbusiness.noinputs.order_service.main.model;

import jakarta.persistence.*;

@Entity
@Table(name = "order_food_items")
public class OrderFoodItem {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private FoodItem foodItem;

    @Column(nullable = false)
    private int quantity;

    @JoinColumn(name = "order_id", nullable = false)
    @ManyToOne
    private Order order;

    public OrderFoodItem() {
    }

    public OrderFoodItem(FoodItem foodItem, int quantity) {
        this.foodItem = foodItem;
        this.quantity = quantity;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public FoodItem getFoodItem() {
        return foodItem;
    }

    public void setFoodItem(FoodItem foodItem) {
        this.foodItem = foodItem;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
