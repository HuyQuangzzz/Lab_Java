package com.example.ex03;

import com.example.ex03.models.Student;
import com.example.ex03.repositories.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class StudentCommandLineRunner implements CommandLineRunner {
    private final StudentRepository studentRepository;

    public StudentCommandLineRunner(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public void run(String... args) {
        // Thêm 3 học sinh
        Student student1 = new Student("Alice", 25, "alice@example.com", 7.5);
        Student student2 = new Student("Bob", 23, "bob@example.com", 6.8);
        Student student3 = new Student("Charlie", 27, "charlie@example.com", 6.0);
        studentRepository.saveAll(List.of(student1, student2, student3));

        // Đọc danh sách học sinh
        Iterable<Student> students = studentRepository.findAll();
        System.out.println("Danh sách học sinh:");
        students.forEach(student -> System.out.println(student.toString()));

        // Cập nhật thông tin học sinh
        Optional<Student> studentToUpdate = studentRepository.findById(1L); // Giả sử id là 1
        if (studentToUpdate.isPresent()) {
            Student updatedStudent = studentToUpdate.get();
            updatedStudent.setAge(26);
            studentRepository.save(updatedStudent);
            System.out.println("Thông tin học sinh sau khi cập nhật: " + updatedStudent.toString());
        } else {
            System.out.println("Học sinh không tồn tại.");
        }

        // Xóa học sinh
        studentRepository.deleteById(2L); // Giả sử id là 2
        System.out.println("Học sinh với id 2 đã bị xóa.");

        // Đọc danh sách học sinh sau khi xóa
        System.out.println("Danh sách học sinh sau khi xóa:");
        studentRepository.findAll().forEach(student -> System.out.println(student.toString()));



    }
}