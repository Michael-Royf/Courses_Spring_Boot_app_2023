package com.michael.project.payload.response;

import com.michael.project.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RoleResponse {
    private Long roleId;
    private String name;
    private Set<User> users = new HashSet<>();
}
