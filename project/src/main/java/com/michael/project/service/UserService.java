package com.michael.project.service;

import com.michael.project.entity.User;
import com.michael.project.payload.request.UserRequest;
import com.michael.project.payload.response.UserResponse;

public interface UserService {

    UserResponse loadUserByEmail(String email);

  User createUser(UserRequest userRequest);

    void assignRoleToUser(String email, String roleName);
}
