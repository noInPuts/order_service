package cphbusiness.noinputs.order_service.main.repository;

import cphbusiness.noinputs.order_service.main.model.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
}
