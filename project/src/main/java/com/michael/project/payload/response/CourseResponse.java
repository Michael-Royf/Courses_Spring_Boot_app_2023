package com.michael.project.payload.response;

import com.michael.project.entity.Instructor;
import com.michael.project.entity.Student;

import java.util.HashSet;
import java.util.Set;

public class CourseResponse {
    private Long courseId;
    private String courseName;
    private String courseDuration;
    private String courseDescription;
    private Instructor instructor;
    private Set<Student> students = new HashSet<>();

}
