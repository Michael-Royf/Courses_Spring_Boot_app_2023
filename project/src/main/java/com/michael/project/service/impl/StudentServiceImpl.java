package com.michael.project.service.impl;

import com.michael.project.entity.Course;
import com.michael.project.entity.Student;
import com.michael.project.entity.User;
import com.michael.project.payload.request.StudentRequest;
import com.michael.project.payload.request.UserRequest;
import com.michael.project.payload.response.StudentResponse;
import com.michael.project.repository.StudentRepository;
import com.michael.project.service.StudentService;
import com.michael.project.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private UserService userService;

    @Autowired
    private StudentRepository studentRepository;


    @Override
    public StudentResponse loadStudentById(Long studentId) {
        Student student = getStudentByDB(studentId);
        return mapper.map(student, StudentResponse.class);
    }

    @Override
    public List<StudentResponse> findStudentByName(String name) {
        return studentRepository.findStudentByName(name)
                .stream()
                .map(student -> mapper.map(student, StudentResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public StudentResponse loadStudentByEmail(String email) {
        Student student = studentRepository.findStudentByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Student with email: %s not found", email)));
        return mapper.map(student, StudentResponse.class);
    }

    @Override
    public StudentResponse createStudent(StudentRequest studentRequest) {
        User user = userService.createUser(new UserRequest(studentRequest.getEmail(), studentRequest.getPasword()));
        userService.assignRoleToUser(studentRequest.getEmail(), "Student");
        Student student = Student.builder()
                .firstName(studentRequest.getFirstName())
                .lastName(studentRequest.getLastName())
                .level(studentRequest.getLevel())
                .user(user)
                .build();
        student = studentRepository.save(student);
        return mapper.map(student, StudentResponse.class);
    }

    @Override
    public StudentResponse updateStudent(Long studentId, StudentRequest studentRequest) {
        Student student = getStudentByDB(studentId);
        student.setFirstName(studentRequest.getFirstName());
        student.setLastName(studentRequest.getLastName());
        student.setLevel(student.getLevel());
        student = studentRepository.save(student);
        return mapper.map(student, StudentResponse.class);
    }

    @Override
    public List<StudentResponse> fetchStudents() {
        return  studentRepository.findAll()
                .stream()
                .map(student -> mapper.map(student, StudentResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public String removeStudent(Long studentId) {
       Student student  = getStudentByDB(studentId);
        Iterator<Course>  iterator = student.getCourses().iterator();
        if (iterator.hasNext()){
            Course course = iterator.next();
            course.removeStudentFromCourse(student);
        }
       studentRepository.delete(student);
       return String.format("Student with id: %s  was deleted");
    }


    private Student getStudentByDB(Long studentId) {
        return studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Student with id: %s not found", studentId)));
    }
}
