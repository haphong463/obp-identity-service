/**
 * @project identity-service
 * @author Phong Ha on 29/11/2024
 */

package com.windev.identity_service.security.user_details;

import com.windev.identity_service.dto.UserDTO;
import com.windev.identity_service.entity.User;
import com.windev.identity_service.mapper.UserMapper;
import com.windev.identity_service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User with username: " + username + " not found."));

        UserDTO userDTO = userMapper.toUserDTO(user);

        return CustomUserDetails.build(userDTO);
    }
}
