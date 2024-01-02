package cphbusiness.noinputs.order_service.main.service;

import cphbusiness.noinputs.order_service.main.dto.FoodItemDTO;
import cphbusiness.noinputs.order_service.main.dto.RestaurantDTO;
import cphbusiness.noinputs.order_service.main.exception.RestaurantNotFoundException;
import cphbusiness.noinputs.order_service.main.proto.GetRestaurantGrpc;
import cphbusiness.noinputs.order_service.main.proto.VerifyRestaurant;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class MessageServiceImpl extends GetRestaurantGrpc.GetRestaurantImplBase implements MessageService {

    @Value("${grpc.serveradress}")
    private String grpcServerAdress;

    @Override
    public RestaurantDTO getRestaurant(long restaurantId) throws RestaurantNotFoundException {
        VerifyRestaurant.GetRestaurantResponse response = getRestaurantResponse(restaurantId);

        if(!response.getValid()) throw new RestaurantNotFoundException("Restaurant not found");

        ArrayList<FoodItemDTO> foodItems = new ArrayList<>();

        for(VerifyRestaurant.MenuItems menuItem : response.getMenuItemsList()) {
            foodItems.add(new FoodItemDTO(menuItem.getId(), menuItem.getName(), menuItem.getPrice()));
        }

        return new RestaurantDTO(restaurantId, response.getName(), foodItems);
    }

    private VerifyRestaurant.GetRestaurantResponse getRestaurantResponse(long restaurantId) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(grpcServerAdress, 9090)
            .usePlaintext()
            .build();
        GetRestaurantGrpc.GetRestaurantBlockingStub stub = GetRestaurantGrpc.newBlockingStub(channel);

        return stub.getRestaurant(VerifyRestaurant.GetRestaurantRequest.newBuilder()
                .setRestaurantId(restaurantId)
                .build());
    }
}
