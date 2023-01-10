package com.michael.project.repository;

import com.michael.project.entity.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor, Long> {

    @Query(value = "select i from Instructor as i where i.firstName like %:name% or i.lastName like %:name%")
    List<Instructor> findInstructorsByName(@Param("name") String name);

    @Query(value = "select i from Instructor  as i where i.user.email=:email")
    Optional<Instructor> findInstructorsByEmail(@Param("email") String email);
}
