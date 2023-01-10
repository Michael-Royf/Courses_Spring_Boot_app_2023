package com.michael.project.service;

import com.michael.project.entity.Course;
import com.michael.project.payload.request.CourseRequest;
import com.michael.project.payload.response.CourseResponse;

import java.util.List;

public interface CourseService {

    CourseResponse loadCourseById(Long courseId);

    CourseResponse createCourse(CourseRequest courseRequest);

    CourseResponse updateCourse(Long courseId, CourseRequest courseRequest);

    List<CourseResponse> findCoursesByCourseName(String keyword);

    void assignStudentToCourse(Long courseId, Long studentId);

    List<CourseResponse> fetchAll();

    List<CourseResponse> fetchCoursesForStudent(Long studentId);

    String removeCourse(Long courseId);

}
