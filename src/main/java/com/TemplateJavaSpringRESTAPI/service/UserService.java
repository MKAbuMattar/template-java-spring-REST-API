package com.TemplateJavaSpringRESTAPI.service;

import com.TemplateJavaSpringRESTAPI.models.RoleModel;
import com.TemplateJavaSpringRESTAPI.models.UserModel;

import java.util.List;

public interface UserService {
    UserModel saveUser(UserModel user);

    RoleModel saveRole(RoleModel role);

    void addRoleToUser(String username, String roleName);

    UserModel getUser(String username);

    List<UserModel> getUsers();
}
