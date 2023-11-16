package com.example.lab8_2.repository;

import com.example.lab8_2.entity.Employeee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employeee, Integer>                 {
}
