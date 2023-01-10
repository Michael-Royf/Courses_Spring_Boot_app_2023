package com.michael.project.service.impl;

import com.michael.project.entity.Course;
import com.michael.project.entity.Instructor;
import com.michael.project.entity.Student;
import com.michael.project.payload.request.CourseRequest;
import com.michael.project.payload.response.CourseResponse;
import com.michael.project.repository.CourseRepository;
import com.michael.project.repository.InstructorRepository;
import com.michael.project.repository.StudentRepository;
import com.michael.project.service.CourseService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CourseServiceImpl implements CourseService {

    private InstructorRepository instructorRepository;
    private StudentRepository studentRepository;
    private CourseRepository courseRepository;
    private ModelMapper mapper;

    @Autowired
    public CourseServiceImpl(InstructorRepository instructorRepository,
                             StudentRepository studentRepository,
                             CourseRepository courseRepository,
                             ModelMapper mapper) {
        this.instructorRepository = instructorRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.mapper = mapper;
    }

    @Override
    public CourseResponse loadCourseById(Long courseId) {
        Course course = getCourseFromDB(courseId);
        return mapper.map(course, CourseResponse.class);
    }

    @Override
    public CourseResponse createCourse(CourseRequest courseRequest) {
        Instructor instructor = getInstructorFromDB(courseRequest.getInstructorId());
        Course course = Course.builder()
                .courseName(courseRequest.getCourseName())
                .courseDescription(courseRequest.getCourseDescription())
                .courseDuration(courseRequest.getCourseDuration())
                .instructor(instructor)
                .build();
        course = courseRepository.save(course);
        return mapper.map(course, CourseResponse.class);
    }

    @Override
    public CourseResponse updateCourse(Long courseId, CourseRequest courseRequest) {
        Instructor instructor = getInstructorFromDB(courseRequest.getInstructorId());
        Course course = getCourseFromDB(courseId);
        course.setCourseName(courseRequest.getCourseName());
        course.setCourseDuration(courseRequest.getCourseDuration());
        course.setCourseDescription(courseRequest.getCourseDescription());
        course.setInstructor(instructor);
        course = courseRepository.save(course);
        return mapper.map(course, CourseResponse.class);
    }



    @Override
    public List<CourseResponse> findCoursesByCourseName(String keyword) {
        return courseRepository.findCoursesByCourseNameContains(keyword)
                .stream()
                .map(course -> mapper.map(course, CourseResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public void assignStudentToCourse(Long courseId, Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Student with id %s not found")));
        Course course = getCourseFromDB(courseId);
        course.assignStudentToCourse(student);
    }

    @Override
    public List<CourseResponse> fetchAll() {
        return courseRepository.findAll()
                .stream()
                .map(course -> mapper.map(course, CourseResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<CourseResponse> fetchCoursesForStudent(Long studentId) {
        return courseRepository.getCourseByStudentId(studentId).stream()
                .map(course -> mapper.map(course, CourseResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public String removeCourse(Long courseId) {
        Course course = getCourseFromDB(courseId);
        courseRepository.delete(course);
        return String.format("Course with id: %s was deleted", courseId);
    }


    private Course getCourseFromDB(Long courseId) {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Course with id %s not found", courseId)));
    }
    private Instructor getInstructorFromDB(Long instructorId) {
        return instructorRepository.findById(instructorId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Instructor with id: %s not found", instructorId)));
    }
}
