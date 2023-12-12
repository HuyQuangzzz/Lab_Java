package com.example.lab9.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;




@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Autowired
    private OrderService service;

    @Autowired
    private ProductService productService;

    @Autowired 
    private UserService userService;

    @Autowired
    private JwtTokenUtils tokenUtils;

    private List<Long> convertObjectToLong(List<Product> lst) {
        return lst.stream()
                .map(t -> t.getCode())
                .toList();
    }

    private Long convertUser(Object o) {
        if(o == null)
            return null;
        User user = (User) o;
        return user.getId();
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<Order> orders = service.getAllOrders();
        List<OrderDTO> orderDTOs = orders.stream()
            .map(p -> new OrderDTO(
                p.getOrderNumber(),
                p.getTotalSellingPrice(),
                convertObjectToLong(p.getProducts()),
                convertUser(p.getUser())
            )).toList();

        return ResponseEntity.ok(new ResponseData(
            200,
            "Success",
            orderDTOs
        ));
    }

    @PostMapping
    public ResponseEntity<?> addOrder(@RequestBody @Valid OrderDTO order, HttpServletRequest request) {

        List<Product> allProducts = productService.getAllProducts();
        List<Product> finalList = new ArrayList<>();

        String jwt = (String)request.getSession().getAttribute("auth");
        String email = tokenUtils.getUserInfoFromJWT(jwt);
        User user = userService.getUserByEmail(email);

        Order newOrder = new Order();
        newOrder.setUser(user);

        double totalSellingPrice = 0;
        for(Long p : order.getIds()) {
            for (Product t : allProducts) {
                if(p == t.getCode()) {
                    finalList.add(t);
                    totalSellingPrice += t.getPrice();
                    t.getOrders().add(newOrder);
                }
            }
        }
        
        newOrder.setProducts(finalList);
        newOrder.setTotalSellingPrice(totalSellingPrice);
        newOrder.setActive(true);
        service.addOrder(newOrder);

        return ResponseEntity.ok(new ResponseData(
            200,
            "Add order successful",
            newOrder
        ));
    
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable @Valid Long id, HttpServletRequest request) {
        Optional<Order> tmp = service.getOrderById(id);

        if(tmp.isPresent()) {
          
            OrderDTO orderDTO = new OrderDTO(
                tmp.get().getOrderNumber(),
                tmp.get().getTotalSellingPrice(),
                convertObjectToLong(tmp.get().getProducts()),
                convertUser(tmp.get().getUser())
            );

           
            return ResponseEntity.ok(new ResponseData(
                200,
                "Success",
                orderDTO
            ));
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            new ResponseData(
                 HttpStatus.NOT_FOUND.value(),
                "Not found ID = " + id,
                ""
            )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> replaceOrder(@PathVariable("id") @Valid Long id, @RequestBody Order order) {
        Optional<Order> tmp = service.getOrderById(id);

        if(tmp.isPresent()) {
            Order foundOrder = tmp.get();

            foundOrder.setTotalSellingPrice(order.getTotalSellingPrice());
            service.updateOrder(foundOrder);

            return ResponseEntity.ok(new ResponseData(
                200,
                "Update order successful",
                foundOrder
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
        Optional<Order> tmp = service.getOrderById(id);

        if(tmp.isPresent()) {
            service.deleteOrder(tmp.get().getOrderNumber(), false);
            return ResponseEntity.ok(new ResponseData(
                200,
                "Delete order successful",
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
