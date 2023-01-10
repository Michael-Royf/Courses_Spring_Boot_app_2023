package com.michael.project.service.impl;

import com.michael.project.entity.Course;
import com.michael.project.entity.Instructor;
import com.michael.project.entity.User;
import com.michael.project.payload.request.InstructorRequest;
import com.michael.project.payload.request.UserRequest;
import com.michael.project.payload.response.InstructorResponse;
import com.michael.project.repository.InstructorRepository;
import com.michael.project.repository.UserRepository;
import com.michael.project.service.CourseService;
import com.michael.project.service.InstructorService;
import com.michael.project.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class InstructorServiceImpl implements InstructorService {


    private CourseService courseService;
    private ModelMapper mapper;
    private InstructorRepository instructorRepository;
    private UserService userService;


    @Autowired
    public InstructorServiceImpl(ModelMapper mapper, InstructorRepository instructorRepository,
                                 UserService userService) {
        this.mapper = mapper;
        this.instructorRepository = instructorRepository;
        this.userService = userService;
    }


    @Override
    public InstructorResponse loadInstructorById(Long instructorId) {
        Instructor instructor = getInstructorFromDB(instructorId);
        return mapper.map(instructor, InstructorResponse.class);
    }

    @Override
    public List<InstructorResponse> findInstructorsByName(String name) {
        return instructorRepository.findInstructorsByName(name)
                .stream()
                .map(instructor -> mapper.map(instructor, InstructorResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public InstructorResponse loadInstructorByEmail(String email) {
        Instructor instructor = instructorRepository.findInstructorsByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Instructor with email: %s not found", email)));
        return mapper.map(instructor, InstructorResponse.class);
    }

    @Override
    public InstructorResponse createInstructor(InstructorRequest instructorRequest) {

        User user = userService
                .createUser(new UserRequest(instructorRequest.getEmail(), instructorRequest.getPassword()));
        userService.assignRoleToUser(user.getEmail(), "Instructor");

        Instructor instructor = Instructor.builder()
                .firstName(instructorRequest.getFirstName())
                .lastName(instructorRequest.getLastName())
                .user(user)
                .build();
        instructor = instructorRepository.save(instructor);
        return mapper.map(instructor, InstructorResponse.class);
    }

    @Override
    public InstructorResponse updateInstructor(Long instructorId, InstructorRequest instructorRequest) {
        Instructor instructor = getInstructorFromDB(instructorId);
        instructor.setFirstName(instructorRequest.getFirstName());
        instructor.setLastName(instructorRequest.getLastName());
        instructor.setSummary(instructor.getSummary());
        instructor = instructorRepository.save(instructor);
        return mapper.map(instructor, InstructorResponse.class);
    }

    @Override
    public List<InstructorResponse> fetchInstructors() {
        return instructorRepository.findAll()
                .stream()
                .map(instructor -> mapper.map(instructor, InstructorResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public String removeInstructor(Long instructorId) {
        Instructor instructor = getInstructorFromDB(instructorId);
        for (Course course : instructor.getCourses()) {
            courseService.removeCourse(course.getCourseId());
        }
        instructorRepository.delete(instructor);
        return String.format("Instructor with id %s was deleted", instructorId);
    }


    private Instructor getInstructorFromDB(Long instructorId) {
        return instructorRepository.findById(instructorId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Instructor with id: %s not found")));
    }
}
