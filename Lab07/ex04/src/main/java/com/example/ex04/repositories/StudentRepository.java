package com.example.ex04.repositories;

import com.example.ex04.models.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends CrudRepository<Student, Long> {

    Iterable<Student> findByAgeGreaterThan(int x);
    int countByIelts(double x);
    List<Student> findByNameContaining(String xxx);
}
