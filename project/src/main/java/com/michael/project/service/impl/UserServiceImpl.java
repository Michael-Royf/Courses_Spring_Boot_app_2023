package com.michael.project.service.impl;

import com.michael.project.entity.Role;
import com.michael.project.entity.User;
import com.michael.project.payload.request.UserRequest;
import com.michael.project.payload.response.UserResponse;
import com.michael.project.repository.RoleRepository;
import com.michael.project.repository.UserRepository;
import com.michael.project.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;


    @Override
    public UserResponse loadUserByEmail(String email) {
        User user = getUserFromDB(email);
        return mapper.map(user, UserResponse.class);
    }

    @Override
    public User createUser(UserRequest userRequest) {
        User user = User.builder()
                .email(userRequest.getEmail())
                .password(userRequest.getPassword())
                .build();
       return userRepository.save(user);
    }

    @Override
    public void assignRoleToUser(String email, String roleName) {
        User user =getUserFromDB(email);
        Role role = roleRepository.findByName(roleName)
                .orElseThrow(()->new EntityNotFoundException(String.format("Role with name: %s not found", roleName)));
        user.assignRoleToUser(role);
        userRepository.save(user);
    }

    private User getUserFromDB(String email){
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException(String.format("User with email: %s not found", email)));
    }
}
