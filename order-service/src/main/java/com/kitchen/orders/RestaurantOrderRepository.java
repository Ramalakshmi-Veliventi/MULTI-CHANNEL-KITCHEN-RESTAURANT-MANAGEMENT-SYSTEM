package com.kitchen.orders;
import org.springframework.data.jpa.repository.JpaRepository;
public interface RestaurantOrderRepository extends JpaRepository<RestaurantOrder, Long> {}
