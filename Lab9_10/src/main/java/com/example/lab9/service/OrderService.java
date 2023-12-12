package com.example.lab9.service;

import com.example.lab9.model.Order;
import com.example.lab9.model.Product;
import com.example.lab9.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;


@Service
public class OrderService {
    
    @Autowired
    private OrderRepository repo;

    @Autowired
    private ProductService productService;

    public List<Order> getAllOrders() {
        return repo.findAll();
    }

    public Optional<Order> getOrderById(long id) {
        return repo.findById(id);
    }

    public void addOrder(Order order) {
        repo.save(order);
    }

    public void updateOrder(Order order) {
        repo.save(order);
    }

    public void deleteOrder(Long id, boolean activeStatus) {
       repo.updateOrderStatusById(id, activeStatus);
    }

    public List<Product> getProductByIds(List<Long> ids) {
        return productService.getProductsByIds(ids);
    }

}
