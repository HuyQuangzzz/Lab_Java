package com.example.lab9.repository;

import java.util.List;


import com.example.lab9.model.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    @Modifying
    @Transactional
    @Query("UPDATE Product o SET o.active = :activeStatus WHERE o.id = :productId")
    public void updateProductStatusById(Long productId, boolean activeStatus);

    @Query("SELECT o FROM Product o WHERE o.active = true")
    public List<Product> findAll();

    @Query("SELECT o FROM Product o WHERE o.active = true and o.code = :id")
    public Product getProductById(long id);
}
