package com.example.backend.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.backend.Repository.AdminRepository;
import com.example.backend.Entity.AdminEntity;

import java.util.Optional;
// import java.util.Arrays;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AdminEntity> admin = adminRepository.findByUsername(username);
        if (admin.isPresent()) {
            var user = admin.get();
            String[] roles = getRoles(user);
            // Debug log to check roles
            // System.out.println("Roles assigned: " + Arrays.toString(roles));
            return User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .roles(roles)
                    .build();
        } else {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }

    private String[] getRoles(AdminEntity admin) {
        if (admin.getRole() == null) {
            return new String[] { "ADMIN" };
        } else {
            return admin.getRole().split(",");
        }
    }

    // private String[] getRoles(AdminEntity admin) {
    // if (admin.getRole() == null) {
    // return new String[] { "ROLE_ADMIN" };
    // } else {
    // // Add "ROLE_" prefix to each role
    // return Arrays.stream(admin.getRole().split(","))
    // .map(role -> "ROLE_" + role.trim())
    // .toArray(String[]::new);
    // }
    // }

}