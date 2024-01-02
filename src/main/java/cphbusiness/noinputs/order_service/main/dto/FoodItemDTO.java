package cphbusiness.noinputs.order_service.main.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class FoodItemDTO {

        @NotNull
        private Long foodItemId;

        @NotEmpty
        private String name;
        private double price;

        public FoodItemDTO() {
        }

        public FoodItemDTO(Long foodItemId, String name, double price) {
            this.foodItemId = foodItemId;
            this.name = name;
            this.price = price;
        }

        public Long getFoodItemId() {
            return foodItemId;
        }

        public void setFoodItemId(Long foodItemId) {
            this.foodItemId = foodItemId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
