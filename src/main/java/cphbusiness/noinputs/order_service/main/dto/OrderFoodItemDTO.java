package cphbusiness.noinputs.order_service.main.dto;



public class OrderFoodItemDTO {

    // Food item ID
    private Long id;

    private int quantity;

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
}
