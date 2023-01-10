package com.michael.project.payload.response;

import com.michael.project.entity.Role;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserResponse {
    private Long userId;
    private String email;
    private Set<Role> roles = new HashSet<>();

}
