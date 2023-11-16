package com.example.lab8_2.service;

import com.example.lab8_2.entity.Employeee;

import java.util.List;

public interface EmployeeService {
    List<Employeee> getAll();

    Employeee save(Employeee employeee);
}
