package cphbusiness.noinputs.order_service.main.dto;

import java.util.ArrayList;

public class RestaurantDTO {

    private long restaurantId;
    private String name;
    private ArrayList<FoodItemDTO> menu;

    public RestaurantDTO() {
    }

public RestaurantDTO(long restaurantId, String name, ArrayList<FoodItemDTO> menu) {
        this.restaurantId = restaurantId;
        this.name = name;
        this.menu = menu;
    }

    public long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<FoodItemDTO> getMenu() {
        return menu;
    }

    public void setMenu(ArrayList<FoodItemDTO> menu) {
        this.menu = menu;
    }
}
