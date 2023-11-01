package org.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class AppConfig {
    @Bean
    @Scope("prototype")
    public Product firstProduct() {
        Product p = new Product();
        p.setId(1);
        p.setName("Fridge");
        p.setPrice(30000000);
        p.setDescription("Cool");
        return p;
    }

    @Bean
    @Scope("prototype")
    public Product secondProduct() {
        return new Product(1, "Air conditioner", 5000000, "2nd generation");
    }

    @Bean
    public Product thirdProduct() {
        return new Product(3, "Fan MU",3000000, "inside the cave");
    }
}
