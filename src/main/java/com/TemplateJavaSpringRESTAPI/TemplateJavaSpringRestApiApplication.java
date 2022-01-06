package com.TemplateJavaSpringRESTAPI;

import com.TemplateJavaSpringRESTAPI.models.RoleModel;
import com.TemplateJavaSpringRESTAPI.models.UserModel;
import com.TemplateJavaSpringRESTAPI.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class TemplateJavaSpringRestApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(TemplateJavaSpringRestApiApplication.class, args);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CommandLineRunner run(UserService userService) {
        return args -> {
            userService.saveRole(new RoleModel(null, "ROLE_USER"));
            userService.saveRole(new RoleModel(null, "ROLE_MANAGER"));
            userService.saveRole(new RoleModel(null, "ROLE_ADMIN"));
            userService.saveRole(new RoleModel(null, "ROLE_SUPER_ADMIN"));

            userService.saveUser(new UserModel(null, "Mohammad", "Khaled", "mkabumattar", "121612", new ArrayList<>()));
            userService.saveUser(new UserModel(null, "Khaled", "Ali", "KAli", "121612", new ArrayList<>()));
            userService.saveUser(new UserModel(null, "Will", "Smith", "WSmith", "121612", new ArrayList<>()));
            userService.saveUser(new UserModel(null, "Jim", "Carry", "JCarry", "121612", new ArrayList<>()));

            userService.addRoleToUser("mkabumattar", "ROLE_SUPER_ADMIN");
            userService.addRoleToUser("KAli", "ROLE_MANAGER");
            userService.addRoleToUser("KAli", "ROLE_ADMIN");
            userService.addRoleToUser("WSmith", "ROLE_ADMIN");
            userService.addRoleToUser("JCarry", "ROLE_USER");
        };
    }

}
