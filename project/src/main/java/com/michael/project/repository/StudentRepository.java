package com.michael.project.repository;

import com.michael.project.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

    @Query(value = "select s from Student  as s where s.firstName like %:name% or s.lastName like %:name% ")
    List<Student> findStudentByName(@Param("name") String name);

    @Query(value = "select s from Student as s where s.user.email=:email")
    Optional<Student> findStudentByEmail(@Param("email") String email);
}
