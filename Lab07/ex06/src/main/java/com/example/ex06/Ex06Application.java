package com.example.ex06;

import com.example.ex06.models.Student;
import com.example.ex06.repositories.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;


@SpringBootApplication
public class Ex06Application {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(Ex06Application.class, args);
		StudentRepository studentRepository = context.getBean(StudentRepository.class);

		Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Order.desc("age"), Sort.Order.asc("ielts")));
		Page<Student> studentsPage = studentRepository.findAll(pageable);
		Iterable<Student> students = studentsPage.getContent();
		System.out.println("Danh sách học sinh:");
		students.forEach(student -> System.out.println(student.toString()));

		if (studentsPage.getTotalElements() >= 6) {
			List<Student> studentsToPrint = studentsPage.stream()
					.filter(student -> student.getId() >= 4 && student.getId() <= 6)
					.toList();

			System.out.println("Students 4-5-6:");
			studentsToPrint.forEach(student -> System.out.println(student.toString()));
		}
	}

}
