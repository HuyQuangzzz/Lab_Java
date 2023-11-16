package com.example.lab8_2.controller;

import com.example.lab8_2.entity.Employeee;
import com.example.lab8_2.repository.EmployeeRepository;
import com.example.lab8_2.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    @GetMapping("/employees")
    public String getAll(Model model) {
        List<Employeee> employeees = employeeService.getAll();
        model.addAttribute("employees", employeees);
        return "index";
    }
}
