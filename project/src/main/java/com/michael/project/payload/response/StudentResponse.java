package com.michael.project.payload.response;

import com.michael.project.entity.Course;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class StudentResponse {
    private Long studentId;
    private String firstName;
    private String lastName;
    private String level;
    private Set<Course> courses = new HashSet<>();

}
