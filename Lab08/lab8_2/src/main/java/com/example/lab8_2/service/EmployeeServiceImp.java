package com.example.lab8_2.service;

import com.example.lab8_2.entity.Employeee;
import com.example.lab8_2.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeServiceImp implements EmployeeService{

    @Autowired
    EmployeeRepository employeeRepository;

    @Override
    public List<Employeee> getAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Employeee save(Employeee employeee) {
        return employeeRepository.save(employeee);
    }
}
