package com.example.ex06.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

@Entity
@Table(name = "student")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String name;

    private int age;

    private String email;

    private double ielts;


    public Student(String name, int age, String email, double ielts) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.ielts = ielts;
    }
}
