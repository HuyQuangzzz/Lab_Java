package com.example.lab9.repository;

import java.util.List;
import java.util.Optional;


import com.example.lab9.model.Order;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    
    @Modifying
    @Transactional
    @Query("UPDATE Order o SET o.active = :activeStatus WHERE o.id = :orderId")
    public void updateOrderStatusById(Long orderId, boolean activeStatus);

    @Query("SELECT o FROM Order o WHERE o.active = true")
    public List<Order> findAll();

    @Query("SELECT o FROM Order o WHERE o.active = true and o.orderNumber = :id")
    public Order getOrderById(long id);


}
