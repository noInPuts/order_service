package cphbusiness.noinputs.order_service.main.dto;



public class OrderFoodItemDTO {

    // Food item ID
    private Long id;

    private int quantity;

    private double price;

    private String name;

    public OrderFoodItemDTO(Long id, int quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public OrderFoodItemDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "OrderFoodItemDTO{" +
                "id=" + id +
                ", quantity=" + quantity +
                ", price=" + price +
                ", name='" + name + '\'' +
                '}';
    }
}
