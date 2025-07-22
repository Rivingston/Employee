package com.example.employeedata.service;

import com.example.employeedata.dto.ProfileDTO;
import com.example.employeedata.model.Employee;
import com.example.employeedata.model.Profile;
import com.example.employeedata.repository.EmployeeRepository;
import com.example.employeedata.repository.ProfileRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfileService {

    private final ProfileRepository profileRepo;
    private final EmployeeRepository employeeRepo;

    public ProfileService(ProfileRepository profileRepo, EmployeeRepository employeeRepo) {
        this.profileRepo = profileRepo;
        this.employeeRepo = employeeRepo;
    }

    private ProfileDTO mapToDTO(Profile profile) {
        ProfileDTO dto = new ProfileDTO();
        dto.setEmpId(profile.getEmpId());
        dto.setPhoneNo(profile.getPhoneNo());
        dto.setAddress(profile.getAddress());
        return dto;
    }

    private Profile mapToEntity(ProfileDTO dto) {
        Profile profile = new Profile();
        profile.setEmpId(dto.getEmpId());
        profile.setPhoneNo(dto.getPhoneNo());
        profile.setAddress(dto.getAddress());
        Employee employee = employeeRepo.findByEmpId(dto.getEmpId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        profile.setEmployee(employee);
        return profile;
    }

    public ProfileDTO create(ProfileDTO dto) {
        Employee employee = employeeRepo.findByEmpId(dto.getEmpId())
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        if (employee.getProfile() != null) {
            throw new RuntimeException("Profile already exists for this employee.");
        }

        Profile profile = new Profile();
        profile.setPhoneNo(dto.getPhoneNo());
        profile.setAddress(dto.getAddress());
        profile.setEmployee(employee);        // 👈 Set employee only
        employee.setProfile(profile);         // 👈 Set both sides of relationship

        profileRepo.save(profile);            // 👈 Save only once

        return mapToDTO(profile);
    }




    public List<ProfileDTO> getAll() {
        return profileRepo.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
    }

    public ProfileDTO getByEmpId(String empId) {
        Profile profile = profileRepo.findById(empId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));
        return mapToDTO(profile);
    }

    public ProfileDTO update(String empId, ProfileDTO dto) {
        Profile profile = profileRepo.findById(empId)
                .orElseThrow(() -> new RuntimeException("Profile not found"));
        profile.setPhoneNo(dto.getPhoneNo());
        profile.setAddress(dto.getAddress());
        return mapToDTO(profileRepo.save(profile));
    }

    public void delete(String empId) {
        profileRepo.deleteById(empId);
    }
}
