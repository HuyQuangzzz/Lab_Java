package org.hibernatedemo.domain;


import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
@Entity(name = "Phone")
@Getter @Setter
public class Phone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Long price;
    @Column(nullable = false)
    private String color;
    @Column(nullable = false)
    private String country;
    @Column(nullable = false)
    private int quantity;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "manufacturer_id")
    private Manufacturer manufacturer;

    public Phone() {
    }

    public Phone(String name, Long price, String color, String country, int quantity) {
        this.name = name;
        this.price = price;
        this.color = color;
        this.country = country;
        this.quantity = quantity;
    }

    public Phone(String name, Long price, String color, String country, int quantity, Manufacturer manufacturer) {
        this.name = name;
        this.price = price;
        this.color = color;
        this.country = country;
        this.quantity = quantity;
        this.manufacturer = manufacturer;
    }

    @Override
    public String toString() {
        return "Phone [id=" + id + ", name=" + name + ", price=" + price + ", color=" + color + ", country=" + country
                + ", quantity=" + quantity + "]";
    }
}
