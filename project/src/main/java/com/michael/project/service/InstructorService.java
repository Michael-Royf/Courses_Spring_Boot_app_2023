package com.michael.project.service;

import com.michael.project.payload.request.InstructorRequest;
import com.michael.project.payload.response.InstructorResponse;

import java.util.List;

public interface InstructorService {
    InstructorResponse loadInstructorById(Long instructorId);

    List<InstructorResponse> findInstructorsByName(String name);

    InstructorResponse loadInstructorByEmail(String email);

    InstructorResponse createInstructor(InstructorRequest instructorRequest);

    InstructorResponse updateInstructor(Long instructorId, InstructorRequest instructorRequest);

    List<InstructorResponse> fetchInstructors();

    String removeInstructor(Long instructorId);
}
