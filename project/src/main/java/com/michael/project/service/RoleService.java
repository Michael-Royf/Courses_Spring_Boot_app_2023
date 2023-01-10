package com.michael.project.service;

import com.michael.project.payload.request.RoleRequest;
import com.michael.project.payload.response.RoleResponse;

public interface RoleService {
    RoleResponse loadRoleByName(String roleName);

    RoleResponse createRole(RoleRequest roleRequest);
}
