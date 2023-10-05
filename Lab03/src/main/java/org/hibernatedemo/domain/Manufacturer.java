package org.hibernatedemo.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter @Setter
@Entity(name = "Manufacturer")
public class Manufacturer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "location", nullable = false)
    private String location;
    @Column(name = "employee", nullable = false)
    private int employee;
    @OneToMany(mappedBy = "manufacturer")
    private List<Phone> phones;

    public Manufacturer(String name, String location, int employee, List<Phone> phones) {
        this.name = name;
        this.location = location;
        this.employee = employee;
        this.phones = phones;
    }

    public Manufacturer(String name, String location, int employee) {
        this.name = name;
        this.location = location;
        this.employee = employee;
    }

    public Manufacturer() {

    }

    @Override
    public String toString() {
        return "Manufacture [id=" + id + ", name=" + name + ", location=" + location + ", employee=" + employee + "]";
    }
}
