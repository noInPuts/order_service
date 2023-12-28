package cphbusiness.noinputs.order_service.main.repository;

import cphbusiness.noinputs.order_service.main.model.FoodItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FoodItemRepository extends CrudRepository<FoodItem, Long> {
}
