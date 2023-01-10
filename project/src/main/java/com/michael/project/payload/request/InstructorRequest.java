package com.michael.project.payload.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class InstructorRequest {
    private String firstName;
    private String lastName;
    private String summary;
    private String email;
    private String password;
}
