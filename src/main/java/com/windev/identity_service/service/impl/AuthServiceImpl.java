/**
 * @project identity-service
 * @author Phong Ha on 29/11/2024
 */

package com.windev.identity_service.service.impl;

import com.windev.identity_service.dto.UserDTO;
import com.windev.identity_service.entity.Role;
import com.windev.identity_service.entity.User;
import com.windev.identity_service.mapper.UserMapper;
import com.windev.identity_service.payload.request.SignInRequest;
import com.windev.identity_service.payload.request.SignUpRequest;
import com.windev.identity_service.repository.RoleRepository;
import com.windev.identity_service.repository.UserRepository;
import com.windev.identity_service.security.jwt.JwtTokenProvider;
import com.windev.identity_service.service.AuthService;
import java.util.HashSet;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private AuthenticationManager authenticationManager;


    @Override
    public String login(SignInRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        String token =jwtTokenProvider.generateToken(authentication);
        return token;
    }

    @Override
    public UserDTO register(SignUpRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhoneNumber(request.getPhoneNumber());

        Set<Role> roles = new HashSet<>();

        Role role = roleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("Role 'USER' not found."));
        roles.add(role);

        user.setRoles(roles);

        User savedUser = userRepository.save(user);

        return userMapper.toUserDTO(savedUser);
    }
}
