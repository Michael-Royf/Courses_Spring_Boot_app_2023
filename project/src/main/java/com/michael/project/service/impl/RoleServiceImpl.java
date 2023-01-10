package com.michael.project.service.impl;

import com.michael.project.entity.Role;
import com.michael.project.payload.request.RoleRequest;
import com.michael.project.payload.response.RoleResponse;
import com.michael.project.repository.RoleRepository;
import com.michael.project.service.RoleService;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class RoleServiceImpl  implements RoleService {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public RoleResponse loadRoleByName(String roleName) {
        Role role = roleRepository.findByName(roleName)
                .orElseThrow(()->new EntityNotFoundException(String.format("Role with name: %s not found")));
        return mapper.map(role, RoleResponse.class);
    }

    @Override
    public RoleResponse createRole(RoleRequest roleRequest) {
        Role role = Role.builder()
                .name(roleRequest.getName())
                .build();
        role = roleRepository.save(role);
        return mapper.map(role, RoleResponse.class);
    }
}
