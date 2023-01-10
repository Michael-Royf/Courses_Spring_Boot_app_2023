package com.michael.project.payload.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CourseRequest {
    private String courseName;
    private String courseDuration;
    private String courseDescription;
    private Long instructorId;
}
