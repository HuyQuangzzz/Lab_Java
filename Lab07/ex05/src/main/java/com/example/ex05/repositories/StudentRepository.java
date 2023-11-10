package com.example.ex05.repositories;

import com.example.ex05.models.Student;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends CrudRepository<Student, Long> {

    @Query("SELECT s FROM Student s WHERE s.age >= :x")
    Iterable<Student> findByAge(@Param("x") int x);
    @Query("SELECT COUNT(s) FROM Student s WHERE s.ielts = :x")
    long countByIelts(@Param("x") double x);
    @Query("SELECT s FROM Student s WHERE LOWER(s.name) LIKE %:name%")
    Iterable<Student> findByName(@Param("name") String name);

}
