package com.example.lab9.service;

import java.util.List;
import java.util.Optional;

import com.example.lab9.model.Product;
import com.example.lab9.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ProductService {
    
    @Autowired
    private ProductRepository repo;

    public List<Product> getAllProducts() {
        return repo.findAll();
    }

    public Optional<Product> getProductById(long id) {
        return repo.findById(id);
    }

    public void addProduct(Product product) {
        
        repo.save(product);
    }

    public void updateProduct(Product product) {
        repo.save(product);
    }

    public void deleteProduct(Long id, boolean activeStatus) {
       repo.updateProductStatusById(id, activeStatus);
    }

    public List<Product> getProductsByIds(List<Long> productIds) {
        return repo.findAllById(productIds);
    }

}
