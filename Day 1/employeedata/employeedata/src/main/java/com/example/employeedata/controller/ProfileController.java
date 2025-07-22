package com.example.employeedata.controller;

import com.example.employeedata.dto.ProfileDTO;
import com.example.employeedata.service.ProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/profiles")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping
    public ResponseEntity<ProfileDTO> create(@RequestBody ProfileDTO dto) {
        return ResponseEntity.ok(profileService.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<ProfileDTO>> getAll() {
        return ResponseEntity.ok(profileService.getAll());
    }

    @GetMapping("/{empId}")
    public ResponseEntity<ProfileDTO> getByEmpId(@PathVariable String empId) {
        return ResponseEntity.ok(profileService.getByEmpId(empId));
    }

    @PutMapping("/{empId}")
    public ResponseEntity<ProfileDTO> update(@PathVariable String empId, @RequestBody ProfileDTO dto) {
        return ResponseEntity.ok(profileService.update(empId, dto));
    }

    @DeleteMapping("/{empId}")
    public ResponseEntity<Void> delete(@PathVariable String empId) {
        profileService.delete(empId);
        return ResponseEntity.noContent().build();
    }
}
