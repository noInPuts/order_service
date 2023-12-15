package cphbusiness.noinputs.order_service.main.dto;

public class CreateOrderDTO {
    private Long orderId;

    public CreateOrderDTO(Long orderId) {
        this.orderId = orderId;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
}
