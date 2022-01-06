package com.TemplateJavaSpringRESTAPI.service;

import com.TemplateJavaSpringRESTAPI.models.RoleModel;
import com.TemplateJavaSpringRESTAPI.models.UserModel;
import com.TemplateJavaSpringRESTAPI.repository.RoleRepository;
import com.TemplateJavaSpringRESTAPI.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {
    private final String TAG = "UserServiceImpl";

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserModel saveUser(UserModel user) {
        log.info("{}, Save new user: {} to DB", TAG, user.getUsername());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public RoleModel saveRole(RoleModel role) {
        log.info("{}, Save new role: {} to DB", TAG, role.getName());
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("{}, add role: {} to user: {} to DB", TAG, roleName, username);

        UserModel user = userRepository.findByUsername(username);
        RoleModel role = roleRepository.findByName(roleName);

        user.getRoles().add(role);
    }

    @Override
    public UserModel getUser(String username) {
        log.info("{}, get user: {} to DB", TAG, username);
        return userRepository.findByUsername(username);
    }

    @Override
    public List<UserModel> getUsers() {
        log.info("{}, Get All users from DB", TAG);
        return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel user = userRepository.findByUsername(username);

        if (user == null) {
            log.info("{}, User not found in database", TAG);
            throw new UsernameNotFoundException("User not found in database");
        } else {
            log.info("{}, User not found in database: {}", TAG, user.getUsername());

        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

        user.getRoles().forEach(roleModel -> {
            authorities.add(new SimpleGrantedAuthority(roleModel.getName()));
        });

        return new User(user.getUsername(), user.getPassword(), authorities);
    }
}
