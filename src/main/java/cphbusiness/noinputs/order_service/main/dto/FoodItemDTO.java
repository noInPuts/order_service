package cphbusiness.noinputs.order_service.main.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class FoodItemDTO {

        @NotNull
        private long foodItemId;

        @NotEmpty
        private String name;
        private double price;

        public FoodItemDTO() {
        }

        public FoodItemDTO(long foodItemId, String name, double price) {
            this.foodItemId = foodItemId;
            this.name = name;
            this.price = price;
        }

        public long getFoodItemId() {
            return foodItemId;
        }

        public void setFoodItemId(long foodItemId) {
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
