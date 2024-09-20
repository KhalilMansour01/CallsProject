package com.example.backend.Controller;

import com.example.backend.Entity.AdminEntity;
import com.example.backend.Exceptions.ResourceNotFoundException;
import com.example.backend.Security.LoginForm;
import com.example.backend.Service.AdminService;
import com.example.backend.Security.JwtService;
import com.example.backend.Security.CustomUserDetailsService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    JwtService jwtService;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private AdminService adminService;

    @GetMapping("/all")
    public ResponseEntity<List<AdminEntity>> getAllAdmins() {
        List<AdminEntity> list = adminService.getAllAdmins();
        return new ResponseEntity<List<AdminEntity>>(list, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdminEntity> getAdminProfile(@PathVariable("id") Long id) throws ResourceNotFoundException {
        return adminService.getAdminById(id);
    }

    @PostMapping("/register")
    public ResponseEntity<AdminEntity> registerAdmin(@RequestBody AdminEntity admin) throws ResourceNotFoundException {
        return adminService.createAdmin(admin);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<AdminEntity> updateAdmin(@PathVariable("id") Long id, @RequestBody AdminEntity admin)
            throws ResourceNotFoundException {
        return adminService.updateAdmin(id, admin);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<AdminEntity> deleteAdmin(@PathVariable("id") Long id) throws ResourceNotFoundException {
        return adminService.deleteAdminById(id);
    }

    // @GetMapping("/findByEmail/{email}")
    // public ResponseEntity<AdminEntity> searchByEmail(@PathVariable("email") String email)
    //         throws ResourceNotFoundException {
    //     return adminService.findByEmail(email);
    // }

    @GetMapping("/findByUsername/{username}")
    public ResponseEntity<AdminEntity> searchByUsername(@PathVariable("username") String username)
            throws ResourceNotFoundException {
        return adminService.findByUsername(username);
    }

    
    @GetMapping("/checkIfExist/{username}")
    public Boolean checkIfAdminExists(@RequestParam String username) {
        return adminService.checkIfAdminExists(username);
    }

    @PostMapping("/authenticate")
    public String authenticateAndGetToken(@RequestBody LoginForm loginForm) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginForm.username(), loginForm.password()));

        if(authentication.isAuthenticated()){
            return jwtService.generateToken(userDetailsService.loadUserByUsername(loginForm.username()));
        } else {
            throw new UsernameNotFoundException("Invalid username or password");
        }
    }

}