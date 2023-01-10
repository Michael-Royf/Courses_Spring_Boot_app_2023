package com.michael.project.service;

import com.michael.project.payload.request.StudentRequest;
import com.michael.project.payload.response.StudentResponse;

import java.util.List;

public interface StudentService {

    StudentResponse loadStudentById(Long studentId);

    List<StudentResponse> findStudentByName(String name);

    StudentResponse loadStudentByEmail(String email);

    StudentResponse createStudent(StudentRequest studentRequest);

    StudentResponse updateStudent(Long studentId, StudentRequest studentRequest);

    List<StudentResponse> fetchStudents();

    String removeStudent(Long studentId);

}
