package com.example.lab9.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/products")
public class ProductController {
    
    @Autowired
    private ProductService service;


    private List<Long> getOrderIds(List<Order> orders) {
        return orders.stream().map(o -> o.getOrderNumber()).toList();
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<Product> products = service.getAllProducts();
        List<ProductDTO> productDTOs = products.stream()
            .map(p -> new ProductDTO(
                p.getCode(),
                p.getName(),
                p.getIllustration(),
                p.getPrice(),
                p.getDescription(),
                getOrderIds(p.getOrders())
            )).toList();

        return ResponseEntity.ok(new ResponseData(
            200,
            "Success",
            productDTOs
        ));
    }

    @PostMapping
    public ResponseEntity<?> addProduct(@Valid @RequestBody Product product) {
        product.setActive(true);
        product.setOrders(new ArrayList<Order>());
        service.addProduct(product);
        return ResponseEntity.ok(new ResponseData(
            200,
            "Add product successful",
            product
        ));
    
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") @Valid Long id) {
        Optional<Product> tmp = service.getProductById(id);

        if(tmp.isPresent()) {
            ProductDTO productDTO = new ProductDTO(
                tmp.get().getCode(),
                tmp.get().getName(),
                tmp.get().getIllustration(),
                tmp.get().getPrice(),
                tmp.get().getDescription(),
                getOrderIds(tmp.get().getOrders())
            );

            return ResponseEntity.ok(new ResponseData(
            200,
            "Success",
                productDTO
            ));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseData(
            HttpStatus.NOT_FOUND.value(),
           "Not found ID = " + id,
           ""
        ));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> replaceproduct(@PathVariable("id") @Valid Long id, @RequestBody Product product) {
        Optional<Product> tmp = service.getProductById(id);

        if(tmp.isPresent()) {
            Product foundProduct = tmp.get();

            foundProduct.setDescription(product.getDescription());
            foundProduct.setIllustration(product.getIllustration());
            foundProduct.setName(product.getName());
            foundProduct.setPrice(product.getPrice());

            service.updateProduct(foundProduct);

            return ResponseEntity.ok(new ResponseData(
                200,
                "Update product successful",
                foundProduct
            ));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseData(
            HttpStatus.NOT_FOUND.value(),
           "Not found ID = " + id,
           ""
        ));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable("id") @Valid Long id, @RequestBody Product product) {
        Optional<Product> tmp = service.getProductById(id);

        if(tmp.isPresent()) {
            Product foundProduct = tmp.get();

            if(product.getName() != null)
                foundProduct.setName(product.getName());
            if(product.getDescription() != null)
                foundProduct.setDescription(product.getDescription());
            if(product.getIllustration() != null)
                foundProduct.setIllustration(product.getIllustration());
            if(product.getPrice() != null)
                foundProduct.setPrice(product.getPrice());

            return ResponseEntity.ok(new ResponseData(
                200,
                "Update product successful",
                foundProduct
            ));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseData(
            HttpStatus.NOT_FOUND.value(),
           "Not found ID = " + id,
           ""
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable @Valid Long id) {
        Optional<Product> tmp = service.getProductById(id);

        if(tmp.isPresent()) {
            service.deleteProduct(tmp.get().getCode(), false);
            return ResponseEntity.ok(new ResponseData(
                200,
                "Delete product successful",
                ""
            ));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseData(
            HttpStatus.NOT_FOUND.value(),
           "Not found ID = " + id,
           ""
       ));
    }
}
