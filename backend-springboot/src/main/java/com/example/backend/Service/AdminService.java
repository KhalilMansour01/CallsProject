package com.example.backend.Service;

import com.example.backend.Entity.AdminEntity;
import com.example.backend.Exceptions.ResourceNotFoundException;
import com.example.backend.Repository.AdminRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AdminService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Get all admins
    public List<AdminEntity> getAllAdmins() {
        List<AdminEntity> adminList = adminRepository.findAll();

        if (adminList.size() > 0) {
            return adminList;
        } else {
            return new ArrayList<AdminEntity>();
        }
    }

    // Get admin by id
    public ResponseEntity<AdminEntity> getAdminById(Long id) throws ResourceNotFoundException {
        AdminEntity admin = adminRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No admin record exist for given id : " + id));
        return ResponseEntity.ok(admin);
    }

    // Create admin
    public ResponseEntity<AdminEntity> createAdmin(AdminEntity admin) throws ResourceNotFoundException {
        boolean exists = adminRepository.findByUsername(admin.getUsername()).isPresent();
        if (exists) {
            throw new ResourceNotFoundException("Admin already exists with username: " +
                    admin.getUsername());
        } else {
            admin.setPassword(passwordEncoder.encode(admin.getPassword()));
            admin.setCreatedAt(LocalDate.now());
            AdminEntity savedAdmin = adminRepository.save(admin);
            return ResponseEntity.status(201).body(savedAdmin);
        }
    }

    // Update admin
    public ResponseEntity<AdminEntity> updateAdmin(Long id, AdminEntity admin) throws ResourceNotFoundException {
        AdminEntity existingAdmin = adminRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No admin record exist for given id : " + id));

        if (admin.getUsername() != null) {
            existingAdmin.setUsername(admin.getUsername());
        }
        if (admin.getEmail() != null) {
            existingAdmin.setEmail(admin.getEmail());
        }
        if (admin.getPassword() != null) {
            existingAdmin.setPassword(passwordEncoder.encode(admin.getPassword()));
        }

        final AdminEntity updatedAdmin = adminRepository.save(existingAdmin);
        return ResponseEntity.ok(updatedAdmin);
    }

    // Delete admin
    public ResponseEntity<AdminEntity> deleteAdminById(Long id) throws ResourceNotFoundException {
        AdminEntity admin = adminRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No admin record exist for given id : " + id));

        adminRepository.delete(admin);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<AdminEntity> findByUsername(String username) {

        return ResponseEntity.ok(adminRepository.findByUsername(username).orElse(null));
    }

    // Find admin by email
    public ResponseEntity<AdminEntity> findByEmail(String email) throws ResourceNotFoundException {
        AdminEntity admin = adminRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("No admin record exist for given email :: " + email));
        return ResponseEntity.status(201).body(admin);
    }

    // Check if username taken
    public boolean checkIfAdminExists(String username) {
        AdminEntity admin = adminRepository.findByUsername(username).orElse(null);

        return admin != null;
    }

    // Check credentials
    public boolean checkCredentials(String username, String password) {
        AdminEntity admin = adminRepository.findByUsername(username).orElse(null);
        if (admin != null) {
            return admin.getPassword().equals(password);
        }
        return false;
    }
}